package net.olegg.adventofcode.year2016.day4

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2016.DayOf2016
import kotlin.comparisons.compareBy

/**
 * @see <a href="http://adventofcode.com/2016/day/4">Year 2016, Day 4</a>
 */
class Day4 : DayOf2016(4) {
    val ROOM_PATTERN = "^(.+)-(\\d+)\\[(.+)\\]$".toPattern()
    override fun first(data: String): Any? {
        val rooms = data.lines().map {
            ROOM_PATTERN.matcher(it).let {
                it.find()
                Triple(it.group(1).replace("-", ""), it.group(2).toInt(), it.group(3))
            }
        }

        return rooms.filter {
            it.third == it.first.toCharArray()
                    .groupBy { it }
                    .mapValues { it.value.size }
                    .toList()
                    .sortedWith(compareBy({ -it.second }, { it.first }))
                    .take(5)
                    .map { it.first }
                    .joinToString(separator = "")
        }.sumBy { it.second }
    }

    override fun second(data: String): Any? {
        val rooms = data.lines().map {
            ROOM_PATTERN.matcher(it).let {
                it.find()
                Triple(it.group(1), it.group(2).toInt(), it.group(3))
            }
        }

        return rooms.map { triple -> triple.first.toCharArray().map {
            if (it == '-') ' ' else ((it.toInt() - 'a'.toInt() + triple.second) % 26 + 'a'.toInt()).toChar()
        }.joinToString(separator = "") to triple.second }.joinToString(separator = "\n")
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day4::class)
