package codes.spectrum.conf2022.parsers

import codes.spectrum.conf2022.IDocTypeParser
import codes.spectrum.conf2022.doc_type.DocType
import codes.spectrum.conf2022.output.ExtractedDocument

class SnilsParser() : IDocTypeParser {
    override val docType: DocType = DocType.SNILS

    val regex = "^\\d{3}-\\d{3}-\\d{3}-\\d{2}$".toRegex()

    override fun parse(input: String): ExtractedDocument? {
        val maybeSnils = regex.find(input)?.value ?: return null

        return ExtractedDocument(docType, maybeSnils, true, true)
    }


}