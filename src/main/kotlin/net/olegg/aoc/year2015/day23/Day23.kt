package net.olegg.aoc.year2015.day23

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2015.DayOf2015

/**
 * See [Year 2015, Day 23](https://adventofcode.com/2015/day/23)
 */
class Day23 : DayOf2015(23) {
  private val commands = data.trim().lines()

  companion object {
    private val HLF_MATCHER = "^hlf (\\w)$".toRegex()
    private val TPL_MATCHER = "^tpl (\\w)$".toRegex()
    private val INC_MATCHER = "^inc (\\w)$".toRegex()
    private val JMP_MATCHER = "^jmp ([+-]?\\d+)$".toRegex()
    private val JIE_MATCHER = "^jie (\\w), ([+-]?\\d+)$".toRegex()
    private val JIO_MATCHER = "^jio (\\w), ([+-]?\\d+)$".toRegex()
  }

  fun emulate(initialState: Triple<Int, Int, Int>): Triple<Int, Int, Int> {
    var state = initialState
    while (state.third in commands.indices) {
      val command = commands[state.third]
      state = when {
        command.matches(HLF_MATCHER) -> {
          when (HLF_MATCHER.find(command)?.groups?.get(1)?.value) {
            "a" -> state.copy(first = state.first / 2, third = state.third + 1)
            "b" -> state.copy(second = state.second / 2, third = state.third + 1)
            else -> state
          }
        }
        command.matches(TPL_MATCHER) -> {
          when (TPL_MATCHER.find(command)?.groups?.get(1)?.value) {
            "a" -> state.copy(first = state.first * 3, third = state.third + 1)
            "b" -> state.copy(second = state.second * 3, third = state.third + 1)
            else -> state
          }
        }
        command.matches(INC_MATCHER) -> {
          when (INC_MATCHER.find(command)?.groups?.get(1)?.value) {
            "a" -> state.copy(first = state.first + 1, third = state.third + 1)
            "b" -> state.copy(second = state.second + 1, third = state.third + 1)
            else -> state
          }
        }
        command.matches(JMP_MATCHER) -> {
          val jump = JMP_MATCHER.find(command)?.groups?.get(1)?.value?.toInt() ?: 0
          state.copy(third = state.third + jump)
        }

        command.matches(JIE_MATCHER) -> {
          val match = JIE_MATCHER.find(command)?.groups
          when (match?.get(1)?.value) {
            "a" -> state.copy(third = state.third +
                if (state.first % 2 == 0) (match[2]?.value?.toInt() ?: 0) else 1)
            "b" -> state.copy(third = state.third +
                if (state.second % 2 == 0) (match[2]?.value?.toInt() ?: 0) else 1)
            else -> state
          }
        }

        command.matches(JIO_MATCHER) -> {
          val match = JIO_MATCHER.find(command)?.groups
          when (match?.get(1)?.value) {
            "a" -> state.copy(third = state.third +
                if (state.first == 1) (match[2]?.value?.toInt() ?: 0) else 1)
            "b" -> state.copy(third = state.third +
                if (state.second == 1) (match[2]?.value?.toInt() ?: 0) else 1)
            else -> state
          }
        }

        else -> state
      }
    }
    return state
  }

  override fun first(data: String): Any? {
    return emulate(Triple(0, 0, 0)).second
  }

  override fun second(data: String): Any? {
    return emulate(Triple(1, 0, 0)).second
  }
}

fun main() = SomeDay.mainify(Day23::class)
