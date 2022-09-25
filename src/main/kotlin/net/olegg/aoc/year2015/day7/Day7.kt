package net.olegg.aoc.year2015.day7

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2015.DayOf2015

/**
 * See [Year 2015, Day 7](https://adventofcode.com/2015/day/7)
 */
object Day7 : DayOf2015(7) {
  private val COMMAND_PATTERN = "^(.*) -> (.*)$".toRegex()
  private val NOT_PATTERN = "^NOT (\\d+)$".toRegex()
  private val AND_PATTERN = "^(\\d+) AND (\\d+)$".toRegex()
  private val OR_PATTERN = "^(\\d+) OR (\\d+)$".toRegex()
  private val LSHIFT_PATTERN = "^(\\d+) LSHIFT (\\d+)$".toRegex()
  private val RSHIFT_PATTERN = "^(\\d+) RSHIFT (\\d+)$".toRegex()
  private val VAR_PATTERN = "[a-z]".toRegex()

  private val source = lines
    .associate { line ->
      val match = checkNotNull(COMMAND_PATTERN.matchEntire(line))
      val (command, wire) = match.destructured
      return@associate wire to command
    }

  private fun measure(board: Map<String, String>, pin: String): String {
    var state = board
    val resolved = linkedMapOf<String, String>()
    while (!resolved.contains(pin)) {
      val temp = state
        .filterValues { VAR_PATTERN !in it }
        .mapValues { (_, value) ->
          NOT_PATTERN.matchEntire(value)?.let { match ->
            val (first) = match.destructured
            first.toInt().inv()
          }
            ?: AND_PATTERN.matchEntire(value)?.let { match ->
              val (first, second) = match.destructured
              first.toInt() and second.toInt()
            }
            ?: OR_PATTERN.matchEntire(value)?.let { match ->
              val (first, second) = match.destructured
              first.toInt() or second.toInt()
            }
            ?: LSHIFT_PATTERN.matchEntire(value)?.let { match ->
              val (first, second) = match.destructured
              first.toInt() shl second.toInt()
            }
            ?: RSHIFT_PATTERN.matchEntire(value)?.let { match ->
              val (first, second) = match.destructured
              first.toInt() shr second.toInt()
            }
            ?: value.toInt()
        }
        .mapValues { (0xFFFF and it.value).toString() }

      state = state
        .filterKeys { !temp.containsKey(it) }
        .mapValues { (_, command) ->
          temp.toList().fold(command) { acc, value ->
            acc.replace("\\b${value.first}\\b".toRegex(), value.second)
          }
        }
      resolved.putAll(temp)
    }
    return resolved[pin].orEmpty()
  }

  override fun first(): Any? {
    return measure(source, "a")
  }

  override fun second(): Any? {
    return measure(source + ("b" to measure(source, "a")), "a")
  }
}

fun main() = SomeDay.mainify(Day7)
