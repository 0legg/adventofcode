package net.olegg.aoc.year2017.day8

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2017.DayOf2017

/**
 * See [Year 2017, Day 8](https://adventofcode.com/2017/day/8)
 */
object Day8 : DayOf2017(8) {
  override fun first(data: String): Any? {
    val registers = mutableMapOf<String, Int>()

    data
        .trim()
        .lines()
        .map { it.split(" ") }
        .forEach { list ->
          val oldValue = registers[list[0]] ?: 0
          val shift = list[2].toInt() * (if (list[1] == "dec") -1 else 1)

          val cmp = registers[list[4]] ?: 0
          val apply = when (list[5]) {
            "<" -> cmp < list[6].toInt()
            ">" -> cmp > list[6].toInt()
            "<=" -> cmp <= list[6].toInt()
            ">=" -> cmp >= list[6].toInt()
            "==" -> cmp == list[6].toInt()
            "!=" -> cmp != list[6].toInt()
            else -> false
          }

          registers[list[0]] = if (apply) oldValue + shift else oldValue
        }

    return registers.values.max()
  }

  override fun second(data: String): Any? {
    val registers = mutableMapOf<String, Int>()

    return data
        .trim()
        .lines()
        .map { it.split(" ") }
        .map { list ->
          val oldValue = registers[list[0]] ?: 0
          val shift = list[2].toInt() * (if (list[1] == "dec") -1 else 1)

          val cmp = registers[list[4]] ?: 0
          val apply = when (list[5]) {
            "<" -> cmp < list[6].toInt()
            ">" -> cmp > list[6].toInt()
            "<=" -> cmp <= list[6].toInt()
            ">=" -> cmp >= list[6].toInt()
            "==" -> cmp == list[6].toInt()
            "!=" -> cmp != list[6].toInt()
            else -> false
          }

          val newValue = if (apply) oldValue + shift else oldValue

          registers[list[0]] = newValue
          return@map newValue
        }
        .max()
  }
}

fun main() = SomeDay.mainify(Day8)
