package net.olegg.adventofcode.year2017.day8

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2017.DayOf2017

/**
 * See [Year 2017, Day 8](https://adventofcode.com/2017/day/8)
 */
class Day8 : DayOf2017(8) {
  override fun first(data: String): Any? {
    val registers = mutableMapOf<String, Int>()

    data.lines()
        .map { it.split("\\s".toRegex()) }
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

    return data.lines()
        .map { it.split("\\s".toRegex()) }
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

fun main(args: Array<String>) = SomeDay.mainify(Day8::class)
