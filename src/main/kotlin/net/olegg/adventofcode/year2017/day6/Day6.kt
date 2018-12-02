package net.olegg.adventofcode.year2017.day6

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2017.DayOf2017

/**
 * @see <a href="http://adventofcode.com/2017/day/6">Year 2017, Day 6</a>
 */
class Day6 : DayOf2017(6) {
    override fun first(data: String): Any? {
        val blocks = data.split("\\s+".toRegex())
                .map { it.toInt() }

        val seen = mutableSetOf<List<Int>>()
        var curr = blocks
        var steps = 0
        while (curr !in seen) {
            seen.add(curr)
            steps += 1
            val max = curr.max() ?: 0
            val position = curr.indexOfFirst { it == max }

            curr = curr.mapIndexed { index, value ->
                val add = max / curr.size
                val bonus = max % curr.size

                return@mapIndexed add +
                        (if (index == position) 0 else value) +
                        (if (index in (position + 1)..(position + bonus)) 1 else 0) +
                        (if ((position + bonus >= curr.size) && (index in 0..(position + bonus) % curr.size)) 1 else 0)
            }
        }
        return steps.toString()
    }

    override fun second(data: String): Any? {
        val blocks = data.split("\\s+".toRegex())
                .map { it.toInt() }

        val seen = mutableMapOf<List<Int>, Int>()
        var curr = blocks
        var steps = 0
        while (curr !in seen) {
            seen.put(curr, steps)
            steps += 1
            val max = curr.max() ?: 0
            val position = curr.indexOfFirst { it == max }

            curr = curr.mapIndexed { index, value ->
                val add = max / curr.size
                val bonus = max % curr.size

                return@mapIndexed add +
                        (if (index == position) 0 else value) +
                        (if (index in (position + 1)..(position + bonus)) 1 else 0) +
                        (if ((position + bonus >= curr.size) && (index in 0..(position + bonus) % curr.size)) 1 else 0)
            }
        }
        return (steps - (seen[curr] ?: 0)).toString()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day6::class)
