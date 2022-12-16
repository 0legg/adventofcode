package net.olegg.aoc.year2020.day13

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseLongs
import net.olegg.aoc.year2020.DayOf2020

/**
 * See [Year 2020, Day 13](https://adventofcode.com/2020/day/13)
 */
object Day13 : DayOf2020(13) {
  override fun first(): Any? {
    val (startLine, busLine) = lines
    val start = startLine.toLong()
    val buses = busLine.parseLongs(",")

    return buses.map { it to ((start - 1) / it + 1) * it }
      .minBy { it.second }
      .let { it.first * (it.second - start) }
  }

  override fun second(): Any? {
    val (_, busLine) = lines
    val (nums, buses) = busLine.split(",")
      .mapIndexedNotNull { index, s -> s.toBigIntegerOrNull()?.let { index.toBigInteger() to it } }
      .unzip()

    val mod = buses.reduce { a, b -> a * b }
    val mis = buses.map { mod / it }
    val revs = buses.zip(mis) { bus, mi -> mi.modInverse(bus) }

    val sum = buses.zip(nums) { bus, num -> bus - (num % bus) }
      .zip(mis) { acc, mi -> acc * mi }
      .zip(revs) { acc, rev -> acc * rev }
      .sumOf { it }

    return sum % mod
  }
}

fun main() = SomeDay.mainify(Day13)
