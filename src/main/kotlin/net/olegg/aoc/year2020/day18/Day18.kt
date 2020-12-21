package net.olegg.aoc.year2020.day18

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2020.DayOf2020

/**
 * See [Year 2020, Day 18](https://adventofcode.com/2020/day/18)
 */
object Day18 : DayOf2020(18) {
  override fun first(data: String): Any? {
    val entries = data
      .trim()
      .lines()

    return solve(entries) { when(it) {
      '*' -> 1
      '+' -> 1
      else -> -1
    } }
  }

  override fun second(data: String): Any? {
    val entries = data
      .trim()
      .lines()

    return solve(entries) { when(it) {
      '*' -> 1
      '+' -> 2
      else -> -1
    } }
  }

  private fun solve(entries: List<String>, precedence: (Char) -> Int): Long {
    val values = entries.map { entry ->
      val numQueue = ArrayDeque<Char>()
      val opQueue = ArrayDeque<Char>()

      entry.reversed().forEach { char ->
        when (char) {
          in '0'..'9' -> numQueue += char
          ')' -> opQueue += char
          '+', '*' -> {
            val cp = precedence(char)
            while (opQueue.isNotEmpty() && precedence(opQueue.last()) > cp) {
              numQueue += opQueue.removeLast()
            }
            opQueue += char
          }
          '(' -> {
            while (opQueue.last() != ')') {
              numQueue += opQueue.removeLast()
            }
            opQueue.removeLast()
          }
        }
      }

      while (opQueue.isNotEmpty()) {
        numQueue += opQueue.removeLast()
      }

      val valStack = ArrayDeque<Long>()
      numQueue.forEach { op ->
        when (op) {
          in '0'..'9' -> valStack += (op - '0').toLong()
          '+' -> valStack += (valStack.removeLast() + valStack.removeLast())
          '*' -> valStack += (valStack.removeLast() * valStack.removeLast())
        }
      }

      return@map valStack.first()
    }

    return values.sum()
  }
}

fun main() = SomeDay.mainify(Day18)
