package codes.spectrum.conf2022.parsers

import codes.spectrum.conf2022.IDocTypeParser
import codes.spectrum.conf2022.doc_type.DocType
import codes.spectrum.conf2022.output.ExtractedDocument

class T2Parser : IDocTypeParser {
    override val docType: DocType = DocType.T2

    override fun parse(input: String): ExtractedDocument? {
        val normalized = normalizeInput(input)
        if (normalized.startsWith("BTT2") || normalized.startsWith("BTT0")) {
            val split = normalized.substring(4)
            val t2Valid = split.contains("5") && split.length == 4
            return ExtractedDocument(docType, normalized, true, t2Valid)
        }
        return null
    }


    private fun normalizeInput(input: String): String =
        input
            .trimStart('@')
            .replace(" " , "")
            .replace("_", "")
            .replace("-", "")
}
