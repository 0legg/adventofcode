package day3

import someday.SomeDay
import utils.scan

/**
 * Created by olegg on 12/18/15.
 */
data class Vector(val x: Int = 0, val y: Int = 0) {
    operator fun plus(other: Vector) = Vector(x + other.x, y + other.y)
}

class Day3: SomeDay(3) {
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
        return (visit(moves.filterIndexed { i, vector -> i % 2 == 0 }) + visit(moves.filterIndexed { i, vector -> i % 2 == 1 })).size.toString()
    }
}

fun main(args: Array<String>) {
    val day = Day3()
    println(day.first())
    println(day.second())
}