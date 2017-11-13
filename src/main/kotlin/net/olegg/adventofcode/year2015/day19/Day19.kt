package net.olegg.adventofcode.year2015.day19

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2015.DayOf2015
import java.util.LinkedList
import java.util.regex.Pattern

/**
 * @see <a href="http://adventofcode.com/2015/day/19">Year 2015, Day 19</a>
 */
class Day19 : DayOf2015(19) {
    val transitions = data.lines().dropLast(2).map { val split = it.split(" => "); Pair(split[0], split[1]) }
    val molecule = data.lines().last()

    fun applyTransitions(molecule: String, transition: Pair<Pattern, String>): Set<String> {
        val matcher = transition.first.matcher(molecule)
        val result = hashSetOf<String>()
        while (matcher.find()) {
            result.add(molecule.replaceRange(matcher.start(), matcher.end(), transition.second))
        }
        return result
    }

    override fun first(): String {
        return transitions.map { it.first.toPattern() to it.second }.flatMap { applyTransitions(molecule, it) }.toSet().size.toString()
    }

    override fun second(): String {
        val reverse = transitions.map { it.second.toPattern() to it.first }
        val molecules = hashMapOf(molecule to 0)
        val queue = LinkedList(listOf(molecule))
        while (!molecules.containsKey("e") && queue.isNotEmpty()) {
            val curr = queue.pop()
            val size = molecules.getOrDefault(curr, Int.MAX_VALUE)
            val next = reverse.flatMap { applyTransitions(curr, it) }.sortedBy { it.length }.take(2) // greedy approach
            next.filterNot { molecules.containsKey(it) }.forEach { queue.addFirst(it) }
            molecules += next.map { it to (molecules.getOrDefault(it, Int.MAX_VALUE)).coerceAtMost(size + 1) }.toMap()
        }
        return molecules.getOrDefault("e", Int.MAX_VALUE).toString()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day19::class)
