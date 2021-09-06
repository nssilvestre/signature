package signature

import java.io.File

class Font(filePath: String, val space: Int) {
    private val file = File(filePath)
    val font = file.readLines()
    val fontHeight = font.first().split(" ")[0].toInt()

    fun charPosition(c: Char): Int {
        val i = if (c.isLowerCase()) c.code - 97 else c.code - 65 + 26
        return i * (fontHeight + 1) + 1
    }

    fun getCharacterWidth(c: Char): Int = font[charPosition(c)].split(" ")[1].toInt()
}

class BadgeSignatureGenerator {
    private val name: String
    private val status: String
    private val fontRoman = Font("c:\\roman.txt", 10)
    private val fontMedium = Font("c:\\medium.txt", 5)
    private val borderLine = "8"

    init {
        print("Enter name and surname: ")
        name = readLine()!!.toString()
        print("Enter person's status: ")
        status = readLine()!!.toString()
    }

    private val nameWidth = getWordWidth(name, fontRoman)
    private val statusWidth = getWordWidth(status, fontMedium)
    private val badgeWidth = maxOf(nameWidth, statusWidth) + 8

    private fun getWordWidth(str: String, f: Font): Int {
        var width = 0
        for (l in str) {
            width += if (l == ' ') f.space else
                f.getCharacterWidth(l)
        }
        return width
    }

    private val alignmentLeft = (maxOf(nameWidth, statusWidth) - minOf(nameWidth, statusWidth)) / 2 + 2
    private val alignmentRight = (maxOf(nameWidth, statusWidth) - minOf(nameWidth, statusWidth) + 1) / 2 + 2

    fun create() {
        println(borderLine.repeat(badgeWidth))
        if (nameWidth > statusWidth) {
            printBadge(fontRoman, name)
            printBadge(fontMedium, status, alignmentLeft, alignmentRight )
        } else {
            printBadge(fontRoman, name, alignmentLeft, alignmentRight)
            printBadge(fontMedium, status)
        }
        println(borderLine.repeat(badgeWidth))
    }

    private fun printBadge(f: Font, str: String, align1: Int = 2, align2: Int = 2) {
        for (i in 1..f.fontHeight) {
            print(borderLine.repeat(2) + " ".repeat(align1))
            for (l in str) {
                if (l == ' ') print(" ".repeat(f.space)) else
                print(f.font[f.charPosition(l) + i])
            }
            println(" ".repeat(align2) + borderLine.repeat(2))
        }
    }
}

fun main() {
    val newBadge = BadgeSignatureGenerator()
    newBadge.create()
}