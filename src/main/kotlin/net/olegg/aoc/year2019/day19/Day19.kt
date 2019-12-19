package net.olegg.aoc.year2019.day19

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.toList
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseLongs
import net.olegg.aoc.year2019.DayOf2019
import net.olegg.aoc.year2019.Intcode

/**
 * See [Year 2019, Day 19](https://adventofcode.com/2019/day/19)
 */
object Day19 : DayOf2019(19) {
  override fun first(data: String): Any? {
    val program = data
        .trim()
        .parseLongs(",")
        .toLongArray()

    return runBlocking {
      val output = Channel<Long>(Channel.UNLIMITED)

      coroutineScope {
        (0L..49L).forEach { x ->
          (0L..49L).forEach { y ->
            val input = Channel<Long>(Channel.UNLIMITED)
            launch {
              val intcode = Intcode(program.copyOf())
              intcode.eval(input, output)
            }
            input.send(x)
            input.send(y)
          }
        }
      }

      output.close()

      return@runBlocking output.toList().count { it == 1L }
    }
  }
}

fun main() = SomeDay.mainify(Day19)
