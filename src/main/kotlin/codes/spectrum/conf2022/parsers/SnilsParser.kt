package codes.spectrum.conf2022.parsers

import codes.spectrum.conf2022.IDocTypeParser
import codes.spectrum.conf2022.doc_type.DocType
import codes.spectrum.conf2022.output.ExtractedDocument

class SnilsParser() : IDocTypeParser {
    override val docType: DocType = DocType.SNILS

    val regex = "^(?<first>\\d{3})-(?<second>\\d{3})-(?<third>\\d{3})-(?<check>\\d{2})$".toRegex()
    val maxSnilsWithoutCheckSumm = "001-001-998-00"

    override fun parse(input: String): ExtractedDocument? {
        val maybeSnils = regex.find(input)?.value ?: return null

        val groups = regex.matchEntire(maybeSnils)?.groups ?: return null

        val first = groups["first"]?.value
        val second = groups["second"]?.value
        val third = groups["third"]?.value
        val check = groups["check"]?.value

        if (first?.toInt() == 1 && second?.toInt() == 1 && (third?.toInt() ?: 0) <= 998) {
            return ExtractedDocument(docType, maybeSnils, true, true)
        } else {
            return ExtractedDocument(docType, maybeSnils, true, true)
        }
    }
}