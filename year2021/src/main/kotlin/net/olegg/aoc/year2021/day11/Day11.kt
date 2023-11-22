package net.olegg.aoc.year2021.day11

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions.Companion.NEXT_8
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.fit
import net.olegg.aoc.utils.get
import net.olegg.aoc.utils.set
import net.olegg.aoc.year2021.DayOf2021

/**
 * See [Year 2021, Day 11](https://adventofcode.com/2021/day/11)
 */
object Day11 : DayOf2021(11) {
  override fun first(): Any? {
    val start = lines.map { line -> line.map { it.digitToInt() } }

    val (_, result) = (0..<100).fold(start to 0) { (field, flash), _ ->
      val new = field.map { line ->
        line.map { it + 1 }.toMutableList()
      }

      val toFlash = new.flatMapIndexed { y, line ->
        line.mapIndexedNotNull { x, value ->
          Vector2D(x, y).takeIf { value > 9 }
        }
      }

      val flashed = mutableSetOf<Vector2D>()
      val queue = ArrayDeque(toFlash)
      while (queue.isNotEmpty()) {
        val curr = queue.removeFirst()
        if (curr !in flashed) {
          flashed += curr
          NEXT_8.map { curr + it.step }
            .filter { it !in flashed }
            .filter { new.fit(it) }
            .forEach { new[it] = new[it]!! + 1 }
          queue += NEXT_8.map { curr + it.step }
            .filter { new.fit(it) }
            .filter { new[it]!! > 9 }
        }
      }

      flashed.forEach { new[it] = 0 }

      return@fold new.map { it.toList() } to flash + flashed.size
    }

    return result
  }

  override fun second(): Any? {
    val start = lines.map { line -> line.map { it.digitToInt() } }

    val allSize = start.flatten().size

    return generateSequence(1) { it + 1 }
      .scan(Triple(start, 0, 0)) { (field, _, _), step ->
        val new = field.map { line ->
          line.map { it + 1 }.toMutableList()
        }

        val toFlash = new.flatMapIndexed { y, line ->
          line.mapIndexedNotNull { x, value ->
            Vector2D(x, y).takeIf { value > 9 }
          }
        }

        val flashed = mutableSetOf<Vector2D>()
        val queue = ArrayDeque(toFlash)
        while (queue.isNotEmpty()) {
          val curr = queue.removeFirst()
          if (curr !in flashed) {
            flashed += curr
            NEXT_8.map { curr + it.step }
              .filter { it !in flashed }
              .filter { new.fit(it) }
              .forEach { new[it] = new[it]!! + 1 }
            queue += NEXT_8.map { curr + it.step }
              .filter { new.fit(it) }
              .filter { new[it]!! > 9 }
          }
        }

        flashed.forEach { new[it] = 0 }

        return@scan Triple(new.map { it.toList() }, flashed.size, step)
      }
      .first { it.second == allSize }
      .third
  }
}

fun main() = SomeDay.mainify(Day11)
