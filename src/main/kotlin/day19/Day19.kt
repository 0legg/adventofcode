package day19

import someday.SomeDay
import java.util.regex.Pattern

/**
 * Created by olegg on 21/12/15.
 */
class Day19: SomeDay(19) {
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
}

fun main(args: Array<String>) {
    val day = Day19()
    println(day.first())
}