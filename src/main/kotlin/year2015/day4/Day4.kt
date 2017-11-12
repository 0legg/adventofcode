package year2015.day4

import someday.SomeDay
import utils.md5
import year2015.DayOf2015

/**
 * Created by olegg on 12/18/15.
 */
class Day4 : DayOf2015(4) {
    override fun first(): String {
        return generateSequence(1) { it + 1 }.first { (data + it.toString()).md5().startsWith("00000") }.toString()
    }

    override fun second(): String {
        return generateSequence(1) { it + 1 }.first { (data + it.toString()).md5().startsWith("000000") }.toString()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day4::class)