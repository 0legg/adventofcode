package year2016.day5

import someday.SomeDay
import utils.md5
import year2016.DayOf2016

/**
 * Created by olegg on 12/4/16.
 */
class Day5 : DayOf2016(5) {
    override fun first(): String {
        return generateSequence(0) { it + 1 }
                .map{ "$data${it}".md5() }
                .filter { it.startsWith("00000") }
                .take(8)
                .map { it[5] }
                .joinToString(separator = "")
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day5::class.java)