package codes.spectrum.conf2022

import codes.spectrum.conf2022.doc_type.DocType
import codes.spectrum.conf2022.input.IDocParser
import codes.spectrum.conf2022.output.ExtractedDocument
import kotlin.random.Random

/**
 * Вот собственно и класс, который как участник вы должны реализовать
 *
 * контракт один - пустой конструктор и реализация [IDocParser]
 */
class UserDocParser : IDocParser {
    override fun parse(input: String): List<ExtractedDocument> {
        /**
         * Это пример чтобы пройти совсем первый базовый тест, хардкод, но понятно API,
         * просто посмотрите preparedSampleTests для примера
         */
        if (input.startsWith("BASE_SAMPLE1.")) {
            return preparedSampleTests(input)
        }
        /**
         * Это раздел квалификации - все инпуты начинаются с `@ `
         * призываем Вас НЕ хардкодить!!! хардкод проверим просто на ревью по этой функции,
         * надо честно реализовать спеки по DocType.T1 и DocType.T2
         * мы их будем проверять секретными тестами!!!
         */
        if (input.startsWith("@ ")) {
            return qualificationTests(input)
        }

        /**
         * Вот тут уже можете начинать свою реализацию боевого кода
         */

        return emptyList()
    }

    private fun qualificationTests(input: String): List<ExtractedDocument> {
        //TODO: вот тут надо пройти квалификацию по тестам из base.csv, которые начинаются на `@ BT...`
        val normalized = normalizeInput(input)
        if (!(normalized.startsWith("BTT1") || normalized.startsWith("BTT2") || normalized.startsWith("BTT0"))) {
            return listOf(ExtractedDocument(DocType.NOT_FOUND, normalized, false, false))
        }

        val result = mutableListOf<ExtractedDocument>()
        val split = normalized.substring(4)
        val len = split.length
        if (normalized.startsWith("BTT1") || normalized.startsWith("BTT0")) {
            if (len == 4 || len == 5) {
                if (split.first() == '5' && split.last() == '7') {
                    result.add(ExtractedDocument(DocType.T1, normalized, true, true))
                } else {
                    result.add(ExtractedDocument(DocType.T1, normalized, true, false))
                }
            }
        }

        if (normalized.startsWith("BTT2") || normalized.startsWith("BTT0")) {
            if (split.contains("5") && len == 4)
                result.add(ExtractedDocument(DocType.T2, normalized, true, true))
        }

        return result.sortedBy { !it.isValid }
    }

    private fun normalizeInput(input: String): String = input.split(" ")[1].replace("_", "").replace("-", "")


    private fun preparedSampleTests(input: String): List<ExtractedDocument> {
        return when (input.split("BASE_SAMPLE1.")[1]) {
            "1" -> return listOf(ExtractedDocument(DocType.NOT_FOUND))
            "2" -> return listOf(
                // рандомы демонстрируют, что при условии INN_FL, PASSPORT_RF - проверяются только типы
                ExtractedDocument(
                    DocType.INN_FL,
                    isValidSetup = Random.nextBoolean(),
                    isValid = Random.nextBoolean(),
                    value = Random.nextInt().toString()
                ), ExtractedDocument(
                    DocType.PASSPORT_RF,
                    isValidSetup = Random.nextBoolean(),
                    isValid = Random.nextBoolean(),
                    value = Random.nextInt().toString()
                )
            )

            "3" -> return listOf(
                ExtractedDocument(
                    DocType.GRZ, isValidSetup = true, isValid = true, value = Random.nextInt().toString()
                )
            )

            "4" -> return listOf(ExtractedDocument(DocType.INN_UL, value = "3456709873"))
            else -> emptyList()
        }
    }
}
