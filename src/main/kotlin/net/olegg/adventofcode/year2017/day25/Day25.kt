package net.olegg.adventofcode.year2017.day25

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2017.DayOf2017

/**
 * @see <a href="http://adventofcode.com/2017/day/25">Year 2017, Day 25</a>
 */
class Day25 : DayOf2017(25) {

    val headerPattern = "Begin in state ([A-Z]+)\\.\\nPerform a diagnostic checksum after ([0-9]+) steps.".toRegex()
    val statePattern = "In state ([A-Z]+):".toRegex()
    val actionPattern = "If the current value is ([0-9]+)[^-]+- Write the value ([0-9]+)[^-]+- Move one slot to the ([a-z]+)[^-]+- Continue with state ([A-Z]+)\\.".toRegex()

    override fun first(data: String): Any? {
        val sections = data.trim().split("\n\n")

        val (state, iterations) = headerPattern.find(sections[0])?.destructured?.let {
            it.component1() to it.component2().toInt()
        } ?: ("A" to 0)

        val states = sections.slice(1 until sections.size).map { section ->
            val name = statePattern.find(section)?.destructured?.component1() ?: "A"
            val actions = actionPattern.findAll(section, section.indexOf("\n")).map {
                with(it.destructured) {
                    component1().toInt() to Action(
                            write = component2().toInt(),
                            shift = if (component3() == "left") -1 else 1,
                            state = component4()
                    )
                }
            }.toMap()

            return@map State(name, actions)
        }
                .map { it.name to it }
                .toMap()

        val tape = mutableMapOf<Int, Int>()

        (0 until iterations).fold(state to 0) { acc, _ ->
            val curr = tape.getOrDefault(acc.second, 0)
            val action = states[acc.first]?.actions?.get(curr) ?: return@fold acc
            tape[acc.second] = action.write
            return@fold action.state to acc.second + action.shift
        }

        return tape.values.sum().toString()
    }

    data class State(
        val name: String,
        val actions: Map<Int, Action>
    )

    data class Action(
        val write: Int,
        val shift: Int,
        val state: String
    )
}

fun main(args: Array<String>) = SomeDay.mainify(Day25::class)
