package net.olegg.adventofcode.year2016.day21

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2016.DayOf2016

/**
 * See [Year 2016, Day 21](https://adventofcode.com/2016/day/21)
 */
class Day21 : DayOf2016(21) {
  companion object {
    private val SP_PATTERN = "swap position (\\d+) with position (\\d+)".toRegex()
    private val SL_PATTERN = "swap letter (\\w) with letter (\\w)".toRegex()
    private val RL_PATTERN = "rotate left (\\d+) steps?".toRegex()
    private val RR_PATTERN = "rotate right (\\d+) steps?".toRegex()
    private val RB_PATTERN = "rotate based on position of letter (\\w)".toRegex()
    private val RP_PATTERN = "reverse positions (\\d+) through (\\d+)".toRegex()
    private val MP_PATTERN = "move position (\\d+) to position (\\d+)".toRegex()
  }

  sealed class Op {
    data class SwapPosition(val from: Int, val to: Int) : Op() {
      override fun apply(acc: StringBuilder) {
        val chars = acc[from] to acc[to]
        acc[from] = chars.second
        acc[to] = chars.first
      }

      override fun unapply(acc: StringBuilder) {
        val chars = acc[from] to acc[to]
        acc[from] = chars.second
        acc[to] = chars.first
      }
    }

    data class SwapLetter(val from: Char, val to: Char) : Op() {
      override fun apply(acc: StringBuilder) {
        val positions = acc.indexOf(from) to acc.indexOf(to)
        acc[positions.first] = to
        acc[positions.second] = from
      }

      override fun unapply(acc: StringBuilder) {
        val positions = acc.indexOf(from) to acc.indexOf(to)
        acc[positions.first] = to
        acc[positions.second] = from
      }
    }

    data class RotateLeft(val steps: Int) : Op() {
      override fun apply(acc: StringBuilder) {
        val sub = acc.substring(0, steps % acc.length)
        acc.delete(0, steps)
        acc.append(sub)
      }

      override fun unapply(acc: StringBuilder) {
        val sub = acc.substring(0, acc.length - steps % acc.length)
        acc.delete(0, acc.length - steps % acc.length)
        acc.append(sub)
      }
    }

    data class RotateRight(val steps: Int) : Op() {
      override fun apply(acc: StringBuilder) {
        val sub = acc.substring(0, acc.length - steps % acc.length)
        acc.delete(0, acc.length - steps % acc.length)
        acc.append(sub)
      }

      override fun unapply(acc: StringBuilder) {
        val sub = acc.substring(0, steps % acc.length)
        acc.delete(0, steps)
        acc.append(sub)
      }
    }

    data class RotateLetter(val letter: Char) : Op() {
      override fun apply(acc: StringBuilder) {
        val shift = acc.indexOf(letter).let { if (it >= 4) it + 2 else it + 1 } % acc.length
        val sub = acc.substring(0, acc.length - shift % acc.length)
        acc.delete(0, acc.length - shift % acc.length)
        acc.append(sub)
      }

      override fun unapply(acc: StringBuilder) {
        val target = acc.indexOf(letter)
        val source = acc.indices.first { i ->
          target == (i + i + if (i >= 4) 2 else 1) % acc.length
        }
        val shift = (acc.length + target - source) % acc.length
        val sub = acc.substring(0, shift % acc.length)
        acc.delete(0, shift)
        acc.append(sub)
      }
    }

    data class ReversePosition(val from: Int, val to: Int) : Op() {
      override fun apply(acc: StringBuilder) {
        val replacement = acc.substring(from, to + 1).reversed()
        acc.replace(from, to + 1, replacement)
      }

      override fun unapply(acc: StringBuilder) {
        val replacement = acc.substring(from, to + 1).reversed()
        acc.replace(from, to + 1, replacement)
      }
    }

    data class MovePosition(val from: Int, val to: Int) : Op() {
      override fun apply(acc: StringBuilder) {
        val char = acc[from]
        acc.deleteCharAt(from)
        acc.insert(to, char)
      }

      override fun unapply(acc: StringBuilder) {
        val char = acc[to]
        acc.deleteCharAt(to)
        acc.insert(from, char)
      }
    }

    abstract fun apply(acc: StringBuilder)
    abstract fun unapply(acc: StringBuilder)

    companion object {
      fun fromString(line: String): Op? = when {
        line.matches(SP_PATTERN) -> {
          val (from, to) = SP_PATTERN.find(line)?.destructured!!
          SwapPosition(from = from.toInt(), to = to.toInt())
        }
        line.matches(SL_PATTERN) -> {
          val (from, to) = SL_PATTERN.find(line)?.destructured!!
          SwapLetter(from = from.first(), to = to.first())
        }
        line.matches(RL_PATTERN) -> {
          val (steps) = RL_PATTERN.find(line)?.destructured!!
          RotateLeft(steps = steps.toInt())
        }
        line.matches(RR_PATTERN) -> {
          val (steps) = RR_PATTERN.find(line)?.destructured!!
          RotateRight(steps = steps.toInt())
        }
        line.matches(RB_PATTERN) -> {
          val (letter) = RB_PATTERN.find(line)?.destructured!!
          RotateLetter(letter = letter.first())
        }
        line.matches(RP_PATTERN) -> {
          val (from, to) = RP_PATTERN.find(line)?.destructured!!
          ReversePosition(from = from.toInt(), to = to.toInt())
        }
        line.matches(MP_PATTERN) -> {
          val (from, to) = MP_PATTERN.find(line)?.destructured!!
          MovePosition(from = from.toInt(), to = to.toInt())
        }
        else -> null
      }
    }
  }

  override fun first(data: String): Any? {
    val ops = data
        .trim()
        .lines()
        .mapNotNull { Op.fromString(it) }
    return scramble("abcdefgh", ops)
  }

  override fun second(data: String): Any? {
    val ops = data
        .trim()
        .lines()
        .mapNotNull { Op.fromString(it) }
    return unscramble("fbgdceah", ops)
  }

  fun scramble(password: String, operations: List<Op>): String {
    val acc = StringBuilder(password)
    operations.forEach { it.apply(acc) }
    return acc.toString()
  }

  fun unscramble(password: String, operations: List<Op>): String {
    val acc = StringBuilder(password)
    operations.reversed().forEach { it.unapply(acc) }
    return acc.toString()
  }
}

fun main(args: Array<String>) = SomeDay.mainify(Day21::class)
