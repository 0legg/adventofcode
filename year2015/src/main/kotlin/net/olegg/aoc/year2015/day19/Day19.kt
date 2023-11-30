package net.olegg.aoc.year2015.day19

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2015.DayOf2015

/**
 * See [Year 2015, Day 19](https://adventofcode.com/2015/day/19)
 */
object Day19 : DayOf2015(19) {
  private val TRANSITIONS = lines
    .dropLast(2)
    .map { it.split(" => ") }
    .map { it.first() to it.last() }
  private val MOLECULE = lines.last()

  private fun applyTransitions(
    molecule: String,
    transition: Pair<Regex, String>
  ): Set<String> {
    val (pattern, replacement) = transition
    return pattern.findAll(molecule)
      .map { molecule.replaceRange(it.range, replacement) }
      .toSet()
  }

  override fun first(): Any? {
    return TRANSITIONS
      .map { it.first.toRegex() to it.second }
      .flatMap { applyTransitions(MOLECULE, it) }
      .toSet()
      .size
  }

  override fun second(): Any? {
    val reverse = TRANSITIONS.map { it.second.toRegex() to it.first }
    val molecules = mutableMapOf(MOLECULE to 0)
    val queue = ArrayDeque(listOf(MOLECULE))
    while ("e" !in molecules && queue.isNotEmpty()) {
      val curr = queue.removeFirst()
      val size = molecules.getOrDefault(curr, Int.MAX_VALUE)
      val next = reverse.flatMap { applyTransitions(curr, it) }.sortedBy { it.length }.take(2) // greedy approach
      queue += next.filterNot { it in molecules }
      molecules += next.associateWith { (molecules.getOrDefault(it, Int.MAX_VALUE)).coerceAtMost(size + 1) }
    }
    return molecules.getOrDefault("e", Int.MAX_VALUE)
  }
}

fun main() = SomeDay.mainify(Day19)
