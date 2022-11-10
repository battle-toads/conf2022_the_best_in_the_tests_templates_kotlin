package codes.spectrum.conf2022.parsers

import codes.spectrum.conf2022.IDocTypeParser
import codes.spectrum.conf2022.doc_type.DocType
import codes.spectrum.conf2022.output.ExtractedDocument

class PersonInnParser : IDocTypeParser {
    override val docType: DocType = DocType.INN_FL

    override fun parse(input: String): ExtractedDocument? {
        val normalized = normalizeInput(input)
        if (normalized.length != 12) return null
        val digits = normalized.map { it.digitToInt() }
        val checksum1 = digits.zip(weights) { digit, weight -> weight * digit }
            .sum() % 11 % 10
        val checksum2 = digits.zip(weights2) { digit, weight -> weight * digit }
            .sum() % 11 % 10
        val isValid = checksum1 == digits[10] && checksum2 == digits[11]
        return ExtractedDocument(docType, normalized, true, isValid)
    }

    private val weights = listOf(7,2,4,10,3,5,9,4,6,8,0)
    private val weights2 = listOf(3,7,2,4,10,3,5,9,4,6,8,0)

    private fun normalizeInput(input: String): String =
        input.filter { it.isDigit() }
}
