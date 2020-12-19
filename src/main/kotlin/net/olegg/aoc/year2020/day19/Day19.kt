package net.olegg.aoc.year2020.day19

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.utils.toPair
import net.olegg.aoc.year2020.DayOf2020

/**
 * See [Year 2020, Day 19](https://adventofcode.com/2020/day/19)
 */
object Day19 : DayOf2020(19) {
  override fun first(data: String): Any? {
    val (rulesList, messages) = data
      .trim()
      .split("\n\n")

    var rules = rulesList
      .lines()
      .map { it.split(": ").toPair() }
      .map { (num, rule) -> " $num " to (" ${rule.replace("\"", "").replace(" ", "  ")} ") }
      .toMap()

    while (rules[" 0 "].orEmpty().any { it.isDigit() }) {
      val toReplace = rules.entries.first { line -> line.value.none { it.isDigit() } }
      rules = rules.mapValues { (_, value) ->
        value.replace(toReplace.key, " (${toReplace.value}) " )
      } - toReplace.key
    }

    val rule = rules[" 0 "].orEmpty().replace(" ", "").toRegex()

    return messages.lines().count { rule.matches(it) }
  }
}

fun main() = SomeDay.mainify(Day19)
