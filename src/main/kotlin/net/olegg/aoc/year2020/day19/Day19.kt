package net.olegg.aoc.year2020.day19

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.utils.toPair
import net.olegg.aoc.year2020.DayOf2020
import net.olegg.aoc.year2020.day19.Day19.Rule.CharRule
import net.olegg.aoc.year2020.day19.Day19.Rule.RefRule

/**
 * See [Year 2020, Day 19](https://adventofcode.com/2020/day/19)
 */
object Day19 : DayOf2020(19) {
  private val CHAR_RULE_PATTERN = "\"(.)\"".toRegex()

  override fun first(data: String): Any? {
    val (rulesList, messages) = data
      .trim()
      .split("\n\n")

    val rules = rulesList
      .lines()
      .map { it.split(": ").toPair() }
      .map { (num, rule) -> num.toInt() to when {
        CHAR_RULE_PATTERN.matches(rule) -> CharRule(rule.trim('\"').first())
        else -> RefRule(rule.split('|').map { it.parseInts(" ") })
      } }
      .toMap()

    return solve(rules, messages.lines())
  }

  override fun second(data: String): Any? {
    val (rulesList, messages) = data
      .trim()
      .split("\n\n")

    val rules = rulesList
      .lines()
      .map { it.split(": ").toPair() }
      .map { (num, rule) -> num to when(num) {
        "8" -> "42 | 42 8"
        "11" -> "42 31 | 42 11 31"
        else -> rule
      } }
      .map { (num, rule) -> num.toInt() to when {
        CHAR_RULE_PATTERN.matches(rule) -> CharRule(rule.trim('\"').first())
        else -> RefRule(rule.split('|').map { it.parseInts(" ") })
      } }
      .toMap()

    return solve(rules, messages.lines())
  }

  private fun solve(rules: Map<Int, Rule>, messages: List<String>): Int {
    return messages.count { message ->
      val cache = mutableMapOf<Triple<Int, Int, Int>, Boolean>()

      fun matches(from: Int, to: Int, ruleNum: Int): Boolean {
        fun matches(from: Int, to: Int, rules: List<Int>): Boolean {
          return when (rules.size) {
            0 -> false
            1 -> matches(from, to, rules.first())
            else -> (from + 1 until to).any { mid ->
              matches(from, mid, rules.first()) && matches(mid, to, rules.drop(1))
            }
          }
        }

        return cache.getOrPut(Triple(from, to, ruleNum)) {
          when (val rule = rules[ruleNum]) {
            is CharRule -> to - from == 1 && message[from] == rule.char
            is RefRule -> rule.refs.any { matches(from, to, it) }
            else -> false
          }
        }
      }

      return@count matches(0, message.length, 0)
    }
  }

  sealed class Rule {
    data class CharRule(val char: Char): Rule()
    data class RefRule(val refs: List<List<Int>>): Rule()
  }
}

fun main() = SomeDay.mainify(Day19)
