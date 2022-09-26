package net.olegg.aoc.year2018.day19

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2018.Command
import net.olegg.aoc.year2018.DayOf2018
import net.olegg.aoc.year2018.Ops

/**
 * See [Year 2018, Day 19](https://adventofcode.com/2018/day/19)
 */
object Day19 : DayOf2018(19) {
  private val OPS_PATTERN = "(\\w+) (\\d+) (\\d+) (\\d+)".toRegex()

  override fun first(): Any? {
    return solve(listOf(0, 0, 0, 0, 0, 0))
  }

  override fun second(): Any? {
    return solve(listOf(1, 0, 0, 0, 0, 0))
  }

  private fun solve(registers: List<Long>): Long {
    val pointer = lines
      .first()
      .let { it.split(" ")[1].toIntOrNull() ?: 0 }
    val program = lines
      .drop(1)
      .mapNotNull { line ->
        OPS_PATTERN.matchEntire(line)?.let { match ->
          val (opRaw, aRaw, bRaw, cRaw) = match.destructured
          return@mapNotNull Command(Ops.valueOf(opRaw.uppercase()), aRaw.toInt(), bRaw.toInt(), cRaw.toInt())
        }
      }

    val regs = registers.toLongArray()

    (0..1_000_000_000_000L).forEach { _ ->
      val instruction = regs[pointer].toInt()
      if (instruction !in program.indices) {
        return regs[0]
      }
      program[instruction].apply(regs)
      regs[pointer]++
    }

    return 0
  }
}

fun main() = SomeDay.mainify(Day19)
