package day2

import someday.SomeDay

/**
 * Created by olegg on 12/18/15.
 */
class Day2: SomeDay(2) {
    val boxes = data.split('\n').filter { it.isNotBlank() }.map { it.split('x').map { it.toInt() }.sorted() }

    override fun first(): String {
        return boxes.sumBy { 3 * it[0] * it[1] + 2 * it[0] * it[2] + 2 * it[1] * it[2] }.toString()
    }

    override fun second(): String {
        return boxes.sumBy { 2 * (it[0] + it[1]) + it[0] * it[1] * it[2] }.toString()
    }
}

fun main(args: Array<String>) {
    val day = Day2()
    println(day.first())
    println(day.second())
}