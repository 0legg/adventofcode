package net.olegg.adventofcode.year2018.day19

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2018.DayOf2018

/**
 * @see <a href="http://adventofcode.com/2018/day/19">Year 2018, Day 19</a>
 */
class Day19 : DayOf2018(19) {
  companion object {
    private val OPS_PATTERN = "(\\w+) (\\d+) (\\d+) (\\d+)".toRegex()
  }

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
            return@mapNotNull Command(Ops.valueOf(opRaw.toUpperCase()), aRaw.toInt(), bRaw.toInt(), cRaw.toInt())
          }
        }

    (0..1_000_000_000).fold(listOf(0, 0, 0, 0, 0, 0)) { acc, _ ->
      val instruction = acc[pointer]
      if (instruction !in program.indices) {
        return acc[0]
      }
      val command = program[instruction]
      return@fold command.apply(acc).mapIndexed { index, value -> if (index == pointer) value + 1 else value }
    }

    return null
  }

  enum class Ops {
    ADDR {
      override fun apply(regs: List<Int>, a: Int, b: Int, c: Int): List<Int> {
        return regs.mapIndexed { index, value -> if (index == c) regs[a] + regs[b] else value }
      }
    },
    ADDI {
      override fun apply(regs: List<Int>, a: Int, b: Int, c: Int): List<Int> {
        return regs.mapIndexed { index, value -> if (index == c) regs[a] + b else value }
      }
    },
    MULR {
      override fun apply(regs: List<Int>, a: Int, b: Int, c: Int): List<Int> {
        return regs.mapIndexed { index, value -> if (index == c) regs[a] * regs[b] else value }
      }
    },
    MULI {
      override fun apply(regs: List<Int>, a: Int, b: Int, c: Int): List<Int> {
        return regs.mapIndexed { index, value -> if (index == c) regs[a] * b else value }
      }
    },
    BANR {
      override fun apply(regs: List<Int>, a: Int, b: Int, c: Int): List<Int> {
        return regs.mapIndexed { index, value -> if (index == c) regs[a] and regs[b] else value }
      }
    },
    BANI {
      override fun apply(regs: List<Int>, a: Int, b: Int, c: Int): List<Int> {
        return regs.mapIndexed { index, value -> if (index == c) regs[a] and b else value }
      }
    },
    BORR {
      override fun apply(regs: List<Int>, a: Int, b: Int, c: Int): List<Int> {
        return regs.mapIndexed { index, value -> if (index == c) regs[a] or regs[b] else value }
      }
    },
    BORI {
      override fun apply(regs: List<Int>, a: Int, b: Int, c: Int): List<Int> {
        return regs.mapIndexed { index, value -> if (index == c) regs[a] or b else value }
      }
    },
    SETR {
      override fun apply(regs: List<Int>, a: Int, b: Int, c: Int): List<Int> {
        return regs.mapIndexed { index, value -> if (index == c) regs[a] else value }
      }
    },
    SETI {
      override fun apply(regs: List<Int>, a: Int, b: Int, c: Int): List<Int> {
        return regs.mapIndexed { index, value -> if (index == c) a else value }
      }
    },
    GTIR {
      override fun apply(regs: List<Int>, a: Int, b: Int, c: Int): List<Int> {
        return regs.mapIndexed { index, value -> if (index == c) { if (a > regs[b]) 1 else 0 } else value }
      }
    },
    GTRI {
      override fun apply(regs: List<Int>, a: Int, b: Int, c: Int): List<Int> {
        return regs.mapIndexed { index, value -> if (index == c) { if (regs[a] > b) 1 else 0 } else value }
      }
    },
    GTRR {
      override fun apply(regs: List<Int>, a: Int, b: Int, c: Int): List<Int> {
        return regs.mapIndexed { index, value -> if (index == c) { if (regs[a] > regs[b]) 1 else 0 } else value }
      }
    },
    EQIR {
      override fun apply(regs: List<Int>, a: Int, b: Int, c: Int): List<Int> {
        return regs.mapIndexed { index, value -> if (index == c) { if (a == regs[b]) 1 else 0 } else value }
      }
    },
    EQRI {
      override fun apply(regs: List<Int>, a: Int, b: Int, c: Int): List<Int> {
        return regs.mapIndexed { index, value -> if (index == c) { if (regs[a] == b) 1 else 0 } else value }
      }
    },
    EQRR {
      override fun apply(regs: List<Int>, a: Int, b: Int, c: Int): List<Int> {
        return regs.mapIndexed { index, value -> if (index == c) { if (regs[a] == regs[b]) 1 else 0 } else value }
      }
    };

    abstract fun apply(regs: List<Int>, a: Int, b: Int, c: Int): List<Int>
  }

  data class Command(val op: Ops, val a: Int, val b: Int, val c: Int) {
    fun apply(regs: List<Int>): List<Int> = op.apply(regs, a, b, c)
  }
}

fun main(args: Array<String>) = SomeDay.mainify(Day19::class)
