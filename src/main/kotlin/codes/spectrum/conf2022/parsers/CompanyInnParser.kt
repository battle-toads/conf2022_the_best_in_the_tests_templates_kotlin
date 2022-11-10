package codes.spectrum.conf2022.parsers

import codes.spectrum.conf2022.IDocTypeParser
import codes.spectrum.conf2022.doc_type.DocType
import codes.spectrum.conf2022.output.ExtractedDocument

class CompanyInnParser : IDocTypeParser {
    override val docType: DocType = DocType.INN_UL

    override fun parse(input: String): ExtractedDocument? {
        val normalized = normalizeInput(input)
        if (normalized.length != 10) return null
        val isValidRegion = normalized.take(2) != "00"
        val digits = normalized.map { it.digitToInt() }
        val checksum = digits.zip(weights) { digit, weight -> weight * digit }
            .sum() % 11 % 10
        val isValid = checksum == digits[9] && isValidRegion
        return ExtractedDocument(docType, normalized, true, isValid)
    }

    private val weights = listOf(2,4,10,3,5,9,4,6,8,0)

    private fun normalizeInput(input: String): String =
        input.filter { it.isDigit() }
}
