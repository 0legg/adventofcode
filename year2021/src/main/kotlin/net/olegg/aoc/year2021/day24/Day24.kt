package net.olegg.aoc.year2021.day24

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2021.DayOf2021

/**
 * See [Year 2021, Day 24](https://adventofcode.com/2021/day/24)
 */
object Day24 : DayOf2021(24) {
  private val MAPPING = listOf('w', 'x', 'y', 'z').withIndex().associate { it.value to it.index }
  private val DIGITS = 1..9

  override fun first(): Any? {
    return findDiffs()
      .flatMap { (first, second, diff) ->
        val match = DIGITS.filter { it in DIGITS && it + diff in DIGITS }
          .maxOf { it }
        listOf(first to match, second to match + diff)
      }
      .sortedBy { it.first }
      .map { it.second }
      .joinToString("")
  }

  override fun second(): Any? {
    return findDiffs()
      .flatMap { (first, second, diff) ->
        val match = DIGITS.filter { it in DIGITS && it + diff in DIGITS }
          .minOf { it }
        listOf(first to match, second to match + diff)
      }
      .sortedBy { it.first }
      .map { it.second }
      .joinToString("")
  }

  private fun findDiffs(): List<Triple<Int, Int, Int>> {
    val rawOps = data
      .split("inp w")
      .filter { it.isNotBlank() }
      .map { block ->
        block.trim().lines().map { Op.parse(it) }
      }

    val pushOut = Op.Div(Arg.Reg(MAPPING.getValue('z')), Arg.Val(26))

    return buildList {
      val stack = ArrayDeque<Pair<Int, Int>>()
      rawOps.forEachIndexed { index, block ->
        if (pushOut in block) {
          val diff = block.last { it is Op.Add && it.a == Arg.Reg(MAPPING.getValue('x')) } as Op.Add
          val linked = stack.removeLast()
          add(Triple(linked.first, index, linked.second + (diff.b as Arg.Val).value))
        } else {
          val diff = block.last { it is Op.Add && it.a == Arg.Reg(MAPPING.getValue('y')) } as Op.Add
          stack.addLast(index to (diff.b as Arg.Val).value)
        }
      }
    }
  }

  sealed interface Op {
    fun apply(regs: IntArray, input: IntArray, pos: Int)

    data class Inp(
      val reg: Int,
    ) : Op {
      override fun apply(regs: IntArray, input: IntArray, pos: Int) {
        regs[reg] = input[pos]
      }
    }

    data class Add(
      val a: Arg,
      val b: Arg,
    ) : Op {
      override fun apply(regs: IntArray, input: IntArray, pos: Int) {
        regs[(a as Arg.Reg).reg] = a.getValue(regs) + b.getValue(regs)
      }
    }

    data class Mul(
      val a: Arg,
      val b: Arg,
    ) : Op {
      override fun apply(regs: IntArray, input: IntArray, pos: Int) {
        regs[(a as Arg.Reg).reg] = a.getValue(regs) * b.getValue(regs)
      }
    }

    data class Div(
      val a: Arg,
      val b: Arg,
    ) : Op {
      override fun apply(regs: IntArray, input: IntArray, pos: Int) {
        regs[(a as Arg.Reg).reg] = a.getValue(regs) / b.getValue(regs)
      }
    }

    data class Mod(
      val a: Arg,
      val b: Arg,
    ) : Op {
      override fun apply(regs: IntArray, input: IntArray, pos: Int) {
        regs[(a as Arg.Reg).reg] = ((a.getValue(regs) % b.getValue(regs)) + b.getValue(regs)) % b.getValue(regs)
      }
    }

    data class Eql(
      val a: Arg,
      val b: Arg,
    ) : Op {
      override fun apply(regs: IntArray, input: IntArray, pos: Int) {
        regs[(a as Arg.Reg).reg] = if (a.getValue(regs) == b.getValue(regs)) 1 else 0
      }
    }

    companion object {
      fun parse(raw: String): Op {
        val tokens = raw.split(" ")
        return when (tokens[0]) {
          "inp" -> Inp(MAPPING.getValue(tokens[1].first()))
          "add" -> Add(parseArg(tokens[1]), parseArg(tokens[2]))
          "mul" -> Mul(parseArg(tokens[1]), parseArg(tokens[2]))
          "div" -> Div(parseArg(tokens[1]), parseArg(tokens[2]))
          "mod" -> Mod(parseArg(tokens[1]), parseArg(tokens[2]))
          "eql" -> Eql(parseArg(tokens[1]), parseArg(tokens[2]))
          else -> error("Unknown operation \"$raw\"")
        }
      }

      private fun parseArg(raw: String): Arg = try {
        Arg.Val(raw.toInt())
      } catch (_: Exception) {
        Arg.Reg(MAPPING.getValue(raw.first()))
      }
    }
  }

  sealed interface Arg {
    fun getValue(regs: IntArray): Int

    data class Val(
      val value: Int
    ) : Arg {
      override fun getValue(regs: IntArray): Int = value
    }

    data class Reg(
      val reg: Int
    ) : Arg {
      override fun getValue(regs: IntArray): Int = regs[reg]
    }
  }
}

fun main() = SomeDay.mainify(Day24)
