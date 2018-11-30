package net.olegg.adventofcode.year2015.day21

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2015.DayOf2015

/**
 * @see <a href="http://adventofcode.com/2015/day/21">Year 2015, Day 21</a>
 */
class Day21 : DayOf2015(21) {
    private val hp = 100
    private val boss = data.trim().lines().map { it.substringAfterLast(": ").toInt() }
    private val weapons = listOf(
            Triple(8, 4, 0),
            Triple(10, 5, 0),
            Triple(25, 6, 0),
            Triple(40, 7, 0),
            Triple(74, 8, 0)
    )

    private val armor = listOf(
            Triple(13, 0, 1),
            Triple(31, 0, 2),
            Triple(53, 0, 3),
            Triple(75, 0, 4),
            Triple(102, 0, 5),
            Triple(0, 0, 0)
    )

    private val rings = listOf(
            Triple(25, 1, 0),
            Triple(50, 2, 0),
            Triple(100, 3, 0),
            Triple(20, 0, 1),
            Triple(40, 0, 2),
            Triple(80, 0, 3),
            Triple(0, 0, 0)
    )

    private val ringBuilds = rings
            .flatMap { first -> rings.map { second -> Pair(first, second) } }
            .filter { (left, right) -> left.first < right.first }
            .map { (left, right) -> left + right } +
            Triple(0, 0, 0)
    private val builds = weapons
            .flatMap { weapon -> armor.map { armor -> weapon + armor } }
            .flatMap { set -> ringBuilds.map { ringBuild -> set + ringBuild } }
            .sortedBy { it.first }

    override fun first(data: String): Any? {
        return builds.first { (_, damage, armor) ->
            val my = (damage - boss[2]).coerceAtLeast(1)
            val his = (boss[1] - armor).coerceAtLeast(1)
            (boss[0] + my - 1) / my <= (hp + his - 1) / his
        }.first
    }

    override fun second(data: String): Any? {
        return builds.reversed().first { (_, damage, armor) ->
            val my = (damage - boss[2]).coerceAtLeast(1)
            val his = (boss[1] - armor).coerceAtLeast(1)
            (boss[0] + my - 1) / my > (hp + his - 1) / his
        }.first
    }
}

operator fun Triple<Int, Int, Int>.plus(other: Triple<Int, Int, Int>) =
        Triple(first + other.first, second + other.second, third + other.third)

fun main(args: Array<String>) = SomeDay.mainify(Day21::class)
