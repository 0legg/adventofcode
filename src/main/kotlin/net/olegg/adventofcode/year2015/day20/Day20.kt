package net.olegg.adventofcode.year2015.day20

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2015.DayOf2015

/**
 * Created by olegg on 21/12/15.
 */
class Day20 : DayOf2015(20) {
    val max = data.toInt()

    override fun first(): String {
        return generateSequence(1) { it + 1 }.first { house ->
            (1..Math.sqrt(house.toDouble()).toInt()).sumBy { if (house % it == 0) (it + house / it) * 10 else 0 } >= max
        }.toString()
    }

    override fun second(): String {
        return generateSequence(1) { it + 1 }.first { house ->
            (1..Math.sqrt(house.toDouble()).toInt()).sumBy {
                if (house % it == 0) 11 * listOf(it, house / it).filter { house <= it * 50 }.sum() else 0
            } >= max
        }.toString()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day20::class)
