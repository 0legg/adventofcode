package year2015.day10

import someday.SomeDay
import utils.series

/**
 * Created by olegg on 12/19/15.
 */
class Day10: SomeDay(10) {
    fun lookAndSay(source: String) = source.toList().series().map { "${it.size}${it.first()}" }.joinToString(separator = "")

    override fun first(): String {
        return (1..40).fold(data) { acc, value -> lookAndSay(acc) }.length.toString()
    }

    override fun second(): String {
        return (1..50).fold(data) { acc, value -> lookAndSay(acc) }.length.toString()
    }
}

fun main(args: Array<String>) {
    val day = Day10()
    println(day.first())
    println(day.second())
}