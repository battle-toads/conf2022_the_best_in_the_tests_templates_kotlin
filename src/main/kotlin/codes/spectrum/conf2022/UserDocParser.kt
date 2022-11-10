package codes.spectrum.conf2022

import codes.spectrum.conf2022.doc_type.DocType
import codes.spectrum.conf2022.input.IDocParser
import codes.spectrum.conf2022.output.ExtractedDocument
import codes.spectrum.conf2022.parsers.*
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
        /*if (input.startsWith("@ ")) {
            return qualificationTests(input)
        }*/

        val result = mutableListOf<ExtractedDocument>()
        for (docType in DocType.values()) {
            parsers[docType]?.parse(input)?.let { result.add(it) }
        }

        return result
            .sortedByDescending { it.isValid }
            .ifEmpty { listOf(ExtractedDocument(DocType.NOT_FOUND)) }
    }

    private val parsers = mapOf(
        DocType.T1 to T1Parser(),
        DocType.T2 to T2Parser(),
        DocType.GRZ to GrzParser(),
        DocType.VIN to VinParser(),
        DocType.INN_FL to PersonInnParser(),
        DocType.INN_UL to CompanyInnParser(),
        DocType.SNILS to SnilsParser(),
    )

    private fun qualificationTests(input: String): List<ExtractedDocument> {
        //TODO: вот тут надо пройти квалификацию по тестам из base.csv, которые начинаются на `@ BT...`
        val normalized = normalizeInput(input)
        if (!(normalized.startsWith("BTT1") || normalized.startsWith("BTT2") || normalized.startsWith("BTT0"))) {
            return listOf(ExtractedDocument(DocType.NOT_FOUND))
        }

        val result = mutableListOf<ExtractedDocument>()
        val split = normalized.substring(4)
        val len = split.length
        if (normalized.startsWith("BTT1") || normalized.startsWith("BTT0")) {
            if (len == 4 || len == 5) {
                val t1Valid = split.first() == '5' && split.last() == '7'
                result.add(ExtractedDocument(DocType.T1, normalized, true, t1Valid))
            } else {
                result.add(ExtractedDocument(DocType.NOT_FOUND))
            }
        }

        if (normalized.startsWith("BTT2") || normalized.startsWith("BTT0")) {
            val t2Valid = split.contains("5") && len == 4
            result.add(ExtractedDocument(DocType.T2, normalized, true, t2Valid))
        }

        return result.sortedBy { !it.isValid }
    }

    private fun normalizeInput(input: String): String =
        input.replace(" " , "")
            .replace("_", "")
            .replace("-", "")


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
