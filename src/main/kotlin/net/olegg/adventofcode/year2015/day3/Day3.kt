package net.olegg.adventofcode.year2015.day3

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2015.DayOf2015
import net.olegg.adventofcode.utils.scan

/**
 * Created by olegg on 12/18/15.
 */
data class Vector(val x: Int = 0, val y: Int = 0) {
    operator fun plus(other: Vector) = Vector(x + other.x, y + other.y)
}

class Day3 : DayOf2015(3) {
    val mapping = mapOf(
            '<' to Vector(-1, 0),
            '>' to Vector(1, 0),
            '^' to Vector(0, 1),
            'v' to Vector(0, -1)
    )
    val moves = data.map { mapping[it] ?: Vector() }

    fun visit(moves: List<Vector>): Set<Vector> {
        return setOf(Vector()) + moves.scan(Vector()) { pos, move -> pos + move }
    }

    override fun first(): String {
        return visit(moves).size.toString()
    }

    override fun second(): String {
        return (visit(moves.filterIndexed { i, _ -> i % 2 == 0 }) + visit(moves.filterIndexed { i, _ -> i % 2 == 1 })).size.toString()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day3::class)
