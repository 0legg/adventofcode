package net.olegg.adventofcode.year2017.day21

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.utils.scan
import net.olegg.adventofcode.year2017.DayOf2017

/**
 * @see <a href="http://adventofcode.com/2017/day/21">Year 2017, Day 21</a>
 */
class Day21 : DayOf2017(21) {
    override fun first(data: String): String {
        val ops = data.trim().lines()
                .map { it.split(" => ") }
                .map { it.map { it.split("/").map { it.toList() }.toList() } }
                .map { lex(it.first()) to it.last() }
                .toMap()

        val sizes = ops.keys.map { it.size }.distinct().sorted()

        val start = ".#./..#/###".split("/").map { it.toList() }.toList()

        return (0 until 5).fold(start) { acc, _ ->
            sizes.firstOrNull { acc.size % it == 0 }?.let { size ->
                val chunks = acc.size / size
                acc.chunked(size).map { rows ->
                    val perRow = rows.map { it.chunked(size) }
                    return@map (0 until chunks).map { cols ->
                        lex(perRow.map { it[cols] })
                    }
                }.map {
                    it.map { ops[it] ?: ops.entries.first { it.key.size == size }.value }
                }.map { rows ->
                    (0 until size + 1).map { row ->
                        rows.map { it[row] }.flatten()
                    }
                }.flatten()
            } ?: acc
        }.sumBy { it.count { it == '#' } }
                .toString()
    }

    fun lex(grid: List<List<Char>>): List<List<Char>> {
        val size = grid.size

        val flips = setOf(
                grid,
                grid.reversed(),
                grid.map { it.reversed() },
                grid.reversed().map { it.reversed() }
        )

        val rotations = flips.map {
            (0..3).scan(it) { acc, _ ->
                (0 until size).map { first ->
                    (0 until size).map { second ->
                        acc[second][first]
                    }.toList()
                }.toList()
            }.toSet()
        }.flatten().toSet()

        return rotations
                .sortedBy { it.joinToString(separator = "") { it.joinToString(separator = "") } }
                .first()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day21::class)
