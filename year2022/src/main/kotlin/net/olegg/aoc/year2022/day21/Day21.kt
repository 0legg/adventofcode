package net.olegg.aoc.year2022.day21

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2022.DayOf2022

/**
 * See [Year 2022, Day 21](https://adventofcode.com/2022/day/21)
 */
object Day21 : DayOf2022(21) {
  private val ROOT = "(root): ([a-z]+) ([-+/*]) ([a-z]+)".toRegex()
  private val HUMAN = "(humn): ([0-9]+)".toRegex()
  private val OP = "([a-z]+): ([a-z]+) ([-+/*]) ([a-z]+)".toRegex()
  private val NUMBER = "([a-z]+): ([0-9]+)".toRegex()

  override fun first(): Any? {
    val monkeys = lines.mapNotNull { line ->
      when {
        OP.matches(line) -> OP.find(line)?.let { match ->
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
        NUMBER.matches(line) -> NUMBER.find(line)?.let { match ->
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
          },
        )
      }
    }

    return (monkeys["root"] as Monkey.Value).value
  }

  override fun second(): Any? {
    val monkeys = lines.mapNotNull { line ->
      when {
        ROOT.matches(line) -> ROOT.find(line)?.let { match ->
          val (name, left, _, right) = match.destructured
          Monkey.Root(
            name = name,
            left = left,
            right = right,
          )
        }
        HUMAN.matches(line) -> HUMAN.find(line)?.let { match ->
          val (name, _) = match.destructured
          Monkey.Human(
            name = name,
            value = 0L,
          )
        }
        OP.matches(line) -> OP.find(line)?.let { match ->
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
        NUMBER.matches(line) -> NUMBER.find(line)?.let { match ->
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

    do {
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
          },
        )
      }
    } while (canResolve.isNotEmpty())

    val root = monkeys["root"] as Monkey.Root
    val rootLeft = monkeys[root.left]
    val rootRight = monkeys[root.right]

    val (rootOp, rootValue) = when {
      rootLeft is Monkey.Op && rootRight is Monkey.Value -> rootLeft to rootRight
      rootRight is Monkey.Op && rootLeft is Monkey.Value -> rootRight to rootLeft
      else -> error("Can't resolve root")
    }

    var curr: Monkey = rootOp
    var currValue = rootValue.value
    while (curr is Monkey.Op) {
      val left = monkeys[curr.left]
      val right = monkeys[curr.right]
      val (nextOp, sideValue) = when {
        left is Monkey.Op && right is Monkey.Value -> left to right.value
        right is Monkey.Op && left is Monkey.Value -> right to left.value
        left is Monkey.Human && right is Monkey.Value -> left to right.value
        right is Monkey.Human && left is Monkey.Value -> right to left.value
        else -> error("Can't resolve $curr $left $right")
      }

      val nextValue = when (curr) {
        is Monkey.Op.Plus -> currValue - sideValue
        is Monkey.Op.Minus -> if (left is Monkey.Value) sideValue - currValue else sideValue + currValue
        is Monkey.Op.Mult -> currValue / sideValue
        is Monkey.Op.Div -> if (left is Monkey.Value) sideValue / currValue else sideValue * currValue
      }
      curr = nextOp
      currValue = nextValue
    }

    return currValue
  }

  sealed interface Monkey {
    val name: String

    data class Value(
      override val name: String,
      val value: Long,
    ) : Monkey

    data class Root(
      override val name: String,
      val left: String,
      val right: String,
    ) : Monkey

    data class Human(
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
      ) : Op

      data class Minus(
        override val name: String,
        override val left: String,
        override val right: String,
      ) : Op

      data class Mult(
        override val name: String,
        override val left: String,
        override val right: String,
      ) : Op

      data class Div(
        override val name: String,
        override val left: String,
        override val right: String,
      ) : Op
    }
  }
}

fun main() = SomeDay.mainify(Day21)
