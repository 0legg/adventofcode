package net.olegg.adventofcode.year2017.day12

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2017.DayOf2017
import java.util.ArrayDeque

/**
 * @see <a href="http://adventofcode.com/2017/day/12">Year 2017, Day 12</a>
 */
class Day12 : DayOf2017(12) {
    override fun first(data: String): String {
        val nodes = data.trimIndent()
                .lines()
                .map { it.replace("[<\\->,]".toRegex(), "") }
                .map { it.split("\\s+".toRegex()).map { it.toInt() } }
                .map { it[0] to it.subList(1, it.size).toSet() }
                .toMap()

        val visited = mutableSetOf(0)
        val queue = ArrayDeque<Int>(listOf(0))
        while (queue.isNotEmpty()) {
            val curr = queue.pop()
            val toVisit = (nodes[curr] ?: emptySet()) - visited
            visited += toVisit
            queue += toVisit
        }

        return visited.size.toString()
    }

    override fun second(data: String): String {
        val nodes = data.trimIndent()
                .lines()
                .map { it.replace("[<\\->,]".toRegex(), "") }
                .map { it.split("\\s+".toRegex()).map { it.toInt() } }
                .map { it[0] to it.subList(1, it.size).toSet() }
                .toMap()
                .toMutableMap()

        var components = 0
        while (nodes.isNotEmpty()) {
            components += 1
            val node = nodes.keys.first()
            val visited = mutableSetOf(node)
            val queue = ArrayDeque<Int>(listOf(node))
            while (queue.isNotEmpty()) {
                val curr = queue.pop()
                val toVisit = (nodes[curr] ?: emptySet()) - visited
                visited += toVisit
                queue += toVisit
            }
            visited.forEach { nodes.remove(it) }
        }

        return components.toString()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day12::class)
