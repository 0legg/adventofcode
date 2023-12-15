package net.olegg.aoc.year2023.day15

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.toPair
import net.olegg.aoc.year2023.DayOf2023
import net.olegg.aoc.year2023.day15.Day15.Step.Companion.toStep

/**
 * See [Year 2023, Day 15](https://adventofcode.com/2023/day/15)
 */
object Day15 : DayOf2023(15) {
  override fun first(): Any? {
    return data.split(",").sumOf { step ->
      step.hash()
    }
  }

  override fun second(): Any? {
    val steps = data.split(",").map { it.toStep() }

    val boxes = List(256) { LinkedHashMap<String, Int>() }

    steps.forEach { step ->
      val index = step.label.hash()
      when (step) {
        is Step.Remove -> boxes[index].remove(step.label)
        is Step.Add -> boxes[index][step.label] = step.value
      }
    }

    return boxes.mapIndexed { box, lenses ->
      (box + 1) * lenses.values.mapIndexed { index, value -> (index + 1) * value }.sum()
    }.sum()
  }

  private fun String.hash() = fold(0) { acc, char -> (acc + char.code) * 17 % 256 }

  sealed interface Step {
    val label: String

    data class Add(
      override val label: String,
      val value: Int,
    ) : Step

    data class Remove(
      override val label: String,
    ) : Step

    companion object {
      fun String.toStep() = when {
        last() == '-' -> Remove(dropLast(1))
        else -> split("=").toPair().let { Add(it.first, it.second.toInt()) }
      }
    }
  }
}

fun main() = SomeDay.mainify(Day15)
