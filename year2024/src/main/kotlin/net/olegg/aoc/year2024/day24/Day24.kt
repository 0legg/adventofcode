package net.olegg.aoc.year2024.day24

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.utils.toPair
import net.olegg.aoc.utils.transpose
import net.olegg.aoc.year2024.DayOf2024
import kotlin.math.absoluteValue

/**
 * See [Year 2024, Day 24](https://adventofcode.com/2024/day/24)
 */
object Day24 : DayOf2024(24) {
  private val rulePattern = "(\\w+) (OR|XOR|AND) (\\w+) -> (\\w+)".toRegex()
  override fun first(): Any? {
    val (rawGates, rawRules) = data.split("\n\n")

    val initialValues = rawGates.lines()
      .associate { it.split(": ").toPair() }
      .mapValues { it.value == "1" }

    val rules = rawRules.lines()
      .mapNotNull { rulePattern.matchEntire(it) }
      .map { match ->
        val (a, op, b, res) = match.destructured
        Triple(op, a, b) to res
      }

    val nodes = initialValues.keys + rules.flatMap { listOf(it.first.first, it.first.second, it.second) }.toSet()
    val finalValues = initialValues.toMutableMap()
    val targets = nodes.filter { it.startsWith("z") }.toSet()
    var remainingRules = rules

    while (targets.any { it !in finalValues }) {
      val (applicable, nonApplicable) = remainingRules.partition { (rule, _) ->
        rule.second in finalValues && rule.third in finalValues
      }

      applicable.forEach { (rule, res) ->
        val (op, a, b) = rule
        finalValues[res] = op.calc(finalValues[a]!!, finalValues[b]!!)
      }

      remainingRules = nonApplicable
    }

    return targets.toSortedSet()
      .joinToString("") { if (finalValues[it]!!) "1" else "0" }
      .reversed()
      .toBigInteger(2)
  }

  private fun String.calc(a: Boolean, b: Boolean) = when (this) {
    "OR" -> a or b
    "XOR" -> a xor b
    "AND" -> a and b
    else -> error("Unknown operation $this")
  }
}

fun main() = SomeDay.mainify(Day24)
