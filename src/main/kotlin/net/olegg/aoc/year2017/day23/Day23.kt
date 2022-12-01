package net.olegg.aoc.year2017.day23

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2017.DayOf2017
import java.math.BigInteger

/**
 * See [Year 2017, Day 23](https://adventofcode.com/2017/day/23)
 */
object Day23 : DayOf2017(23) {
  override fun first(): Any? {
    val ops = lines.map { it.split(" ") }

    var position = 0
    val regs = mutableMapOf<String, BigInteger>()
    var muls = 0

    while (position in ops.indices) {
      val op = ops[position]
      when (op[0]) {
        "set" -> regs[op[1]] = extract(regs, op[2])
        "sub" -> regs[op[1]] = extract(regs, op[1]) - extract(regs, op[2])
        "mul" -> {
          regs[op[1]] = extract(regs, op[1]) * extract(regs, op[2])
          muls += 1
        }
        "jnz" -> if (extract(regs, op[1]) != BigInteger.ZERO) position += (extract(regs, op[2]).toInt() - 1)
      }
      position += 1
    }

    return muls
  }

  override fun second(): Any? {
    val ops = lines.map { it.split(" ") }

    var position = 0
    val regs = mutableMapOf("a" to BigInteger.ONE)

    while (position in ops.indices) {
      val op = ops[position]
      when (op[0]) {
        "set" -> regs[op[1]] = extract(regs, op[2])
        "sub" -> regs[op[1]] = extract(regs, op[1]) - extract(regs, op[2])
        "mul" -> regs[op[1]] = extract(regs, op[1]) * extract(regs, op[2])
        "jnz" -> if (extract(regs, op[1]) != BigInteger.ZERO) position += (extract(regs, op[2]).toInt() - 1)
      }
      position += 1
    }

    return extract(regs, "h")
  }

  private fun extract(map: Map<String, BigInteger>, field: String): BigInteger {
    return field.toBigIntegerOrNull() ?: map.getOrDefault(field, BigInteger.ZERO)
  }
}

fun main() = SomeDay.mainify(Day23)
