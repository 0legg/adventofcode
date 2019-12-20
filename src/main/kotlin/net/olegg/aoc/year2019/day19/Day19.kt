package net.olegg.aoc.year2019.day19

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.toList
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Vector2D
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
        (0L..49L).forEach { y ->
          (0L..49L).forEach { x ->
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

//      println(outs.chunked(50).joinToString(separator = "\n", prefix = "\n") { row ->
//        row.joinToString(separator = "") { if (it == 1L) "#" else "." }
//      })

      return@runBlocking output.toList().count { it == 1L }
    }
  }

  override fun second(data: String): Any? {
    val program = data
        .trim()
        .parseLongs(",")
        .toLongArray()

    return runBlocking {
      val stepRight = Vector2D(1, 0)
      val stepDown = Vector2D(0, 1)
      val input = Channel<Long>(Channel.UNLIMITED)
      val output = Channel<Long>(Channel.UNLIMITED)

      val head = Vector2D()
      val tail = Vector2D()
      val bottom = Vector2D()

      while (tail.x - head.x < 100 && bottom.y - head.y < 100) {
        tail.x = head.x
        tail.y = head.y
        do {
          tail += stepRight
          launch {
            val intcode = Intcode(program.copyOf())
            intcode.eval(input, output)
          }
          input.send(tail.x.toLong())
          input.send(tail.y.toLong())
        } while (output.receive() == 1L)

        while (tail.x - head.x >= 100 && bottom.y - head.y < 100) {
          bottom.x = head.x
          bottom.y = head.y
          do {
            bottom += stepDown
            launch {
              val intcode = Intcode(program.copyOf())
              intcode.eval(input, output)
            }
            input.send(bottom.x.toLong())
            input.send(bottom.y.toLong())
          } while (output.receive() == 1L)

          if (bottom.y - head.y < 100) {
            head += stepRight
          }
        }

        if (tail.x - head.x < 100 || bottom.y - head.y < 100) {
          head += stepDown
          launch {
            val intcode = Intcode(program.copyOf())
            intcode.eval(input, output)
          }
          input.send(head.x.toLong())
          input.send(head.y.toLong())
          while (output.receive() == 0L) {
            if (head.x > tail.x + 1) {
              head.x = tail.x
              head += stepDown
            } else {
              head += stepRight
            }
            launch {
              val intcode = Intcode(program.copyOf())
              intcode.eval(input, output)
            }
            input.send(head.x.toLong())
            input.send(head.y.toLong())
          }
        }
      }
      input.close()
      output.close()
      return@runBlocking head
    }.let { it.x * 10000 + it.y }
  }
}

fun main() = SomeDay.mainify(Day19)
