package net.olegg.adventofcode.year2016.day7

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2016.DayOf2016

/**
 * Created by olegg on 1/21/17.
 */
class Day7 : DayOf2016(7) {

    val abba = ".*([a-z])(?!\\1)([a-z])(\\2)(\\1).*".toRegex()
    val xabba = ".*\\[[^\\]]*([a-z])(?!\\1)([a-z])(\\2)(\\1)[^\\[]*\\].*".toRegex()

    override fun first(): String {
        return data.split("\n")
                .filterNot { it.matches(xabba) }
                .filter { it.matches(abba) }
                .count()
                .toString()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day7::class)
