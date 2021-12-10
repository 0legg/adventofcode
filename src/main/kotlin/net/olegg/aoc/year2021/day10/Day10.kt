package net.olegg.aoc.year2021.day10

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2021.DayOf2021

/**
 * See [Year 2021, Day 10](https://adventofcode.com/2021/day/10)
 */
object Day10 : DayOf2021(10) {
  override fun first(data: String): Any? {
    return data.trim().lines().sumOf { line ->
      val queue = ArrayDeque<Char>()
      line.forEach { char ->
        when (char) {
          in "(<[{" -> queue.addLast(char)
            ')' -> if (queue.lastOrNull() == '(') queue.removeLast() else return@sumOf 3L
            ']' -> if (queue.lastOrNull() == '[') queue.removeLast() else return@sumOf 57L
            '}' -> if (queue.lastOrNull() == '{') queue.removeLast() else return@sumOf 1197L
            '>' -> if (queue.lastOrNull() == '<') queue.removeLast() else return@sumOf 25137L
        }
      }
      return@sumOf 0L
    }
  }
}

fun main() = SomeDay.mainify(Day10)
