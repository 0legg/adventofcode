package net.olegg.aoc.year2015.day19

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2015.DayOf2015

/**
 * See [Year 2015, Day 19](https://adventofcode.com/2015/day/19)
 */
object Day19 : DayOf2015(19) {
  private val transitions = data.trim()
      .lines()
      .dropLast(2)
      .map { it.split(" => ") }
      .map { it.first() to it.last() }
  private val molecule = data.trim().lines().last()

  private fun applyTransitions(molecule: String, transition: Pair<Regex, String>): Set<String> {
    val (regex, replacement) = transition
    return regex.findAll(molecule)
        .map { molecule.replaceRange(it.range, replacement) }
        .toSet()
  }

  override fun first(data: String): Any? {
    return transitions
        .map { it.first.toRegex() to it.second }
        .flatMap { applyTransitions(molecule, it) }
        .toSet()
        .size
  }

  override fun second(data: String): Any? {
    val reverse = transitions.map { it.second.toRegex() to it.first }
    val molecules = mutableMapOf(molecule to 0)
    val queue = ArrayDeque(listOf(molecule))
    while (!molecules.containsKey("e") && queue.isNotEmpty()) {
      val curr = queue.removeFirst()
      val size = molecules.getOrDefault(curr, Int.MAX_VALUE)
      val next = reverse.flatMap { applyTransitions(curr, it) }.sortedBy { it.length }.take(2) // greedy approach
      queue += next.filterNot { it in molecules }
      molecules += next.map { it to (molecules.getOrDefault(it, Int.MAX_VALUE)).coerceAtMost(size + 1) }.toMap()
    }
    return molecules.getOrDefault("e", Int.MAX_VALUE)
  }
}

fun main() = SomeDay.mainify(Day19)
