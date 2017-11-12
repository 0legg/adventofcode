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
                .map { "$data$it".md5() }
                .filter { it.startsWith("00000") }
                .take(8)
                .map { it[5] }
                .joinToString(separator = "")
    }

    override fun second(): String {
        val map = hashMapOf<Char, Char>()
        return generateSequence(0) { it + 1 }
                .map { "$data$it".md5() }
                .filter { it.startsWith("00000") }
                .filter { it[5] in '0'..'7' }
                .map { it[5] to it[6] }
                .filter { map[it.first] == null }
                .map { map[it.first] = it.second; it }
                .takeWhile { map.size < 8 }
                .toList()
                .sortedBy { it.first }
                .map { it.second }
                .joinToString(separator = "")
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day5::class)
