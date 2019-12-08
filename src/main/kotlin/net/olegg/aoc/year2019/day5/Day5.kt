package net.olegg.aoc.year2019.day5

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.channels.toList
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.produceIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.year2019.DayOf2019
import net.olegg.aoc.year2019.Intcode

/**
 * See [Year 2019, Day 5](https://adventofcode.com/2019/day/5)
 */
@ExperimentalCoroutinesApi
@UseExperimental(FlowPreview::class)
object Day5 : DayOf2019(5) {
  override fun first(data: String): Any? {
    val program = data
        .trim()
        .parseInts(",")
        .toIntArray()

    val result = runBlocking {
      val input = flowOf(1).produceIn(GlobalScope)
      val output = Channel<Int>(UNLIMITED)

      launch {
        Intcode.eval(program, input, output)
        output.close()
      }

      return@runBlocking output.toList()
    }

    return result.last()
  }

  override fun second(data: String): Any? {
    val program = data
        .trim()
        .parseInts(",")
        .toIntArray()

    val result = runBlocking {
      val input = flowOf(5).produceIn(GlobalScope)
      val output = Channel<Int>(UNLIMITED)

      launch {
        Intcode.eval(program, input, output)
        output.close()
      }

      return@runBlocking output.toList()
    }

    return result.last()
  }
}

@ExperimentalCoroutinesApi
fun main() = SomeDay.mainify(Day5)
