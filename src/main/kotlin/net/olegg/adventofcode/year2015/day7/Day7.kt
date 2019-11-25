package net.olegg.adventofcode.year2015.day7

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2015.DayOf2015

/**
 * See [Year 2015, Day 7](https://adventofcode.com/2015/day/7)
 */
class Day7 : DayOf2015(7) {
  companion object {
    private val COMMAND_PATTERN = "^(.*) -> (.*)$".toRegex()
    private val NOT_PATTERN = "^NOT (\\d+)$".toRegex()
    private val AND_PATTERN = "^(\\d+) AND (\\d+)$".toRegex()
    private val OR_PATTERN = "^(\\d+) OR (\\d+)$".toRegex()
    private val LSHIFT_PATTERN = "^(\\d+) LSHIFT (\\d+)$".toRegex()
    private val RSHIFT_PATTERN = "^(\\d+) RSHIFT (\\d+)$".toRegex()
    private var VAR_PATTERN = "[a-z]".toRegex()
  }

  val source = data
      .trim()
      .lines()
      .mapNotNull { line ->
        COMMAND_PATTERN.matchEntire(line)?.let { match ->
          val (command, wire) = match.destructured
          return@let wire to command
        }
      }
      .toMap()

  fun measure(board: Map<String, String>, pin: String): String {
    var state = board
    val resolved = linkedMapOf<String, String>()
    while (!resolved.contains(pin)) {
      val temp = state
          .filterValues { !it.contains(VAR_PATTERN) }
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
    return resolved[pin] ?: ""
  }

  override fun first(data: String): Any? {
    return measure(source, "a")
  }

  override fun second(data: String): Any? {
    return measure(source + ("b" to measure(source, "a")), "a")
  }
}

fun main(args: Array<String>) = SomeDay.mainify(Day7::class)
