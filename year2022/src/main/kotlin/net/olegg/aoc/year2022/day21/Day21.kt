package net.olegg.aoc.year2022.day21

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2022.DayOf2022

/**
 * See [Year 2022, Day 21](https://adventofcode.com/2022/day/21)
 */
object Day21 : DayOf2022(21) {
  private val op = "([a-z]+): ([a-z]+) ([-+/*]) ([a-z]+)".toRegex()
  private val number = "([a-z]+): ([0-9]+)".toRegex()

  override fun first(): Any? {
    val monkeys = lines.mapNotNull { line ->
      when {
        op.matches(line) -> op.find(line)?.let { match ->
          val (name, left, op, right) = match.destructured
          when (op) {
            "+" -> Monkey.Op.Plus(
              name = name,
              left = left,
              right = right,
            )
            "-" -> Monkey.Op.Minus(
              name = name,
              left = left,
              right = right,
            )
            "*" -> Monkey.Op.Mult(
              name = name,
              left = left,
              right = right,
            )
            "/" -> Monkey.Op.Div(
              name = name,
              left = left,
              right = right,
            )
            else -> null
          }
        }
        number.matches(line) -> number.find(line)?.let { match ->
          val (name, number) = match.destructured
          Monkey.Value(
            name = name,
            value = number.toLong(),
          )
        }
        else -> null
      }
    }
      .associateBy { it.name }
      .toMutableMap()

    while (monkeys["root"] !is Monkey.Value) {
      val canResolve = monkeys
        .filter { it.value is Monkey.Op }
        .filter {
          val op = it.value as Monkey.Op
          monkeys[op.left] is Monkey.Value && monkeys[op.right] is Monkey.Value
        }

      canResolve.forEach { (key, value) ->
        val op = value as Monkey.Op
        val left = monkeys[op.left] as Monkey.Value
        val right = monkeys[op.right] as Monkey.Value
        monkeys[key] = Monkey.Value(
          name = key,
          value = when (op) {
            is Monkey.Op.Plus -> left.value + right.value
            is Monkey.Op.Minus -> left.value - right.value
            is Monkey.Op.Mult -> left.value * right.value
            is Monkey.Op.Div -> left.value / right.value
          }
        )
      }
    }

    return (monkeys["root"] as Monkey.Value).value
  }

  sealed interface Monkey {
    val name: String

    data class Value(
      override val name: String,
      val value: Long,
    ) : Monkey

    sealed interface Op : Monkey {
      val left: String
      val right: String

      data class Plus(
        override val name: String,
        override val left: String,
        override val right: String,
      ): Op

      data class Minus(
        override val name: String,
        override val left: String,
        override val right: String,
      ): Op

      data class Mult(
        override val name: String,
        override val left: String,
        override val right: String,
      ): Op

      data class Div(
        override val name: String,
        override val left: String,
        override val right: String,
      ): Op
    }
  }
}

fun main() = SomeDay.mainify(Day21)
