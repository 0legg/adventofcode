package year2016.day3

import someday.SomeDay
import year2016.DayOf2016

/**
 * Created by olegg on 12/2/16.
 */
class Day3 : DayOf2016(3) {
    override fun first(): String {
        return data.split("\n").map {
            it.trim().split("\\s+".toRegex()).map { it.toInt() }.sorted()
        }.count { it[0] + it[1] > it[2] }.toString()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day3::class.java)