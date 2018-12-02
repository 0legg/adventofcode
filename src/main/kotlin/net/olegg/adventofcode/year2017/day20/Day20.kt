package net.olegg.adventofcode.year2017.day20

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2017.DayOf2017
import kotlin.math.abs

/**
 * @see <a href="http://adventofcode.com/2017/day/20">Year 2017, Day 20</a>
 */
class Day20 : DayOf2017(20) {
    override fun first(data: String): Any? {
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

    override fun second(data: String): Any? {
        val points = data.trim().lines()
                .map { it.replace("[pva=<> ]".toRegex(), "") }
                .map { it.split(",").map { it.toLong() } }
                .mapIndexed { index, list ->
                    index to Triple(
                            Triple(list[0], list[1], list[2]),
                            Triple(list[3], list[4], list[5]),
                            Triple(list[6], list[7], list[8])
                    )
                }

        return (0..1_000).fold(points) { acc, _ ->
            acc.map {
                val speed = Triple(
                        it.second.second.first + it.second.third.first,
                        it.second.second.second + it.second.third.second,
                        it.second.second.third + it.second.third.third
                )
                val point = Triple(
                        it.second.first.first + speed.first,
                        it.second.first.second + speed.second,
                        it.second.first.third + speed.third
                )
                return@map it.first to it.second.copy(first = point, second = speed)
            }
                    .groupBy { it.second.first }
                    .filterValues { it.size == 1 }
                    .values
                    .flatten()
        }
                .count()
                .toString()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day20::class)
