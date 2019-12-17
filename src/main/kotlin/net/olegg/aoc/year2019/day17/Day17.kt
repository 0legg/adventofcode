package net.olegg.aoc.year2019.day17

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Neighbors4
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.parseLongs
import net.olegg.aoc.year2019.DayOf2019
import net.olegg.aoc.year2019.Intcode

/**
 * See [Year 2019, Day 17](https://adventofcode.com/2019/day/17)
 */
object Day17 : DayOf2019(17) {
  override fun first(data: String): Any? {
    val program = data
        .trim()
        .parseLongs(",")
        .toLongArray()

    return runBlocking {
      val input = Channel<Long>(Channel.UNLIMITED)
      val output = Channel<Long>(Channel.UNLIMITED)

      GlobalScope.launch {
        val intcode = Intcode(program)
        intcode.eval(input, output)
        output.close()
      }

      val map = output.toList()
          .map { it.toChar() }
          .joinToString(separator = "")
          .trim()
          .lines()
          .map { it.toList() }

      return@runBlocking map.mapIndexed { y, row ->
        row.mapIndexedNotNull { x, c ->
          val pos = Vector2D(x, y)

          if (c == '#' && Neighbors4.map { pos + it.step }
              .all { it.x in row.indices && it.y in map.indices && map[it.y][it.x] == '#' }) {
            x
          } else {
            null
          }
        }.sumBy { it * y }
      }.sum()
    }
  }
}

fun main() = SomeDay.mainify(Day17)
