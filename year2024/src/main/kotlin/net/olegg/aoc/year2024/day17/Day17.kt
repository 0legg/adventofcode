package net.olegg.aoc.year2024.day17

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2024.DayOf2024
import java.math.BigInteger

/**
 * See [Year 2024, Day 17](https://adventofcode.com/2024/day/17)
 */
object Day17 : DayOf2024(17) {
  private val numberPattern = "(-?\\d+)".toRegex()
  private val EIGHT = 8.toBigInteger()

  override fun first(): Any? {
    val (rawRegisters, rawProgram) = data.split("\n\n")
    val (a, b, c) = rawRegisters.lines().mapNotNull { line ->
      numberPattern.find(line)?.value?.toBigInteger()
    }
    val program = numberPattern.findAll(rawProgram).map { it.value.toInt() }.toList()

    return calculate(program, a, b, c).joinToString(separator = ",")
  }

  override fun second(): Any? {
    val rawProgram = data.substringAfter("\n\n")
    val program = numberPattern.findAll(rawProgram).map { it.value.toInt() }.toList()

    val mapping = (0 until 4096).associate { startA ->
      (0..3).map { (startA shr (3 * it)) and 7 } to calculate(program, startA.toBigInteger()).first()
    }

    val startConfig = mapping.filter { it.value == program.first() }
      .keys
      .groupingBy { it.drop(1) }
      .fold(Int.MAX_VALUE) { min, list -> minOf(min, list.first()) }
      .mapValues { it.value.toBigInteger() }

    val result = program.drop(1).foldIndexed(startConfig) { index, acc, op ->
      val fit = mapping.filter { it.value == op }.filter { it.key.dropLast(1) in acc }

      fit.keys
        .groupingBy { it.drop(1) }
        .fold(Int.MAX_VALUE) { min, list -> minOf(min, list.first()) }
        .mapValues { it.value.toBigInteger().shl((index + 1) * 3) + acc[listOf(it.value) + it.key.dropLast(1)]!! }
    }

    val best = result.minOf { (rawHead, tail) ->
      val head = rawHead.reduceRight { value, acc -> acc * 8 + value }.toBigInteger()
      head.shl(3 * program.size) + tail
    }

    check(calculate(program, best) == program)

    return best
  }

  private fun calculate(
    program: List<Int>,
    startA: BigInteger,
    startB: BigInteger = BigInteger.ZERO,
    startC: BigInteger = BigInteger.ZERO,
  ): List<Int> = buildList {
    var a = startA
    var b = startB
    var c = startC

    fun combo(input: Int): BigInteger = when (input) {
      in 0..3 -> input.toBigInteger()
      4 -> a
      5 -> b
      6 -> c
      else -> error("Unexpected value")
    }

    var position = 0
    while (position in program.indices) {
      val instruction = program[position++]
      val operand = program[position++]

      when (instruction) {
        0 -> a = a.div(BigInteger.TWO.pow(combo(operand).toInt()))
        1 -> b = b xor operand.toBigInteger()
        2 -> b = combo(operand).mod(EIGHT)
        3 -> position = if (a != BigInteger.ZERO) operand else position
        4 -> b = b xor c
        5 -> add(combo(operand).mod(EIGHT).toInt())
        6 -> b = a.div(BigInteger.TWO.pow(combo(operand).toInt()))
        7 -> c = a.div(BigInteger.TWO.pow(combo(operand).toInt()))
      }
    }
  }
}

fun main() = SomeDay.mainify(Day17)
