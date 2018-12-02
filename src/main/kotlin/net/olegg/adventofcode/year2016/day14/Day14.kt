package net.olegg.adventofcode.year2016.day14

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.utils.md5
import net.olegg.adventofcode.year2016.DayOf2016
import org.funktionale.memoization.memoize

/**
 * @see <a href="http://adventofcode.com/2016/day/14">Year 2016, Day 14</a>
 */
class Day14 : DayOf2016(14) {

    val match3 = "(.)(\\1)(\\1)".toRegex()

    override fun first(data: String): Any? {
        val hash = { n: Int ->
            "$data$n".md5()
        }.memoize()

        return solve(64, hash).toString()
    }

    override fun second(data: String): Any? {
        val hash = { n: Int ->
            (0..2016).fold("$data$n") { acc, _ ->
                acc.md5()
            }
        }.memoize()

        return solve(64, hash).toString()
    }

    fun solve(count: Int, hash: (Int) -> String): Int {
        return sequence {
            var i = 0
            while (true) {
                val curr = hash(i)
                match3.find(curr)?.let {
                    val next = it.groupValues[1].repeat(5)
                    if ((i + 1..i + 1000).any { hash(it).contains(next) }) {
                        yield(i)
                    }
                }
                i += 1
            } }
                .take(count)
                .last()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day14::class)
