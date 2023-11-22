package net.olegg.aoc.year2017.day18

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2017.DayOf2017

/**
 * See [Year 2017, Day 18](https://adventofcode.com/2017/day/18)
 */
object Day18 : DayOf2017(18) {
  override fun first(): Any? {
    var sound = 0L
    val ops = lines.map { it.split(" ") }

    var position = 0
    val regs = mutableMapOf<String, Long>()

    while (position in ops.indices) {
      val op = ops[position]
      when (op[0]) {
        "snd" -> sound = extract(regs, op[1])
        "set" -> regs[op[1]] = extract(regs, op[2])
        "add" -> regs[op[1]] = extract(regs, op[1]) + extract(regs, op[2])
        "mul" -> regs[op[1]] = extract(regs, op[1]) * extract(regs, op[2])
        "mod" -> regs[op[1]] =
          (extract(regs, op[1]) % extract(regs, op[2]) + extract(regs, op[2])) % extract(regs, op[2])
        "rcv" -> if (extract(regs, op[1]) != 0L) return sound
        "jgz" -> if (extract(regs, op[1]) > 0L) position += (extract(regs, op[2]) - 1).toInt()
      }
      position += 1
    }

    return null
  }

  override fun second(): Any? {
    val regs = Array(2) { mutableMapOf("p" to it.toLong()) }
    val stacks = Array(2) { ArrayDeque<Long>() }
    val position = Array(2) { 0 }
    var active = 0
    val send = Array(2) { 0L }
    val locked = Array(2) { false }
    val stopped = Array(2) { false }
    val ops = lines.map { it.split(" ") }

    while (!locked[active] && !stopped[active]) {
      if (position[active] !in ops.indices) {
        stopped[active] = true
        active = 1 - active
        continue
      }

      val op = ops[position[active]]
      position[active] += when (op[0]) {
        "snd" -> {
          stacks[1 - active].add(extract(regs[active], op[1]))
          locked[1 - active] = false
          send[active]++
          1
        }
        "set" -> {
          regs[active][op[1]] = extract(regs[active], op[2])
          1
        }
        "add" -> {
          val a = extract(regs[active], op[1])
          val b = extract(regs[active], op[2])
          regs[active][op[1]] = a + b
          1
        }
        "mul" -> {
          val a = extract(regs[active], op[1])
          val b = extract(regs[active], op[2])
          regs[active][op[1]] = a * b
          1
        }
        "mod" -> {
          val a = extract(regs[active], op[1])
          val b = extract(regs[active], op[2])
          regs[active][op[1]] = ((a % b) + b) % b
          1
        }
        "rcv" -> if (stacks[active].isNotEmpty()) {
          regs[active][op[1]] = stacks[active].removeFirst()
          1
        } else {
          locked[active] = true
          active = 1 - active
          0
        }
        "jgz" -> {
          if (extract(regs[active], op[1]) > 0L) extract(regs[active], op[2]).toInt() else 1
        }
        else -> 1
      }
    }

    return send[1]
  }

  private fun extract(
    map: Map<String, Long>,
    field: String
  ): Long {
    return field.toLongOrNull() ?: map.getOrDefault(field, 0)
  }
}

fun main() = SomeDay.mainify(Day18)
