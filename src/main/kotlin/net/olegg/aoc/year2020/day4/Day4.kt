package net.olegg.aoc.year2020.day4

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.toPair
import net.olegg.aoc.year2020.DayOf2020

/**
 * See [Year 2020, Day 4](https://adventofcode.com/2020/day/4)
 */
object Day4 : DayOf2020(4) {
  private val HEIGHT_PATTERN = "^([0-9]+)(cm|in)$".toRegex()
  private val COLOR_PATTERN = "^#[0-9a-f]{6}$".toRegex()
  private val ID_PATTERN = "^[0-9]{9}$".toRegex()

  override fun first(): Any? {
    val passports = data
      .trim()
      .split("\n\n")
      .map { it.replace("\n", " ") }
      .map { line -> line.split(" ").associate { it.split(":").toPair() } }
    return passports.count { (it - "cid").size == 7 }
  }

  override fun second(): Any? {
    val passports = data
      .trim()
      .split("\n\n")
      .map { it.replace("\n", " ") }
      .map { line -> line.split(" ").associate { it.split(":").toPair() } }

    return passports.count { (it - "cid").size == 7 && it.all { (key, value) -> isValid(key, value) } }
  }

  private fun isValid(field: String, value: String): Boolean {
    return try {
      when (field) {
        "byr" -> value.toInt() in 1920..2002
        "iyr" -> value.toInt() in 2010..2020
        "eyr" -> value.toInt() in 2020..2030
        "hgt" -> HEIGHT_PATTERN.find(value)?.destructured?.let { (length, measure) ->
          (measure == "in" && length.toInt() in 59..76) or (measure == "cm" && length.toInt() in 150..193)
        } ?: false
        "hcl" -> COLOR_PATTERN.matches(value)
        "ecl" -> value in setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
        "pid" -> ID_PATTERN.matches(value)
        "cid" -> true
        else -> false
      }
    } catch (_: Exception) {
      false
    }
  }
}

fun main() = SomeDay.mainify(Day4)
