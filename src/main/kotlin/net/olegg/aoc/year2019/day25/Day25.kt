package net.olegg.aoc.year2019.day25

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
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
  override fun first(data: String): Any? {
    val program = data
      .trim()
      .parseLongs(",")
      .toLongArray()

    return runBlocking {
      val input = Channel<Long>(Channel.UNLIMITED)
      val output = Channel<Long>(Channel.UNLIMITED)

      launch(Dispatchers.Default) {
        val intcode = Intcode(program)
        intcode.eval(input, output)
      }

      launch(Dispatchers.Default) {
        while (!output.isClosedForReceive) {
          print(output.receive().toInt().toChar())
        }
      }

      do {
        val command = readLine().orEmpty()
        (command + "\n")
          .map { it.code.toLong() }
          .forEach { input.send(it) }
      } while (command != "!quit")

      return@runBlocking 0
    }
  }
}

fun main() = SomeDay.mainify(Day25)
