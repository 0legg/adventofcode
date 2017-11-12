package year2015.day2

import someday.SomeDay
import year2015.DayOf2015

/**
 * Created by olegg on 12/18/15.
 */
class Day2 : DayOf2015(2) {
    val boxes = data.lines().map { it.split('x').map { it.toInt() }.sorted() }

    override fun first(): String {
        return boxes.sumBy { 3 * it[0] * it[1] + 2 * it[0] * it[2] + 2 * it[1] * it[2] }.toString()
    }

    override fun second(): String {
        return boxes.sumBy { 2 * (it[0] + it[1]) + it[0] * it[1] * it[2] }.toString()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day2::class)
