package net.olegg.adventofcode.year2017.day21

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.utils.scan
import net.olegg.adventofcode.year2017.DayOf2017

/**
 * See [Year 2017, Day 21](https://adventofcode.com/2017/day/21)
 */
class Day21 : DayOf2017(21) {
  override fun first(data: String): Any? {
    return countOn(data, 5)
  }

  override fun second(data: String): Any? {
    return countOn(data, 18)
  }

  fun countOn(data: String, iterations: Int): Int {
    val ops = data
        .trim()
        .lines()
        .map { it.split(" => ") }
        .map { part -> part.map { row -> row.split("/").map { it.toList() }.toList() } }
        .map { lex(it.first()) to it.last() }
        .toMap()

    val sizes = ops.keys.map { it.size }.distinct().sorted()

    val start = ".#./..#/###".split("/").map { it.toList() }.toList()

    return (0 until iterations)
        .fold(start) { acc, _ ->
          sizes.firstOrNull { acc.size % it == 0 }
              ?.let { size ->
                val chunks = acc.size / size
                acc.chunked(size)
                    .map { rows ->
                      val perRow = rows.map { it.chunked(size) }
                      return@map (0 until chunks).map { cols ->
                        lex(perRow.map { it[cols] })
                      }
                    }.map { rows ->
                      rows.map { row ->
                        ops[row] ?: ops.entries.first { it.key.size == size }.value
                      }
                    }.map { rows ->
                      (0..size).map { row ->
                        rows.flatMap { it[row] }
                      }
                    }.flatten()
              } ?: acc
        }
        .sumBy { row -> row.count { it == '#' } }
  }

  private fun lex(grid: List<List<Char>>): List<List<Char>> {
    val size = grid.size

    val flips = setOf(
        grid,
        grid.reversed(),
        grid.map { it.reversed() },
        grid.reversed().map { it.reversed() }
    )

    val rotations = flips
        .map { flip ->
          (0..3).scan(flip) { acc, _ ->
            (0 until size).map { first ->
              (0 until size).map { second ->
                acc[second][first]
              }
            }
          }.toSet()
        }
        .flatten()
        .toSet()

    return rotations
        .sortedBy { rot -> rot.joinToString(separator = "") { it.joinToString(separator = "") } }
        .first()
  }
}

fun main() = SomeDay.mainify(Day21::class)
