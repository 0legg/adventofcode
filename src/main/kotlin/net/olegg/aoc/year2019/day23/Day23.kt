package net.olegg.aoc.year2019.day23

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.receiveOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseLongs
import net.olegg.aoc.year2019.DayOf2019
import net.olegg.aoc.year2019.Intcode

/**
 * See [Year 2019, Day 23](https://adventofcode.com/2019/day/23)
 */
@ExperimentalCoroutinesApi
object Day23 : DayOf2019(23) {
  override fun first(data: String): Any? {
    val program = data
        .trim()
        .parseLongs(",")
        .toLongArray()

    return runBlocking {
      val inputs = List(50) { Channel<Long>(Channel.UNLIMITED) }
      val outputs = List(50) { Channel<Long>(Channel.UNLIMITED) }

      (0 until 50).forEach {
        inputs[it].send(it.toLong())

        GlobalScope.launch {
          val intcode = Intcode(program.copyOf())
          intcode.eval(inputs[it], outputs[it])
        }
      }

      while (true) {
        outputs.forEachIndexed { src, output ->
          output.poll()?.let { dst ->
            val x = output.receive()
            val y = output.receive()
            if (dst == 255L) {
              return@runBlocking y
            } else {
              println("Sending ($x, $y) from $src to $dst")
              inputs[dst.toInt()].send(x)
              inputs[dst.toInt()].send(y)
            }
          }
        }
        inputs.forEach {
          if (it.isEmpty) {
            it.send(-1L)
          }
        }
      }
    }
  }
}

@ExperimentalCoroutinesApi
fun main() = SomeDay.mainify(Day23)
