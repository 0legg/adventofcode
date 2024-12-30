package net.olegg.aoc.year2024.day24

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.toPair
import net.olegg.aoc.year2024.DayOf2024
import net.olegg.aoc.year2024.day24.Day24.Arg.X
import net.olegg.aoc.year2024.day24.Day24.Arg.Y
import net.olegg.aoc.year2024.day24.Day24.Arg.Z

/**
 * See [Year 2024, Day 24](https://adventofcode.com/2024/day/24)
 */
object Day24 : DayOf2024(24) {
  private const val DEBUG = false
  private val rulePattern = "(\\w+) (OR|XOR|AND) (\\w+) -> (\\w+)".toRegex()
  override fun first(): Any? {
    val (rawGates, rawRules) = data.split("\n\n")

    val inputs = rawGates.lines()
      .associate { line ->
        val rawPair = line.split(": ").toPair()
        Arg.parse(rawPair.first) to (rawPair.second == "1")
      }

    val rules = rawRules.lines()
      .mapNotNull { rulePattern.matchEntire(it) }
      .map { match ->
        val (a, op, b, res) = match.destructured
        Triple(op, Arg.parse(a), Arg.parse(b)) to Arg.parse(res)
      }

    val nodes = (inputs.keys + rules.flatMap { listOf(it.first.first, it.first.second, it.second) }).toSet()
    val finalValues = inputs.toMutableMap()
    val zs = nodes.filterIsInstance<Z>().toSortedSet()
    var remainingRules = rules

    while (zs.any { it !in finalValues }) {
      val (applicable, nonApplicable) = remainingRules.partition { (rule, _) ->
        rule.second in finalValues && rule.third in finalValues
      }

      applicable.forEach { (rule, res) ->
        val (op, a, b) = rule
        finalValues[res] = op.calc(finalValues[a]!!, finalValues[b]!!)
      }

      remainingRules = nonApplicable
    }

    return zs
      .reversed()
      .joinToString("") { if (finalValues[it]!!) "1" else "0" }
      .toBigInteger(2)
  }

  override fun second(): Any? {
    val (rawGates, rawRules) = data.split("\n\n")

    val inputs = rawGates.lines().map { Arg.parse(it.substringBefore(": ")) }
    val xs = inputs.filterIsInstance<X>().toSortedSet()
    val ys = inputs.filterIsInstance<Y>().toSortedSet()

    val rules = rawRules.lines()
      .mapNotNull { rulePattern.matchEntire(it) }
      .map { match ->
        val (a, op, b, res) = match.destructured
        Triple(op, Arg.parse(a), Arg.parse(b)) to Arg.parse(res)
      }

    val nodes = (inputs + rules.flatMap { listOf(it.first.first, it.first.second, it.second) }).toSet()

    val zs = nodes.filterIsInstance<Z>().toSortedSet()

    val rawSwaps = listOf(
      "z12" to "fgc",
      "z29" to "mtj",
      "vvm" to "dgr",
      "z37" to "dtv",
    )
    val swaps = buildMap(rawSwaps.size * 2) {
      rawSwaps.forEach { (first, second) ->
        put(Arg.parse(first), Arg.parse(second))
        put(Arg.parse(second), Arg.parse(first))
      }
    }

    val fixedRules = rules.map { (rule, res) ->
      rule to (swaps[res] ?: res)
    }

    if (DEBUG) {
      println(
        buildString {
          appendLine()
          appendLine("########################################")
          appendLine("digraph G {")

          appendLine("  subgraph input_x {")
          appendLine("    node [style=filled,color=lightgrey];")
          xs.joinTo(this, separator = " -> ", prefix = "    ", postfix = ";\n")
          appendLine("  }")

          appendLine("  subgraph input_y {")
          appendLine("    node [style=filled,color=lightgrey];")
          ys.joinTo(this, separator = " -> ", prefix = "    ", postfix = ";\n")
          appendLine("  }")

          appendLine("  subgraph gates_and {")
          appendLine("    node [style=filled,color=lightgreen];")
          fixedRules.filter { it.first.first == "AND" }
            .map { it.second }
            .joinTo(this, separator = "; ", prefix = "    ", postfix = ";\n")
          appendLine("  }")

          appendLine("  subgraph gates_or {")
          appendLine("    node [style=filled,color=yellow];")
          fixedRules.filter { it.first.first == "OR" }
            .map { it.second }
            .joinTo(this, separator = "; ", prefix = "    ", postfix = ";\n")
          appendLine("  }")

          appendLine("  subgraph gates_xor {")
          appendLine("    node [style=filled,color=lightskyblue];")
          fixedRules.filter { it.first.first == "XOR" }
            .map { it.second }
            .joinTo(this, separator = "; ", prefix = "    ", postfix = ";\n")
          appendLine("  }")

          appendLine("  subgraph output_z {")
          zs.joinTo(this, separator = " -> ", prefix = "    ", postfix = ";\n")
          appendLine("  }")

          fixedRules.joinTo(this, separator = "\n", postfix = "\n") {
            "  ${it.first.second} -> ${it.second}; ${it.first.third} -> ${it.second};"
          }

          appendLine("}")
          appendLine("########################################")
          appendLine()
        }
      )
    }

    return rawSwaps.flatMap { it.toList() }.sorted().joinToString(",")
  }

  sealed interface Arg: Comparable<Arg> {
    val original: String
      get() = toString()
    val mapped: String
      get() = toString()
    val order: Int

    sealed interface Input: Arg {
      val num: Int
    }

    data class X(
      override val num: Int,
    ): Input {
      override val order = 0

      override fun toString() = "x%02d".format(num)

      override fun compareTo(other: Arg): Int = when (other) {
        is X -> num - other.num
        else -> order - other.order
      }
    }

    data class Y(
      override val num: Int,
    ): Input {
      override val order = 1

      override fun toString() = "y%02d".format(num)

      override fun compareTo(other: Arg): Int = when (other) {
        is Y -> num - other.num
        else -> order - other.order
      }
    }

    data class Z(
      val num: Int,
    ): Arg {
      override val order = 2

      override fun toString() = "z%02d".format(num)

      override fun compareTo(other: Arg) = when (other) {
        is Z -> num - other.num
        else -> order - other.order
      }
    }

    data class Unmapped(
      val raw: String,
    ): Arg {
      override val order = 100

      override fun toString() = raw

      override fun compareTo(other: Arg) = when (other) {
        is Unmapped -> raw.compareTo(other.raw)
        else -> order - other.order
      }
    }

    companion object {
      fun parse(raw: String): Arg = when (raw[0]) {
        'x' -> raw.drop(1).toIntOrNull()?.let { X(it) }
        'y' -> raw.drop(1).toIntOrNull()?.let { Y(it) }
        'z' -> raw.drop(1).toIntOrNull()?.let { Z(it) }
        else -> Unmapped(raw)
      } ?: Unmapped(raw)
    }
  }

  private fun String.calc(a: Boolean, b: Boolean) = when (this) {
    "OR" -> a or b
    "XOR" -> a xor b
    "AND" -> a and b
    else -> error("Unknown operation $this")
  }
}

fun main() = SomeDay.mainify(Day24)
