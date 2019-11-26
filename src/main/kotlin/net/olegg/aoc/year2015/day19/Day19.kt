package net.olegg.aoc.year2015.day19

import java.util.ArrayDeque
import java.util.regex.Pattern
import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2015.DayOf2015

/**
 * See [Year 2015, Day 19](https://adventofcode.com/2015/day/19)
 */
class Day19 : DayOf2015(19) {
  private val transitions = data.lines().dropLast(2).map { val split = it.split(" => "); Pair(split[0], split[1]) }
  private val molecule = data.lines().last()

  private fun applyTransitions(molecule: String, transition: Pair<Pattern, String>): Set<String> {
    val matcher = transition.first.matcher(molecule)
    val result = hashSetOf<String>()
    while (matcher.find()) {
      result.add(molecule.replaceRange(matcher.start(), matcher.end(), transition.second))
    }
    return result
  }

  override fun first(data: String): Any? {
    return transitions
        .map { it.first.toPattern() to it.second }
        .flatMap { applyTransitions(molecule, it) }
        .toSet()
        .size
  }

  override fun second(data: String): Any? {
    val reverse = transitions.map { it.second.toPattern() to it.first }
    val molecules = hashMapOf(molecule to 0)
    val queue = ArrayDeque(listOf(molecule))
    while (!molecules.containsKey("e") && queue.isNotEmpty()) {
      val curr = queue.pop()
      val size = molecules.getOrDefault(curr, Int.MAX_VALUE)
      val next = reverse.flatMap { applyTransitions(curr, it) }.sortedBy { it.length }.take(2) // greedy approach
      next.filterNot { molecules.containsKey(it) }.forEach { queue.push(it) }
      molecules += next.map { it to (molecules.getOrDefault(it, Int.MAX_VALUE)).coerceAtMost(size + 1) }.toMap()
    }
    return molecules.getOrDefault("e", Int.MAX_VALUE)
  }
}

fun main() = SomeDay.mainify(Day19::class)
