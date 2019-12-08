package net.olegg.aoc.year2019.day2

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.channels.toList
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.year2019.DayOf2019
import net.olegg.aoc.year2019.Intcode

/**
 * See [Year 2019, Day 2](https://adventofcode.com/2019/day/2)
 */
@ExperimentalCoroutinesApi
object Day2 : DayOf2019(2) {
  override fun first(data: String): Any? {
    val program = data
        .trim()
        .parseInts(",")
        .toIntArray()
    program[1] = 12
    program[2] = 2
    runBlocking {
      launch {
        Intcode.eval(program)
      }
    }

    return program[0]
  }

  override fun second(data: String): Any? {
    val program = data
        .trim()
        .parseInts(",")
        .toIntArray()

    val result = runBlocking {
      val fit = Channel<Int>(UNLIMITED)
      coroutineScope {
        for (noun in 0..99) {
          for (verb in 0..99) {
            launch {
              val newProgram = program.copyOf()
              newProgram[1] = noun
              newProgram[2] = verb

              Intcode.eval(newProgram)

              if (newProgram[0] == 19690720) {
                fit.send(noun * 100 + verb)
              }
            }
          }
        }
      }

      fit.close()
      return@runBlocking fit.toList()
    }

    return result.first()
  }
}

@ExperimentalCoroutinesApi
fun main() = SomeDay.mainify(Day2)
