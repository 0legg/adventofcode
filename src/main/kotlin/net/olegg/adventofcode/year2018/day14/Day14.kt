package net.olegg.adventofcode.year2018.day14

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2018.DayOf2018

/**
 * @see <a href="http://adventofcode.com/2018/day/14">Year 2018, Day 14</a>
 */
class Day14 : DayOf2018(14) {
  override fun first(data: String): Any? {
    val rounds = data.trim().toInt()

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

  override fun second(data: String): Any? {
    val tail = data.trim()

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

fun main(args: Array<String>) = SomeDay.mainify(Day14::class)
