package net.olegg.aoc.year2022.day11

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.year2022.DayOf2022

/**
 * See [Year 2022, Day 11](https://adventofcode.com/2022/day/11)
 */
object Day11 : DayOf2022(11) {
  override fun first(): Any? {
    val monkeys = data.split("\n\n")
      .map { monkeyData ->
        val lines = monkeyData.lines()
        val number = lines[0].split(" ").last().dropLast(1).toInt()
        val items = lines[1].substringAfter(": ").parseInts(", ")
        val (rawOp, value) = lines[2].split(" ").takeLast(2)
        val op = when (rawOp) {
          "+" -> Op.Add(value.toIntOrNull()?.let { Arg.Val(it) } ?: Arg.Self)
          "*" -> Op.Mult(value.toIntOrNull()?.let { Arg.Val(it) } ?: Arg.Self)
          else -> error("Unable to parse $rawOp")
        }
        val test = lines[3].split(" ").last().toInt()
        val ifTrue = lines[4].split(" ").last().toInt()
        val ifFalse = lines[5].split(" ").last().toInt()

        Monkey(
          number = number,
          items = ArrayDeque(items),
          op = op,
          test = test,
          targets = ifTrue to ifFalse,
        )
      }

    val inspected = MutableList(monkeys.size) { 0 }

    repeat(20) {
      monkeys.forEachIndexed { index, monkey ->
        inspected[index] += monkey.items.size
        while (monkey.items.isNotEmpty()) {
          val item = monkey.items.removeFirst()
          val newItem = monkey.op.apply(item) / 3
          val target = if (newItem % monkey.test == 0) monkey.targets.first else monkey.targets.second
          monkeys[target].items += newItem
        }
      }
    }

    return inspected
      .sortedDescending()
      .take(2)
      .reduce(Int::times)
  }

  data class Monkey(
    val number: Int,
    val items: ArrayDeque<Int>,
    val op: Op,
    val test: Int,
    val targets: Pair<Int, Int>,
  )

  sealed interface Op {
    fun apply(to: Int): Int

    data class Add(
      val value: Arg,
    ) : Op {
      override fun apply(to: Int): Int = to + value.apply(to)
    }

    data class Mult(
      val value: Arg,
    ) : Op {
      override fun apply(to: Int): Int = to * value.apply(to)
    }
  }

  sealed interface Arg {
    fun apply(to: Int): Int

    data class Val(
      val value: Int,
    ): Arg {
      override fun apply(to: Int): Int = value
    }

    object Self : Arg {
      override fun apply(to: Int): Int = to
    }
  }
}

fun main() = SomeDay.mainify(Day11)
