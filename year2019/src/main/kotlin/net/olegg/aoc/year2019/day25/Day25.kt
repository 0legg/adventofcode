package net.olegg.aoc.year2019.day25

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseLongs
import net.olegg.aoc.year2019.DayOf2019
import net.olegg.aoc.year2019.Intcode

/**
 * See [Year 2019, Day 25](https://adventofcode.com/2019/day/25)
 */
object Day25 : DayOf2019(25) {
  override fun first(): Any? {
    val program = data
      .trim()
      .parseLongs(",")
      .toLongArray()

    return runBlocking {
      val input = Channel<Long>(Channel.UNLIMITED)
      val output = Channel<Long>(Channel.UNLIMITED)

      val intCodeJob = launch(Dispatchers.Default) {
        val intcode = Intcode(program)
        intcode.eval(input, output)
      }

      val ioJob = launch(Dispatchers.IO) {
        output.receiveAsFlow().collect {
          print(it.toInt().toChar())
        }
      }

      val cliJob = async(Dispatchers.IO) {
        generateSequence { readln() }
          .takeWhile { it != "!quit" }
          .flatMap { it.toList() + '\n' }
          .map { it.code.toLong() }
          .asFlow()
          .collect(input::send)
      }

      cliJob.await()
      ioJob.cancel()
      intCodeJob.cancel()
      return@runBlocking 0
    }
  }
}

fun main() = SomeDay.mainify(Day25)
