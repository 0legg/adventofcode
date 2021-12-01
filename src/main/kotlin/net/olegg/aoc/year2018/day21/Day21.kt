package net.olegg.aoc.year2018.day21

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2018.Command
import net.olegg.aoc.year2018.DayOf2018
import net.olegg.aoc.year2018.Ops

/**
 * See [Year 2018, Day 21](https://adventofcode.com/2018/day/21)
 */
object Day21 : DayOf2018(21) {
  private val OPS_PATTERN = "(\\w+) (\\d+) (\\d+) (\\d+)".toRegex()

  override fun first(data: String): Any? {
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
          return@mapNotNull Command(Ops.valueOf(opRaw.uppercase()), aRaw.toInt(), bRaw.toInt(), cRaw.toInt())
        }
      }

    println(program.mapIndexed { index, command -> "%1$-2d: $command".format(index) }.joinToString("\n"))

    val regs = longArrayOf(0L, 0L, 0L, 0L, 0L, 0L)
    (0..1_000_000_000_000L).forEach { _ ->
      val instruction = regs[pointer].toInt()
      if (instruction !in program.indices) {
        return -1
      }
      if (instruction == 28) {
        return regs[5]
      }
      program[instruction].apply(regs)
      regs[pointer]++
    }

    return 0
  }

  override fun second(data: String): Any? {
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
          return@mapNotNull Command(Ops.valueOf(opRaw.uppercase()), aRaw.toInt(), bRaw.toInt(), cRaw.toInt())
        }
      }

    println(program.mapIndexed { index, command -> "%1$-2d: $command".format(index) }.joinToString("\n"))

    val exit = mutableMapOf<Long, Long>()
    val regs = longArrayOf(0L, 0L, 0L, 0L, 0L, 0L)
    (0..1_000_000_000_000L).forEach { step ->
      if (step % 100_000_000L == 0L) {
        println("$step -> [${regs.joinToString()}]")
      }
      val instruction = regs[pointer].toInt()
      if (instruction !in program.indices) {
        return -1
      }
      if (instruction == 28 && regs[5] !in exit) {
        exit[regs[5]] = step
        println("Exiting with ${regs[5]} at $step")
      }
      program[instruction].apply(regs)
      regs[pointer]++
    }

    return 0
  }
}

fun main() = SomeDay.mainify(Day21)
