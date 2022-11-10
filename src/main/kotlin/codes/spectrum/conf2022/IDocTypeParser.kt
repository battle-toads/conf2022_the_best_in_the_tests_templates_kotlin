package codes.spectrum.conf2022

import codes.spectrum.conf2022.doc_type.DocType
import codes.spectrum.conf2022.output.ExtractedDocument

interface IDocTypeParser {
    val docType: DocType

    fun parse(input: String): ExtractedDocument?
}
