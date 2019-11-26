package net.olegg.aoc.year2016.day7

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2016.DayOf2016

/**
 * See [Year 2016, Day 7](https://adventofcode.com/2016/day/7)
 */
class Day7 : DayOf2016(7) {

  val abba = ('a'..'z').flatMap { a -> ('a'..'z').filter { b -> a != b }.map { b -> "$a$b$b$a" } }
  val ababab = ('a'..'z').flatMap { a -> ('a'..'z').filter { b -> a != b }.map { b -> "$a$b$a" to "$b$a$b" } }

  fun splitAddresses(addresses: List<String>): List<Pair<List<String>, List<String>>> {
    return addresses.map { it.split("[", "]") }
        .map { it.mapIndexed { i, s -> i to s }.partition { it.first % 2 == 0 } }
        .map { it.first.map { it.second } to it.second.map { it.second } }
  }

  override fun first(data: String): Any? {
    return splitAddresses(data.lines())
        .filterNot { it.second.any { substr -> abba.any { substr.contains(it) } } }
        .filter { it.first.any { substr -> abba.any { substr.contains(it) } } }
        .count()
  }

  override fun second(data: String): Any? {
    return splitAddresses(data.lines())
        .filter { split ->
          ababab.any { ab ->
            split.first.any { it.contains(ab.first) } && split.second.any { it.contains(ab.second) }
          }
        }
        .count()
  }
}

fun main() = SomeDay.mainify(Day7::class)
