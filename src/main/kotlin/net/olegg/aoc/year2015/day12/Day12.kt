package net.olegg.aoc.year2015.day12

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.contentOrNull
import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2015.DayOf2015

/**
 * See [Year 2015, Day 12](https://adventofcode.com/2015/day/12)
 */
object Day12 : DayOf2015(12) {
  private val json = Json.parseToJsonElement(data)

  override fun first(data: String): Any? {
    return sumRecursive(json).toString()
  }

  override fun second(data: String): Any? {
    return sumRecursiveRed(json).toString()
  }

  private fun sumRecursive(json: JsonElement): Int {
    return when (json) {
      is JsonPrimitive -> json.contentOrNull?.toIntOrNull() ?: 0
      is JsonObject -> json.values.sumOf { sumRecursive(it) }
      is JsonArray -> json.sumOf { sumRecursive(it) }
    }
  }

  private fun sumRecursiveRed(json: JsonElement): Int {
    return when (json) {
      is JsonPrimitive -> json.contentOrNull?.toIntOrNull() ?: 0
      is JsonObject -> if (JsonPrimitive("red") in json.values) 0 else json.values.sumOf { sumRecursiveRed(it) }
      is JsonArray -> json.sumOf { sumRecursiveRed(it) }
    }
  }
}

fun main() = SomeDay.mainify(Day12)
