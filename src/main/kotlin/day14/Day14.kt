package day14

import someday.SomeDay
import utils.scan

/**
 * Created by olegg on 12/20/15.
 */
class Day14: SomeDay(14) {
    val time = 2503
    val pattern = ".*\\b(\\d+)\\b.*\\b(\\d+)\\b.*\\b(\\d+)\\b.*".toPattern()
    val speeds = data.lines().map {
        val matcher = pattern.matcher(it)
        matcher.matches()
        Triple(matcher.group(1).toInt(), matcher.group(2).toInt(), matcher.group(2).toInt() + matcher.group(3).toInt())
    }

    override fun first(): String {
        return (speeds.map { ((time / it.third) * it.second +
                (time % it.third).coerceAtMost(it.second)) * it.first }.max() ?: 0).toString()
    }

    override fun second(): String {
        val distances = speeds.map { speed -> (0..time - 1).scan(0) { acc, value -> if (value % speed.third < speed.second) acc + speed.first else acc } }
        val timestamps = (0..time - 1).map { second ->  distances.map { it[second] } }.map { list -> list.map { if (it == list.max()) 1 else 0 } }
        return speeds.indices.map { speed -> timestamps.map { it[speed] } }.map { it.sum() }.max().toString()

    }
}

fun main(args: Array<String>) {
    val day = Day14()
    println(day.first())
    println(day.second())
}