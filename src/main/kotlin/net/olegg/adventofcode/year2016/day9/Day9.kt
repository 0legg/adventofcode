package net.olegg.adventofcode.year2016.day9

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2016.DayOf2016

/**
 * @see <a href="http://adventofcode.com/2016/day/9">Year 2016, Day 9</a>
 */
class Day9 : DayOf2016(9) {
    val pattern = "\\((\\d+)x(\\d+)\\)".toPattern()

    override fun first(data: String): String {
        val input = data.replace("\\s".toRegex(), "")
        var position = 0
        val matcher = pattern.matcher(input)
        val output = StringBuilder()
        while (position < input.length && matcher.find(position)) {
            val result = matcher.toMatchResult()
            output.append(input.substring(position, result.start(1) - 1))
            (0 until result.group(2).toInt()).forEach {
                output.append(input.substring(result.end(2) + 1, result.end(2) + 1 + result.group(1).toInt()))
            }
            position = result.end(2) + 1 + result.group(1).toInt()
        }
        output.append(input.substring(position))
        return output.length.toString()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day9::class)
