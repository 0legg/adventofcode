package day3

import someday.SomeDay

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

    override fun first(): String {
        val visited = setOf(Vector()).toMutableSet()
        moves.fold(Vector()) { pos, move -> val next = pos + move; visited += next; next }
        return visited.size.toString()
    }
}

fun main(args: Array<String>) {
    val day = Day3()
    println(day.first())
}