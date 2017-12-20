package net.olegg.adventofcode.year2017.day20

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2017.DayOf2017
import kotlin.math.abs

/**
 * @see <a href="http://adventofcode.com/2017/day/20">Year 2017, Day 20</a>
 */
class Day20 : DayOf2017(20) {
    override fun first(data: String): String {
        val points = data.trim().lines()
                .map { it.replace("[pva=<> ]".toRegex(), "") }
                .map { it.split(",").map { it.toLong() } }
                .map { Triple(
                        Triple(it[0], it[1], it[2]),
                        Triple(it[3], it[4], it[5]),
                        Triple(it[6], it[7], it[8])
                ) }


        return (0..1_000).fold(points) { acc, _ ->
            acc.map {
                val speed = Triple(
                        it.second.first + it.third.first,
                        it.second.second + it.third.second,
                        it.second.third + it.third.third
                )
                val point = Triple(
                        it.first.first + speed.first,
                        it.first.second + speed.second,
                        it.first.third + speed.third
                )
                return@map it.copy(first = point, second = speed)
            }
        }
                .mapIndexed { index, triple -> index to triple }
                .minBy { abs(it.second.first.first) + abs(it.second.first.second) + abs(it.second.first.third) }
                ?.first
                .toString()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day20::class)
