package net.olegg.adventofcode.year2016.day13

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2016.DayOf2016

class Day13 : DayOf2016(13) {
    override fun first(data: String): String {
        val fav = data.toInt()

        val moves = listOf(
                0 to 1,
                0 to -1,
                1 to 0,
                -1 to 0
        )

        val target = 31 to 39

        val known = mutableMapOf<Pair<Int, Int>, Int>()
        val queue = mutableListOf(Triple(1, 1, 0))

        do {
            val (x, y, step) = queue.removeAt(0)

            val next = moves
                    .map { x + it.first to y + it.second }
                    .filter { it.first >= 0 && it.second >= 0 }
                    .filter { isOpen(it.first, it.second, fav) }
                    .filter { !known.contains(it) }

            next.forEach {
                known.put(it, step + 1)
                queue.add(Triple(it.first, it.second, step + 1))
            }
        } while (!known.contains(target))

        return known[target].toString()
    }

    fun isOpen(x: Int, y: Int, fav: Int) =
            Integer.bitCount(x * x + 3 * x + 2 * x * y + y + y * y + fav) % 2 == 0
}

fun main(args: Array<String>) = SomeDay.mainify(Day13::class)
