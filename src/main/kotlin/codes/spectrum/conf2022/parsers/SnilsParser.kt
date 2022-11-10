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
            val nine = first!!.get(2).toInt()
            val eigth = first.get(1).toInt()
            val seven = first.get(0).toInt()

            val six = second!!.get(2).toInt()
            val five = second.get(1).toInt()
            val four = second.get(0).toInt()

            val three = third!!.get(2).toInt()
            val two = third.get(1).toInt()
            val one = third.get(0).toInt()


            val checkFromSnils = check?.toInt() ?: 0

            val checkSum =
                (nine * 9 + eigth * 8 + seven * 7 + six * 6 + five * 5 + four * 4 + three * 3 + two * 2 + one) % 101

            return if (checkFromSnils == checkSum) {
                ExtractedDocument(docType, maybeSnils, true, true)
            } else {
                ExtractedDocument(docType, maybeSnils, true, false)
            }
        }
    }
}
