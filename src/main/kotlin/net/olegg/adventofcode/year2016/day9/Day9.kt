package net.olegg.adventofcode.year2016.day9

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2016.DayOf2016

/**
 * @see <a href="http://adventofcode.com/2016/day/9">Year 2016, Day 9</a>
 */
class Day9 : DayOf2016(9) {
    val pattern = "\\((\\d+)x(\\d+)\\)".toPattern()

    override fun first(data: String): Any? {
        val input = data.replace("\\s".toRegex(), "")
        return measure(input, 0, input.length, false).toString()
    }

    override fun second(data: String): Any? {
        val input = data.replace("\\s".toRegex(), "")
        return measure(input, 0, input.length, true).toString()
    }

    fun measure(data: String, start: Int, end: Int, unfold: Boolean): Long {
        pattern.matcher(data).let {
            it.region(start, end)
            if (it.find()) {
                with(it.toMatchResult()) {
                    return if (unfold) {
                        (start(1) - start - 1) +
                                group(2).toLong() * measure(data, end(2) + 1, end(2) + 1 + group(1).toInt(), true) +
                                measure(data, end(2) + 1 + group(1).toInt(), end, true)
                    } else {
                        (start(1) - start - 1) +
                                group(1).toInt() * group(2).toLong() +
                                measure(data, end(2) + 1 + group(1).toInt(), end, false)
                    }
                }
            } else {
                return (end - start).toLong()
            }
        }
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day9::class)
