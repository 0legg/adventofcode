package net.olegg.aoc.year2018.day14

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2018.DayOf2018

/**
 * See [Year 2018, Day 14](https://adventofcode.com/2018/day/14)
 */
object Day14 : DayOf2018(14) {
  override fun first(): Any? {
    val rounds = data.toInt()

    val recipes = mutableListOf(3, 7)

    (1..rounds).fold(0 to 1) { (first, second), _ ->
      val next = recipes[first] + recipes[second]
      if (next > 9) {
        recipes += (next / 10)
      }
      recipes += (next % 10)
      val (newFirst, newSecond) =
        (first + recipes[first] + 1) % recipes.size to (second + recipes[second] + 1) % recipes.size
      return@fold (newFirst to newSecond)
    }
    return recipes.subList(rounds, rounds + 10).joinToString(separator = "")
  }

  override fun second(): Any? {
    val tail = data

    val recipes = mutableListOf(3, 7)
    val builder = StringBuilder("37")

    (1..1_000_000_000).fold(0 to 1) { (first, second), _ ->
      val next = recipes[first] + recipes[second]
      if (next > 9) {
        recipes += (next / 10)
        builder.append(next / 10)
        if (builder.endsWith(tail)) {
          return builder.length - tail.length
        }
      }
      recipes += (next % 10)
      builder.append(next % 10)
      if (builder.endsWith(tail)) {
        return builder.length - tail.length
      }
      val (newFirst, newSecond) =
        (first + recipes[first] + 1) % recipes.size to (second + recipes[second] + 1) % recipes.size
      return@fold (newFirst to newSecond)
    }

    return null
  }
}

fun main() = SomeDay.mainify(Day14)
