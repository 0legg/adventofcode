package net.olegg.aoc.year2021.day4

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.year2021.DayOf2021

/**
 * See [Year 2021, Day 4](https://adventofcode.com/2021/day/4)
 */
object Day4 : DayOf2021(4) {
  override fun first(data: String): Any? {
    val values = data
      .lines()
      .first()
      .parseInts(",")
    val boards = data
      .split("\n\n")
      .drop(1)
      .map { it.replace("\\s+".toRegex(), " ").parseInts(" ").map { value -> value to false } }

    values.fold(boards) { acc, value ->
      val newAcc = acc.map {
        it.map { (curr, seen) -> if (curr == value) curr to true else curr to seen }
      }

      newAcc.forEach { board ->
        for (x in 0 until 5) {
          if ((0 until 5).all { y -> board[5 * y + x].second }) {
            return score(board, value)
          }
        }

        for (y in 0 until 5) {
          if ((0 until 5).all { x -> board[5 * y + x].second }) {
            return score(board, value)
          }
        }
      }

      newAcc
    }

    return 0
  }

  override fun second(data: String): Any? {
    val values = data
      .lines()
      .first()
      .parseInts(",")
    val boards = data
      .split("\n\n")
      .drop(1)
      .map { it.replace("\\s+".toRegex(), " ").parseInts(" ").map { value -> value to false } }

    values.fold(boards) { acc, value ->
      val newAcc = acc.map {
        it.map { (curr, seen) -> if (curr == value) curr to true else curr to seen }
      }

      val filteredAcc = newAcc.filter { board ->
        for (x in 0 until 5) {
          if ((0 until 5).all { y -> board[5 * y + x].second }) {
            return@filter false
          }
        }

        for (y in 0 until 5) {
          if ((0 until 5).all { x -> board[5 * y + x].second }) {
            return@filter false
          }
        }

        return@filter true
      }

      if (filteredAcc.isEmpty()) {
        return score(newAcc.first(), value)
      }

      filteredAcc
    }

    return 0
  }

  private fun score(board: List<Pair<Int, Boolean>>, value: Int) =
    board.filter { !it.second }.sumOf { it.first } * value
}

fun main() = SomeDay.mainify(Day4)
