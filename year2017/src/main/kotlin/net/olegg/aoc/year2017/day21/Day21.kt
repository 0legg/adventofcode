package net.olegg.aoc.year2017.day21

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2017.DayOf2017

/**
 * See [Year 2017, Day 21](https://adventofcode.com/2017/day/21)
 */
object Day21 : DayOf2017(21) {
  override fun first(): Any? {
    return countOn(5)
  }

  override fun second(): Any? {
    return countOn(18)
  }

  private fun countOn(iterations: Int): Int {
    val ops = lines
      .map { it.split(" => ") }
      .map { part -> part.map { row -> row.split("/").map { it.toList() }.toList() } }
      .associate { lex(it.first()) to it.last() }

    val sizes = ops.keys.map { it.size }.toSortedSet()

    val start = ".#./..#/###".split("/").map { it.toList() }

    return (0..<iterations)
      .fold(start) { acc, _ ->
        sizes.firstOrNull { acc.size % it == 0 }
          ?.let { size ->
            val chunks = acc.size / size
            acc.asSequence()
              .chunked(size)
              .map { rows ->
                val perRow = rows.map { it.chunked(size) }
                return@map (0..<chunks).map { cols ->
                  lex(perRow.map { it[cols] })
                }
              }
              .map { rows ->
                rows.map { row ->
                  ops[row] ?: ops.entries.first { it.key.size == size }.value
                }
              }
              .flatMap { rows ->
                (0..size).map { row ->
                  rows.flatMap { it[row] }
                }
              }
              .toList()
          } ?: acc
      }
      .sumOf { row -> row.count { it == '#' } }
  }

  private fun lex(grid: List<List<Char>>): List<List<Char>> {
    val size = grid.size

    val flips = setOf(
      grid,
      grid.reversed(),
      grid.map { it.reversed() },
      grid.reversed().map { it.reversed() },
    )

    val rotations = flips
      .flatMap { flip ->
        (0..<3).scan(flip) { acc, _ ->
          (0..<size).map { first ->
            (0..<size).map { second ->
              acc[second][first]
            }
          }
        }
      }
      .toSet()

    return rotations.minBy { rot ->
      rot.joinToString(separator = "") { it.joinToString(separator = "") }
    }
  }
}

fun main() = SomeDay.mainify(Day21)
