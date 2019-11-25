package net.olegg.adventofcode.year2018.day16

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2018.Command
import net.olegg.adventofcode.year2018.DayOf2018
import net.olegg.adventofcode.year2018.Ops

/**
 * See [Year 2018, Day 16](https://adventofcode.com/2018/day/16)
 */
class Day16 : DayOf2018(16) {
  companion object {
    private val REGS_PATTERN = ".*\\[(\\d+), (\\d+), (\\d+), (\\d+)]".toRegex()
    private val OPS_PATTERN = "(\\d+) (\\d+) (\\d+) (\\d+)".toRegex()
    private val EMPTY = listOf(0L, 0L, 0L, 0L)
  }

  override fun first(data: String): Any? {
    val firstPart = data.split("\n\n\n\n")[0]
    val inputs = firstPart
        .split("\n\n")
        .map { sample ->
          val (beforeRaw, commandRaw, afterRaw) = sample.split("\n")
          val before = REGS_PATTERN.find(beforeRaw)?.destructured?.toList()?.map { it.toLong() } ?: EMPTY
          val command = OPS_PATTERN.find(commandRaw)?.destructured?.toList()?.map { it.toInt() } ?: listOf(0, 0, 0, 0)
          val after = REGS_PATTERN.find(afterRaw)?.destructured?.toList()?.map { it.toLong() } ?: EMPTY

          return@map Triple(before, command, after)
        }

    return inputs
        .map { (before, command, after) ->
          Ops.values().count { op ->
            val beforeArray = before.toLongArray()
            val vmcommand = Command(op, command[1], command[2], command[3])
            vmcommand.apply(beforeArray)
            return@count beforeArray.toList() == after
          }
        }
        .count { it >= 3 }
  }

  override fun second(data: String): Any? {
    val (firstPart, secondPart) = data.split("\n\n\n\n")
    val inputs = firstPart
        .split("\n\n")
        .map { sample ->
          val (beforeRaw, commandRaw, afterRaw) = sample.split("\n")
          val before = REGS_PATTERN.find(beforeRaw)?.destructured?.toList()?.map { it.toLong() } ?: EMPTY
          val command = OPS_PATTERN.find(commandRaw)?.destructured?.toList()?.map { it.toInt() } ?: listOf(0, 0, 0, 0)
          val after = REGS_PATTERN.find(afterRaw)?.destructured?.toList()?.map { it.toLong() } ?: EMPTY

          return@map Triple(before, command, after)
        }

    val possible = List(16) { Ops.values().toMutableSet() }
    inputs.forEach { (before, command, after) ->
      possible[command[0]].removeAll { op ->
        val beforeArray = before.toLongArray()
        val vmcommand = Command(op, command[1], command[2], command[3])
        vmcommand.apply(beforeArray)
        return@removeAll beforeArray.toList() != after
      }

      if (possible[command[0]].size == 1) {
        val op = possible[command[0]].first()
        possible
            .filterIndexed { index, _ -> index != command[0] }
            .forEach { it.remove(op) }
      }
    }
    val ops = possible.map { it.first() }

    val program = secondPart
        .split("\n")
        .mapNotNull { line -> OPS_PATTERN.find(line)?.destructured?.toList()?.map { it.toInt() } }

    val regs = EMPTY.toLongArray()
    program.forEach { command ->
      val vmcommand = Command(ops[command[0]], command[1], command[2], command[3])
      vmcommand.apply(regs)
    }

    return regs[0]
  }
}

fun main() = SomeDay.mainify(Day16::class)
