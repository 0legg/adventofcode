package net.olegg.adventofcode.year2015.day16

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2015.DayOf2015

/**
 * @see <a href="http://adventofcode.com/2015/day/16">Year 2015, Day 16</a>
 */
class Day16 : DayOf2015(16) {
    val sues = data.lines().map {
        it.substring("Sue ".length, it.indexOf(':')).toInt() to it.substring(it.indexOf(':') + 1).trim().split(", ").map { val split = it.split(": "); split[0] to split[1].toInt() }.toMap()
    }

    val footprint = mapOf(
            "children" to 3,
            "cats" to 7,
            "samoyeds" to 2,
            "pomeranians" to 3,
            "akitas" to 0,
            "vizslas" to 0,
            "goldfish" to 5,
            "trees" to 3,
            "cars" to 2,
            "perfumes" to 1
    )

    override fun first(data: String): Any? {
        return sues.filter { it.second.all { it.value == footprint[it.key] } }.map { it.first }.first()
    }

    override fun second(data: String): Any? {
        return sues.filter {
            it.second.all {
                when (it.key) {
                    "cats", "trees" -> (it.value > footprint[it.key] ?: 0)
                    "pomeranians", "goldfish" -> (it.value < footprint[it.key] ?: 0)
                    else -> it.value == footprint[it.key]
                }
            }
        }.map { it.first }.first()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day16::class)
