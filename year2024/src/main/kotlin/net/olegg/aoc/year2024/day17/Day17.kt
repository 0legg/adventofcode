package net.olegg.aoc.year2024.day17

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2024.DayOf2024
import java.math.BigInteger

/**
 * See [Year 2024, Day 17](https://adventofcode.com/2024/day/17)
 */
object Day17 : DayOf2024(17) {
  private val numberPattern = "(-?\\d+)".toRegex()
  override fun first(): Any? {
    val (rawRegisters, rawProgram) = data.split("\n\n")

    var (a, b, c) = rawRegisters.lines()
      .mapNotNull { line ->
        numberPattern.find(line)?.value?.toBigInteger()
      }

    val program = numberPattern.findAll(rawProgram).map { it.value.toInt() }.toList()

    fun combo(input: Int): BigInteger = when (input) {
      in 0..3 -> input.toBigInteger()
      4 -> a
      5 -> b
      6 -> c
      else -> error("Unexpected value")
    }

    val output = mutableListOf<BigInteger>()

    var position = 0
    while (position in program.indices) {
      val instruction = program[position++]
      val operand = program[position++]

      when (instruction) {
        0 -> a = a.div(BigInteger.TWO.pow(combo(operand).toInt()))
        1 -> b = b xor operand.toBigInteger()
        2 -> b = combo(operand).mod(8.toBigInteger())
        3 -> position = if (a != BigInteger.ZERO) operand else position
        4 -> b = b xor c
        5 -> output += combo(operand).mod(8.toBigInteger())
        6 -> b = a.div(BigInteger.TWO.pow(combo(operand).toInt()))
        7 -> c = a.div(BigInteger.TWO.pow(combo(operand).toInt()))
      }
    }

    return output.joinToString(separator = ",")
  }
}

fun main() = SomeDay.mainify(Day17)
