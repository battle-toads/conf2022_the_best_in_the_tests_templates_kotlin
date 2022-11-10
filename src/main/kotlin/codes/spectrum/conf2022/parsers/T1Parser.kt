package codes.spectrum.conf2022.parsers

import codes.spectrum.conf2022.IDocTypeParser
import codes.spectrum.conf2022.doc_type.DocType
import codes.spectrum.conf2022.output.ExtractedDocument

class T1Parser : IDocTypeParser {
    override val docType: DocType = DocType.T1

    override fun parse(input: String): ExtractedDocument? {
        val normalized = normalizeInput(input)
        if (normalized.startsWith("BTT1") || normalized.startsWith("BTT0")) {
            if (normalized.length == 8 || normalized.length == 9) {
                val split = normalized.substring(4)
                val t1Valid = split.first() == '5' && split.last() == '7'
                return ExtractedDocument(docType, normalized, true, t1Valid)
            }
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
