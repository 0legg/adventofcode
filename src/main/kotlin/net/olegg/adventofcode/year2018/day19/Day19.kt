package net.olegg.adventofcode.year2018.day19

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2018.DayOf2018
import java.lang.Thread.sleep

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

  enum class Ops {
    ADDR {
      override fun apply(regs: LongArray, a: Int, b: Int) = regs[a] + regs[b]
      override fun stringify(a: Int, b: Int, c: Int) = "regs[$c] = regs[$a] + regs[$b]"
    },
    ADDI {
      override fun apply(regs: LongArray, a: Int, b: Int) = regs[a] + b
      override fun stringify(a: Int, b: Int, c: Int) = "regs[$c] = regs[$a] + $b"
    },
    MULR {
      override fun apply(regs: LongArray, a: Int, b: Int) = regs[a] * regs[b]
      override fun stringify(a: Int, b: Int, c: Int) = "regs[$c] = regs[$a] * regs[$b]"
    },
    MULI {
      override fun apply(regs: LongArray, a: Int, b: Int) = regs[a] * b
      override fun stringify(a: Int, b: Int, c: Int) = "regs[$c] = regs[$a] * $b"
    },
    BANR {
      override fun apply(regs: LongArray, a: Int, b: Int) = regs[a] and regs[b]
      override fun stringify(a: Int, b: Int, c: Int) = "regs[$c] = regs[$a] & regs[$b]"
    },
    BANI {
      override fun apply(regs: LongArray, a: Int, b: Int) = regs[a] and b.toLong()
      override fun stringify(a: Int, b: Int, c: Int) = "regs[$c] = regs[$a] & $b"
    },
    BORR {
      override fun apply(regs: LongArray, a: Int, b: Int) = regs[a] or regs[b]
      override fun stringify(a: Int, b: Int, c: Int) = "regs[$c] = regs[$a] | regs[$b]"
    },
    BORI {
      override fun apply(regs: LongArray, a: Int, b: Int) = regs[a] or b.toLong()
      override fun stringify(a: Int, b: Int, c: Int) = "regs[$c] = regs[$a] | $b"
    },
    SETR {
      override fun apply(regs: LongArray, a: Int, b: Int) = regs[a]
      override fun stringify(a: Int, b: Int, c: Int) = "regs[$c] = regs[$a]"
    },
    SETI {
      override fun apply(regs: LongArray, a: Int, b: Int) = a.toLong()
      override fun stringify(a: Int, b: Int, c: Int) = "regs[$c] = $a"
    },
    GTIR {
      override fun apply(regs: LongArray, a: Int, b: Int) = if (a > regs[b]) 1L else 0L
      override fun stringify(a: Int, b: Int, c: Int) = "regs[$c] = $a > regs[$b]"
    },
    GTRI {
      override fun apply(regs: LongArray, a: Int, b: Int) = if (regs[a] > b) 1L else 0L
      override fun stringify(a: Int, b: Int, c: Int) = "regs[$c] = regs[$a] > $b"
    },
    GTRR {
      override fun apply(regs: LongArray, a: Int, b: Int) = if (regs[a] > regs[b]) 1L else 0L
      override fun stringify(a: Int, b: Int, c: Int) = "regs[$c] = regs[$a] > regs[$b]"
    },
    EQIR {
      override fun apply(regs: LongArray, a: Int, b: Int) = if (a.toLong() == regs[b]) 1L else 0L
      override fun stringify(a: Int, b: Int, c: Int) = "regs[$c] = $a == regs[$b]"
    },
    EQRI {
      override fun apply(regs: LongArray, a: Int, b: Int) = if (regs[a] == b.toLong()) 1L else 0L
      override fun stringify(a: Int, b: Int, c: Int) = "regs[$c] = regs[$a] == $b"
    },
    EQRR {
      override fun apply(regs: LongArray, a: Int, b: Int) = if (regs[a] == regs[b]) 1L else 0L
      override fun stringify(a: Int, b: Int, c: Int) = "regs[$c] = regs[$a] == regs[$b]"
    };

    abstract fun apply(regs: LongArray, a: Int, b: Int): Long

    abstract fun stringify(a: Int, b: Int, c: Int): String
  }

  data class Command(val op: Ops, val a: Int, val b: Int, val c: Int) {
    fun apply(regs: LongArray) {
      regs[c] = op.apply(regs, a, b)
    }

    override fun toString(): String {
      return op.stringify(a, b, c)
    }
  }
}

fun main() = SomeDay.mainify(Day19::class)
