package net.olegg.aoc.year2019.day13

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.parseLongs
import net.olegg.aoc.year2019.DayOf2019
import net.olegg.aoc.year2019.Intcode

/**
 * See [Year 2019, Day 13](https://adventofcode.com/2019/day/13)
 */
object Day13 : DayOf2019(13) {
  override fun first(data: String): Any? {
    val program = data
        .trim()
        .parseLongs(",")
        .toLongArray()

    val result = runBlocking {
      val input = Channel<Long>(Channel.UNLIMITED)
      val output = Channel<Long>(Channel.UNLIMITED)

      launch {
        val intcode = Intcode(program)
        intcode.eval(input, output)
        output.close()
      }

      val field = output.toList()
          .map { it.toInt() }
          .chunked(3)
          .map { (x, y, tile) -> Vector2D(x, y) to tile }
          .toMap()

      return@runBlocking field
    }

    return result.count { it.value == 2 }
  }
}

fun main() = SomeDay.mainify(Day13)
