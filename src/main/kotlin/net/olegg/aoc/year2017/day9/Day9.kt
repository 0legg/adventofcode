package net.olegg.aoc.year2017.day9

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2017.DayOf2017

/**
 * See [Year 2017, Day 9](https://adventofcode.com/2017/day/9)
 */
class Day9 : DayOf2017(9) {
  enum class State {
    GARBAGE,
    NORMAL
  }

  override fun first(data: String): Any? {
    return data
        .replace("!.".toRegex(), "")
        .fold(Triple(0, 0, State.NORMAL)) { acc, char ->
          when (acc.third) {
            State.GARBAGE -> if (char == '>') acc.copy(third = State.NORMAL) else acc
            State.NORMAL -> when (char) {
              '<' -> acc.copy(third = State.GARBAGE)
              '{' -> acc.copy(second = acc.second + 1)
              '}' -> acc.copy(first = acc.first + acc.second, second = acc.second - 1)
              else -> acc
            }
          }
        }
        .first
  }

  override fun second(data: String): Any? {
    return data
        .replace("!.".toRegex(), "")
        .fold(Pair(0, State.NORMAL)) { acc, char ->
          when (acc.second) {
            State.GARBAGE -> when (char) {
              '>' -> acc.copy(second = State.NORMAL)
              else -> acc.copy(first = acc.first + 1)
            }
            State.NORMAL -> when (char) {
              '<' -> acc.copy(second = State.GARBAGE)
              else -> acc
            }
          }
        }
        .first
  }
}

fun main() = SomeDay.mainify(Day9::class)
