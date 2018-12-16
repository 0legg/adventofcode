package net.olegg.adventofcode.year2018.day16

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2018.DayOf2018

/**
 * @see <a href="http://adventofcode.com/2018/day/16">Year 2018, Day 16</a>
 */
class Day16 : DayOf2018(16) {
  companion object {
    private val REGS_PATTERN = ".*\\[(\\d+), (\\d+), (\\d+), (\\d+)]".toRegex()
    private val OPS_PATTERN = "(\\d+) (\\d+) (\\d+) (\\d+)".toRegex()
  }

  override fun first(data: String): Any? {
    val firstPart = data.split("\n\n\n\n")[0]
    val inputs = firstPart
        .split("\n\n")
        .map { sample ->
          val (beforeRaw, commandRaw, afterRaw) = sample.split("\n")
          val before = REGS_PATTERN.find(beforeRaw)?.destructured?.toList()?.map { it.toInt() } ?: listOf(0, 0, 0, 0)
          val command = OPS_PATTERN.find(commandRaw)?.destructured?.toList()?.map { it.toInt() } ?: listOf(0, 0, 0, 0)
          val after = REGS_PATTERN.find(afterRaw)?.destructured?.toList()?.map { it.toInt() } ?: listOf(0, 0, 0, 0)

          return@map Triple(before, command, after)
        }

    return inputs
        .map { (before, command, after) ->
          Ops.values().count { op ->
            op.apply(before, command[1], command[2], command[3]) == after
          }
        }
        .count { it >= 3 }
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
}

fun main(args: Array<String>) = SomeDay.mainify(Day16::class)
