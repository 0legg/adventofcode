package net.olegg.adventofcode.year2018.day19

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2018.Command
import net.olegg.adventofcode.year2018.DayOf2018
import net.olegg.adventofcode.year2018.Ops

/**
 * See [Year 2018, Day 19](https://adventofcode.com/2018/day/19)
 */
class Day19 : DayOf2018(19) {
  companion object {
    private val OPS_PATTERN = "(\\w+) (\\d+) (\\d+) (\\d+)".toRegex()
  }

  override fun first(data: String): Any? {
    return solve(data, listOf(0, 0, 0, 0, 0, 0))
  }

  override fun second(data: String): Any? {
    return solve(data, listOf(1, 0, 0, 0, 0, 0))
  }

  private fun solve(data: String, registers: List<Long>): Long {
    val pointer = data
        .trim()
        .lines()
        .first()
        .let { it.split(" ")[1].toIntOrNull() ?: 0 }
    val program = data
        .trim()
        .lines()
        .drop(1)
        .mapNotNull { line ->
          OPS_PATTERN.matchEntire(line)?.let { match ->
            val (opRaw, aRaw, bRaw, cRaw) = match.destructured
            return@mapNotNull Command(Ops.valueOf(opRaw.toUpperCase()), aRaw.toInt(), bRaw.toInt(), cRaw.toInt())
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

fun main() = SomeDay.mainify(Day19::class)
