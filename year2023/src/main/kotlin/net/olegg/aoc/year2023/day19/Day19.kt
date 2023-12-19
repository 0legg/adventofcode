package net.olegg.aoc.year2023.day19

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.toPair
import net.olegg.aoc.year2023.DayOf2023

/**
 * See [Year 2023, Day 19](https://adventofcode.com/2023/day/19)
 */
object Day19 : DayOf2023(19) {
  override fun first(): Any? {
    val (rulesBlock, partsBlock) = data.split("\n\n")
    val rules = rulesBlock.lines()
      .associate { line ->
        val name = line.substringBefore('{')
        val rulesRaw = line.substringAfter('{').substringBefore('}').split(",")

        val rulesList = rulesRaw.map { ruleRaw ->
          when {
            '<' in ruleRaw -> Rule.Less(
              stat = ruleRaw.substringBefore('<'),
              value = ruleRaw.substringAfter('<').substringBefore(':').toInt(),
            ) to ruleRaw.substringAfter(':')
            '>' in ruleRaw -> Rule.More(
              stat = ruleRaw.substringBefore('>'),
              value = ruleRaw.substringAfter('>').substringBefore(':').toInt(),
            ) to ruleRaw.substringAfter(':')
            else -> Rule.Always to ruleRaw
          }
        }

        name to rulesList
      }

    val parts = partsBlock.lines()
      .map { line ->
        val stats = line.drop(1).dropLast(1).split(",")
        Part(stats.associate { stat -> stat.split("=").toPair().let { it.first to it.second.toInt() } })
      }

    return parts
      .filter { part ->
        var curr = "in"
        do {
          val rule = rules.getValue(curr)
          curr = rule.first { it.first.accept(part) }.second
        } while (curr != "A" && curr != "R")

        curr == "A"
      }
      .sumOf { it.x + it.m + it.a + it.s }
  }

  sealed interface Rule {
    fun accept(part: Part): Boolean

    data class Less(
      val stat: String,
      val value: Int,
    ) : Rule {
      override fun accept(part: Part): Boolean {
        return part.map.getOrDefault(stat, 0) < value
      }
    }

    data class More(
      val stat: String,
      val value: Int,
    ) : Rule {
      override fun accept(part: Part): Boolean {
        return part.map.getOrDefault(stat, 0) > value
      }
    }

    data object Always : Rule {
      override fun accept(part: Part): Boolean = true
    }
  }

  data class Part(
    val map: Map<String, Int>,
  ) {
    val x: Int by map
    val m: Int by map
    val a: Int by map
    val s: Int by map
  }
}

fun main() = SomeDay.mainify(Day19)
