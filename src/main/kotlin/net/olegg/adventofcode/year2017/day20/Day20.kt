package net.olegg.adventofcode.year2017.day20

import kotlin.math.abs
import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2017.DayOf2017

/**
 * @see <a href="http://adventofcode.com/2017/day/20">Year 2017, Day 20</a>
 */
class Day20 : DayOf2017(20) {
    override fun first(data: String): Any? {
        val points = data.trim().lines()
                .map { line -> line.replace("[pva=<> ]".toRegex(), "") }
                .map { line -> line.split(",").map { it.toLong() } }
                .map { nums ->
                    Triple(
                        Triple(nums[0], nums[1], nums[2]),
                        Triple(nums[3], nums[4], nums[5]),
                        Triple(nums[6], nums[7], nums[8])
                    )
                }

        return (0..1_000)
                .fold(points) { acc, _ ->
                    acc.map { prev ->
                        val speed = Triple(
                                prev.second.first + prev.third.first,
                                prev.second.second + prev.third.second,
                                prev.second.third + prev.third.third
                        )
                        val point = Triple(
                                prev.first.first + speed.first,
                                prev.first.second + speed.second,
                                prev.first.third + speed.third
                        )
                        return@map prev.copy(first = point, second = speed)
                    }
                }
                .mapIndexed { index, triple -> index to triple }
                .minBy { abs(it.second.first.first) + abs(it.second.first.second) + abs(it.second.first.third) }
                ?.first
    }

    override fun second(data: String): Any? {
        val points = data.trim().lines()
                .map { line -> line.replace("[pva=<> ]".toRegex(), "") }
                .map { line -> line.split(",").map { it.toLong() } }
                .mapIndexed { index, nums ->
                    index to Triple(
                            Triple(nums[0], nums[1], nums[2]),
                            Triple(nums[3], nums[4], nums[5]),
                            Triple(nums[6], nums[7], nums[8])
                    )
                }

        return (0..1_000)
                .fold(points) { acc, _ ->
                    acc.map { (index, prev) ->
                        val speed = Triple(
                                prev.second.first + prev.third.first,
                                prev.second.second + prev.third.second,
                                prev.second.third + prev.third.third
                        )
                        val point = Triple(
                                prev.first.first + speed.first,
                                prev.first.second + speed.second,
                                prev.first.third + speed.third
                        )
                        return@map index to prev.copy(first = point, second = speed)
                    }
                    .groupBy { it.second.first }
                    .filterValues { it.size == 1 }
                    .values
                    .flatten()
                }
                .count()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day20::class)
