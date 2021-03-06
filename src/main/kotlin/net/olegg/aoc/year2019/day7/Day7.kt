package net.olegg.aoc.year2019.day7

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.toList
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseLongs
import net.olegg.aoc.utils.permutations
import net.olegg.aoc.year2019.DayOf2019
import net.olegg.aoc.year2019.Intcode

/**
 * See [Year 2019, Day 7](https://adventofcode.com/2019/day/7)
 */
@ExperimentalCoroutinesApi
object Day7 : DayOf2019(7) {
  override fun first(data: String): Any? {
    val program = data
      .trim()
      .parseLongs(",")
      .toLongArray()

    val basePhases = List(5) { it.toLong() }

    return basePhases.permutations()
      .map { phases ->
        val inputs = phases.map { phase ->
          Channel<Long>(UNLIMITED).also { ch ->
            runBlocking {
              ch.send(phase)
            }
          }
        } + Channel(UNLIMITED)

        return@map runBlocking {
          inputs.zipWithNext()
            .forEach { (input, output) ->
              launch {
                val intcode = Intcode(program.copyOf())
                intcode.eval(input, output)
              }
            }

          inputs.first().send(0)
          return@runBlocking inputs.last().receive()
        }
      }
      .maxOrNull()
  }

  override fun second(data: String): Any? {
    val program = data
      .trim()
      .parseLongs(",")
      .toLongArray()

    val basePhases = List(5) { it + 5L }

    return basePhases.permutations()
      .map { phases ->
        val inputs = phases.map { phase ->
          ConflatedBroadcastChannel(phase)
        }

        val outputs = inputs.drop(1) + inputs.first()

        return@map runBlocking {
          coroutineScope {
            inputs.zip(outputs)
              .forEach { (input, output) ->
                launch {
                  val intcode = Intcode(program.copyOf())
                  intcode.eval(input.openSubscription(), output)
                }
              }

            launch { inputs.first().send(0) }
          }

          launch {
            inputs.forEach { it.close() }
          }

          return@runBlocking inputs.first().openSubscription().toList().last()
        }
      }
      .maxOrNull()
  }
}

@ExperimentalCoroutinesApi
fun main() = SomeDay.mainify(Day7)
