package day4

import someday.SomeDay
import java.math.BigInteger
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

/**
 * Created by olegg on 12/18/15.
 */
class Day4: SomeDay(4) {
    override fun first(): String {
        return generateSequence(1) { it + 1 }.first { (data + it.toString()).md5().startsWith("00000") }.toString()
    }

    override fun second(): String {
        return generateSequence(1) { it + 1 }.first { (data + it.toString()).md5().startsWith("000000") }.toString()
    }
}

fun String.md5() = "%032x".format(BigInteger(1, MessageDigest.getInstance("MD5").digest(this.toByteArray(StandardCharsets.UTF_8))))

fun main(args: Array<String>) {
    val day = Day4()
    println(day.first())
    println(day.second())
}