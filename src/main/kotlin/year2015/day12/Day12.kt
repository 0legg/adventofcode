package year2015.day12

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import someday.SomeDay

/**
 * Created by olegg on 12/20/15.
 */
class Day12: SomeDay(12) {
    val json = Parser().parse(data.byteInputStream()) as JsonObject

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

    override fun first(): String {
        return sumRecursive(json).toString()
    }

    override fun second(): String {
        return sumRecursiveRed(json).toString()
    }
}

fun main(args: Array<String>) {
    val day = Day12()
    println(day.first())
    println(day.second())
}
