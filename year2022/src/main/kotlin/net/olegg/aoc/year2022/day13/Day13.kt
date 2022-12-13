package net.olegg.aoc.year2022.day13

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.utils.toPair
import net.olegg.aoc.year2022.DayOf2022
import net.olegg.aoc.year2022.day13.Day13.Node.Collection
import net.olegg.aoc.year2022.day13.Day13.Node.Value

/**
 * See [Year 2022, Day 13](https://adventofcode.com/2022/day/13)
 */
object Day13 : DayOf2022(13) {
  override fun first(): Any? {
    val inputs = data
      .split("\n\n")
      .map { it.lines().toPair() }

    return inputs
      .map { (first, second) ->
        parseLine(first) to parseLine(second)
      }
      .withIndex()
      .filter { (_, value) ->
        value.first < value.second
      }
      .sumOf { it.index + 1 }
  }

  private fun parseLine(line: String): Node {
    val queue = ArrayDeque(line.toList())
    val stack = ArrayDeque<MutableList<Node>>()
    stack.add(mutableListOf())
    while (queue.isNotEmpty()) {
      when (val top = queue.removeFirst()) {
        '[' -> {
          stack.add(mutableListOf())
        }
        ']' -> {
          val items = stack.removeLast()
          stack.last().add(
            Collection(items)
          )
        }
        in '0'..'9' -> {
          val number = buildString {
            append(top)
            while (queue.isNotEmpty() && queue.first() in '0'..'9') {
              append(queue.removeFirst())
            }
          }.toInt()
          stack.last().add(
            Value(number)
          )
        }
        else -> Unit
      }
    }
    return stack.first().first()
  }

  sealed interface Node : Comparable<Node> {
    data class Value(
      val value: Int,
    ) : Node {
      override fun compareTo(other: Node): Int {
        return when (other) {
          is Value -> value - other.value
          is Collection -> Collection(listOf(this)).compareTo(other)
        }
      }
    }

    data class Collection(
      val values: List<Node>,
    ) : Node {
      override fun compareTo(other: Node): Int {
        return when (other) {
          is Value -> this.compareTo(Collection(listOf(other)))
          is Collection -> {
            val maybeDiff = values.zip(other.values).firstOrNull { (first, second) -> first.compareTo(second) != 0 }
            when {
              maybeDiff != null -> maybeDiff.first.compareTo(maybeDiff.second)
              else -> values.size - other.values.size
            }
          }
        }
      }
    }
  }
}

fun main() = SomeDay.mainify(Day13)
