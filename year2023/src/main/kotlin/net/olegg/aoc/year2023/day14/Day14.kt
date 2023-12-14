package net.olegg.aoc.year2023.day14

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2023.DayOf2023

/**
 * See [Year 2023, Day 14](https://adventofcode.com/2023/day/14)
 */
object Day14 : DayOf2023(14) {
  override fun first(): Any? {
    val shifted = matrix.transpose().shiftLeft()

    return shifted.transpose().northLoad()
  }

  override fun second(): Any? {
    val seen = mutableMapOf<List<List<Char>>, Int>()
    val rev = mutableMapOf<Int, List<List<Char>>>()

    var curr = matrix
    var step = 0

    while (curr !in seen){
      rev[step] = curr
      seen[curr] = step
      step++
      val north = curr.transpose().shiftLeft().transpose()
      val west = north.shiftLeft()
      val south = west.transpose().flip().shiftLeft().flip().transpose()
      curr = south.flip().shiftLeft().flip()
    }

    val head = seen.getValue(curr)
    val loop = step - head
    val tail = (1000000000 - head) % loop

    val final = rev.getValue(head + tail)

    return final.northLoad()
  }

  private fun List<List<Char>>.transpose(): List<List<Char>> {
    return List(first().size) { row ->
      List(size) { column -> this[column][row] }
    }
  }

  private fun List<List<Char>>.northLoad(): Int {
    return mapIndexed { y, row ->
      (size - y) * row.count { it == 'O' }
    }.sum()
  }

  private fun List<List<Char>>.shiftLeft() = map { row ->
    val total = StringBuilder()
    val round = StringBuilder()
    "#$row".reversed().forEach { char ->
      when (char) {
        '.' -> total.append(char)
        'O' -> round.append(char)
        '#' -> {
          total.append(round).append(char)
          round.setLength(0)
        }
      }
    }

    total.setLength(total.length - 1)
    total.reverse().toList()
  }

  private fun List<List<Char>>.flip() = map { it.asReversed() }
}

fun main() = SomeDay.mainify(Day14)
