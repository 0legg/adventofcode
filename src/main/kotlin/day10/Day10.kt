package day10

import someday.SomeDay
import utils.scan

/**
 * Created by olegg on 12/19/15.
 */
class Day10: SomeDay(10) {
    fun lookAndSay(source: String): String {
        val result = StringBuilder()
        var list = source.toList()

        while (list.isNotEmpty()) {
            val head = list.takeWhile { it == list[0] }
            result.append(head.size).append(list[0])
            list = list.slice(head.size .. list.size - 1)
        }
        return result.toString()
    }

    override fun first(): String {
        return (1..40).fold(data) { acc, value -> lookAndSay(acc) }.length.toString()
    }
}

fun main(args: Array<String>) {
    val day = Day10()
    println(day.first())
}