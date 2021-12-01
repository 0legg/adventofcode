package net.olegg.aoc.year2019.day9

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.toList
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.produceIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseLongs
import net.olegg.aoc.year2019.DayOf2019
import net.olegg.aoc.year2019.Intcode

/**
 * See [Year 2019, Day 9](https://adventofcode.com/2019/day/9)
 */
object Day9 : DayOf2019(9) {
  override fun first(data: String): Any? {
    val program = data
      .trim()
      .parseLongs(",")
      .toLongArray()

    val result = runBlocking {
      val input = flowOf(1L).produceIn(this)
      val output = Channel<Long>(Channel.UNLIMITED)

      launch {
        val intcode = Intcode(program)
        intcode.eval(input, output)
        output.close()
      }

      return@runBlocking output.toList()
    }

    return result
  }

  override fun second(data: String): Any? {
    val program = data
      .trim()
      .parseLongs(",")
      .toLongArray()

    val result = runBlocking {
      val input = flowOf(2L).produceIn(this)
      val output = Channel<Long>(Channel.UNLIMITED)

      launch {
        val intcode = Intcode(program)
        intcode.eval(input, output)
        output.close()
      }

      return@runBlocking output.toList()
    }

    return result
  }
}

fun main() = SomeDay.mainify(Day9)
