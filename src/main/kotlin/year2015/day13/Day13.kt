package year2015.day13

import someday.SomeDay
import utils.permutations
import utils.scan

/**
 * Created by olegg on 12/20/15.
 */
class Day13: SomeDay(13) {
    val pattern = "^\\b(\\w+)\\b.*\\b(gain|lose) \\b(\\d+)\\b.*\\b(\\w+)\\b\\.$".toPattern()
    val edges = data.lines().map {
        val matcher = pattern.matcher(it)
        if (matcher.matches()) {
            Pair(matcher.group(1), matcher.group(4)) to matcher.group(3).toInt() * (if (matcher.group(2) == "gain") 1 else -1)
        } else {
            Pair("", "") to 0
        }
    }.toMap()
    val names = edges.keys.flatMap { listOf(it.first, it.second) }.distinct()

    override fun first(): String {
        return names.permutations().map {
            it.scan(Pair("", it.last())) { acc, value -> Pair(acc.second, value) }.sumBy { edges[it] ?: 0 } +
                    it.reversed().scan(Pair("", it.first())) { acc, value -> Pair(acc.second, value) }.sumBy { edges[it] ?: 0 }
        }.max().toString()
    }

    override fun second(): String {
        return names.permutations().map {
            it.scan(Pair("", "")) { acc, value -> Pair(acc.second, value) }.sumBy { edges[it] ?: 0 } +
                    it.reversed().scan(Pair("", "")) { acc, value -> Pair(acc.second, value) }.sumBy { edges[it] ?: 0 }
        }.max().toString()
    }
}

fun main(args: Array<String>) {
    val day = Day13()
    println(day.first())
    println(day.second())
}