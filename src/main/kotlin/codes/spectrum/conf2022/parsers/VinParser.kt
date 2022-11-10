package codes.spectrum.conf2022.parsers

import codes.spectrum.conf2022.IDocTypeParser
import codes.spectrum.conf2022.doc_type.DocType
import codes.spectrum.conf2022.output.ExtractedDocument

class VinParser : IDocTypeParser {
    override val docType: DocType = DocType.VIN

    override fun parse(input: String): ExtractedDocument? {
        val normalized = normalizeInput(input)
        for (word in normalized) {
            val maybeVin = regex.find(word)?.value ?: continue
            if (maybeVin.any { !it.isLetterOrDigit() } ||
                maybeVin.none { it.isDigit() } ||
                maybeVin.none { it.isLetter() }
            ) {
                return ExtractedDocument(docType, maybeVin, true, false)
            }
            return ExtractedDocument(docType, maybeVin, true, true)
        }
        return null
    }

    private val regex = """\b[A-Z0-9]{17}\b""".toRegex()

    private val translit = mapOf(
        'А' to 'A',
        'В' to 'B',
        'Е' to 'E',
        'К' to 'K',
        'М' to 'M',
        'Н' to 'H',
        'О' to '0',
        'Р' to 'P',
        'С' to 'C',
        'Т' to 'T',
        'У' to 'Y',
        'Х' to 'X',

        'I' to '1',
        'O' to '0',
        'Q' to '0',
    )

    private fun normalizeInput(input: String): List<String> =
        input
            .trimStart('@')
            .replace("_", "")
            .replace("-", "")
            .uppercase()
            .map { translit[it] ?: it }
            .joinToString(separator = "")
            .split(" ")
}
