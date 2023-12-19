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

  override fun second(): Any? {
    val rulesBlock = data.substringBefore("\n\n")
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

    val queue = ArrayDeque(
      listOf(
        "in" to PartRanges(
          map = mapOf(
            "x" to listOf(1..4000),
            "m" to listOf(1..4000),
            "a" to listOf(1..4000),
            "s" to listOf(1..4000),
          )
        ),
      ),
    )

    val result = mutableListOf<PartRanges>()

    while (queue.isNotEmpty()) {
      val (node, curr) = queue.removeFirst()
      if (node == "R") {
        continue
      }
      if (curr == PartRanges.Empty) {
        continue
      }
      if (node == "A") {
        result += curr
        continue
      }

      rules.getValue(node).fold(curr) { acc, (rule, target) ->
        val (accept, reject) = rule.partition(acc)
        if (accept != PartRanges.Empty) {
          queue.add(target to accept)
        }
        reject
      }
    }

    return result.sumOf { partRanges ->
      partRanges.map.values
        .map { ranges -> ranges.sumOf { it.count().toLong() } }
        .reduce(Long::times)
    }
  }

  sealed interface Rule {
    fun accept(part: Part): Boolean

    fun partition(partRanges: PartRanges): Pair<PartRanges, PartRanges>

    data class Less(
      val stat: String,
      val value: Int,
    ) : Rule {
      override fun accept(part: Part): Boolean {
        return part.map.getOrDefault(stat, 0) < value
      }

      override fun partition(partRanges: PartRanges): Pair<PartRanges, PartRanges> {
        val accept = mutableListOf<IntRange>()
        val reject = mutableListOf<IntRange>()

        partRanges.map.getOrDefault(stat, emptyList()).forEach { range ->
          when {
            range.first > value -> reject.add(range)
            range.last < value -> accept.add(range)
            else -> {
              accept.add(range.first..<value)
              reject.add(value..range.last)
            }
          }
        }

        val acceptParts = if (accept.isNotEmpty()) {
          partRanges.copy(map = partRanges.map + (stat to accept))
        } else {
          PartRanges.Empty
        }

        val rejectParts = if (accept.isNotEmpty()) {
          partRanges.copy(map = partRanges.map + (stat to reject))
        } else {
          PartRanges.Empty
        }

        return acceptParts to rejectParts
      }
    }

    data class More(
      val stat: String,
      val value: Int,
    ) : Rule {
      override fun accept(part: Part): Boolean {
        return part.map.getOrDefault(stat, 0) > value
      }

      override fun partition(partRanges: PartRanges): Pair<PartRanges, PartRanges> {
        val accept = mutableListOf<IntRange>()
        val reject = mutableListOf<IntRange>()

        partRanges.map.getOrDefault(stat, emptyList()).forEach { range ->
          when {
            range.first > value -> accept.add(range)
            range.last < value -> reject.add(range)
            else -> {
              reject.add(range.first ..value)
              accept.add(value + 1..range.last)
            }
          }
        }

        val acceptParts = if (accept.isNotEmpty()) {
          partRanges.copy(map = partRanges.map + (stat to accept))
        } else {
          PartRanges.Empty
        }

        val rejectParts = if (accept.isNotEmpty()) {
          partRanges.copy(map = partRanges.map + (stat to reject))
        } else {
          PartRanges.Empty
        }

        return acceptParts to rejectParts
      }
    }

    data object Always : Rule {
      override fun accept(part: Part): Boolean = true

      override fun partition(partRanges: PartRanges): Pair<PartRanges, PartRanges> {
        return partRanges to PartRanges.Empty
      }
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

  data class PartRanges(
    val map: Map<String, List<IntRange>>,
  ) {
    val x: List<IntRange> by map
    val m: List<IntRange> by map
    val a: List<IntRange> by map
    val s: List<IntRange> by map

    companion object {
      val Empty = PartRanges(
        map = emptyMap(),
      )
    }
  }
}

fun main() = SomeDay.mainify(Day19)
