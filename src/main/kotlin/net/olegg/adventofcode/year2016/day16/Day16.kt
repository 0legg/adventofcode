package net.olegg.adventofcode.year2016.day16

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2016.DayOf2016

/**
 * @see <a href="http://adventofcode.com/2016/day/16">Year 2016, Day 16</a>
 */
class Day16 : DayOf2016(16) {
    override fun first(data: String): String {
        val curve = generateSequence(data) {
            it + "0" + it.reversed().replace('0', '2').replace('1', '0').replace('2', '1')
        }
                .dropWhile { it.length <= 272 }
                .first()
                .substring(0, 272)

        return generateSequence(curve) {
            it.foldIndexed(listOf<Char>()) { index, acc, c ->
                if (index % 2 == 0) {
                    acc + listOf(c)
                } else {
                    acc.subList(0, acc.size - 1) + listOf(when ("${acc.last()}$c") {
                        "00", "11" -> '1'
                        "01", "10" -> '0'
                        else -> '2'
                    })
                }
            }.joinToString(separator = "")
        }.first { it.length % 2 == 1 }
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day16::class)
