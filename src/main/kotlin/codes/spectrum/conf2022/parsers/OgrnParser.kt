package codes.spectrum.conf2022.parsers

import codes.spectrum.conf2022.IDocTypeParser
import codes.spectrum.conf2022.doc_type.DocType
import codes.spectrum.conf2022.output.ExtractedDocument

class OgrnParser : IDocTypeParser {
    override val docType: DocType = DocType.OGRN

    override fun parse(input: String): ExtractedDocument? {
        val normalized = normalizeInput(input)
        if (normalized.length != 13) return null

        var isValid = true
        val firstLetter = normalized.first()
        if (firstLetter == '0') isValid = false

        val year = normalized.substring(1,2).toInt()
        if (year in 23..90) isValid = false

        val checkSum = normalized.last().toString().toInt()
        val checkedNum = normalized.dropLast(1).toLong()

        val calculatedSum = checkedNum.mod(11).let {
            if (it == 10) 0 else it
        }

        if (calculatedSum != checkSum) isValid = false

        return ExtractedDocument(docType, normalized, true, isValid)


    }

    private val regex = """\b[A-Z0-9]{17}\b""".toRegex()



    private fun normalizeInput(input: String): String =
        input.replace("[^0-9]".toRegex(), "")
}
