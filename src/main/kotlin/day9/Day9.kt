package day9

import someday.SomeDay
import utils.permutations
import utils.scan

/**
 * Created by olegg on 12/19/15.
 */
class Day9: SomeDay(9) {
    val pattern = "^\\b(\\w*)\\b to \\b(\\w*)\\b = (\\d*)$".toPattern()
    val edges = data.lines().flatMap {
        val matcher = pattern.matcher(it)
        matcher.matches()
        listOf(
                Pair(matcher.group(1), matcher.group(2)) to matcher.group(3).toInt(),
                Pair(matcher.group(2), matcher.group(1)) to matcher.group(3).toInt()
        )
    }.toMap()
    val cities = edges.keys.flatMap { listOf(it.first, it.second) }.distinct()

    override fun first(): String {
        return cities.permutations()
                .map {
                    it
                            .scan(Pair("", "")) { acc, value -> Pair(acc.second, value) }
                            .drop(1)
                            .map { edges[it] ?: 0}
                            .sumBy { it }
                }.minBy { it }.toString()
    }

    override fun second(): String {
        return cities.permutations()
                .map {
                    it
                            .scan(Pair("", "")) { acc, value -> Pair(acc.second, value) }
                            .drop(1)
                            .map { edges[it] ?: 0}
                            .sumBy { it }
                }.maxBy { it }.toString()
    }
}

fun main(args: Array<String>) {
    val day = Day9()
    println(day.first())
    println(day.second())
}
