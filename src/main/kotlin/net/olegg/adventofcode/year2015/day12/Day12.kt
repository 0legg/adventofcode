package net.olegg.adventofcode.year2015.day12

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2015.DayOf2015

/**
 * @see <a href="http://adventofcode.com/2015/day/12">Year 2015, Day 12</a>
 */
class Day12 : DayOf2015(12) {
    val json = Parser.default(streaming = true).parse(data.byteInputStream()) as JsonObject

    fun sumRecursive(json: Any?): Int {
        return when (json) {
            is Int -> json
            is JsonObject -> json.values.sumBy { sumRecursive(it) }
            is JsonArray<*> -> json.sumBy { sumRecursive(it) }
            else -> 0
        }
    }

    fun sumRecursiveRed(json: Any?): Int {
        return when (json) {
            is Int -> json
            is JsonObject -> if (json.values.contains("red")) 0 else json.values.sumBy { sumRecursiveRed(it) }
            is JsonArray<*> -> json.sumBy { sumRecursiveRed(it) }
            else -> 0
        }
    }

    override fun first(data: String): String {
        return sumRecursive(json).toString()
    }

    override fun second(data: String): String {
        return sumRecursiveRed(json).toString()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day12::class)
