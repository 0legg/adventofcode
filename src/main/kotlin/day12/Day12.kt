package day12

import someday.SomeDay

/**
 * Created by olegg on 12/20/15.
 */
class Day12: SomeDay(12) {
    override fun first(): String {
        return data.replace("[^(\\-?\\d)]".toRegex(), " ").trim().split("\\s+".toRegex()).sumBy { it.toInt() }.toString()
    }
}

fun main(args: Array<String>) {
    val day = Day12()
    println(day.first())
}
