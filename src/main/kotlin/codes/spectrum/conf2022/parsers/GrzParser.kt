package codes.spectrum.conf2022.parsers

import codes.spectrum.conf2022.IDocTypeParser
import codes.spectrum.conf2022.doc_type.DocType
import codes.spectrum.conf2022.output.ExtractedDocument

class GrzParser : IDocTypeParser {
    override val docType: DocType = DocType.GRZ

    override fun parse(input: String): ExtractedDocument? {
        val normalized = normalizeInput(input)
        val maybeGrz = regex.find(normalized)?.value ?: return null
        val region = maybeGrz.takeLastWhile { it.isDigit() }
        val isValid = region[0] != '0' && region != "20"
        return ExtractedDocument(docType, maybeGrz, true, isValid)
    }

    private val regex = """\b[АВЕКМНОРСТУХ]\d{3}[АВЕКМНОРСТУХ]{2}\d{2,3}\b""".toRegex()

    private val translit = mapOf(
        'A' to 'А',
        'B' to 'В',
        'E' to 'Е',
        'K' to 'К',
        'M' to 'М',
        'H' to 'Н',
        'O' to 'О',
        'P' to 'Р',
        'C' to 'С',
        'T' to 'Т',
        'Y' to 'У',
        'X' to 'Х',
    )

    private fun normalizeInput(input: String): String =
        input
            .trimStart('@')
            .replace(" " , "")
            .replace("_", "")
            .replace("-", "")
            .uppercase()
            .map { translit[it] ?: it }
            .joinToString(separator = "")
}
