package net.olegg.aoc.year2019.day5

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
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
 * See [Year 2019, Day 5](https://adventofcode.com/2019/day/5)
 */
object Day5 : DayOf2019(5) {
  override fun first(): Any? {
    val program = data
      .trim()
      .parseLongs(",")
      .toLongArray()

    val result = runBlocking {
      val input = flowOf(1L).produceIn(this)
      val output = Channel<Long>(UNLIMITED)

      launch {
        val intcode = Intcode(program)
        intcode.eval(input, output)
        output.close()
      }

      return@runBlocking output.toList()
    }

    return result.last()
  }

  override fun second(): Any? {
    val program = data
      .trim()
      .parseLongs(",")
      .toLongArray()

    val result = runBlocking {
      val input = flowOf(5L).produceIn(this)
      val output = Channel<Long>(UNLIMITED)

      launch {
        val intcode = Intcode(program)
        intcode.eval(input, output)
        output.close()
      }

      return@runBlocking output.toList()
    }

    return result.last()
  }
}

fun main() = SomeDay.mainify(Day5)
