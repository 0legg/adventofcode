package net.olegg.aoc.year2019.day15

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions
import net.olegg.aoc.utils.Directions.D
import net.olegg.aoc.utils.Directions.L
import net.olegg.aoc.utils.Directions.R
import net.olegg.aoc.utils.Directions.U
import net.olegg.aoc.utils.Neighbors4
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.parseLongs
import net.olegg.aoc.year2019.DayOf2019
import net.olegg.aoc.year2019.Intcode

/**
 * See [Year 2019, Day 15](https://adventofcode.com/2019/day/15)
 */
object Day15 : DayOf2019(15) {
  override fun first(data: String): Any? {
    val program = data
        .trim()
        .parseLongs(",")
        .toLongArray()

    val map = mutableMapOf(Vector2D() to (1L to 0))

    runBlocking {
      val input = Channel<Long>(Channel.UNLIMITED)
      val output = Channel<Long>(Channel.UNLIMITED)

      GlobalScope.launch {
        val intcode = Intcode(program)
        intcode.eval(input, output)
        output.close()
      }

      val stack = ArrayDeque<Move>(Neighbors4.map { Move.Forward(it, Vector2D(), 1) })

      launch {
        while (stack.isNotEmpty()) {
          val curr = stack.removeFirst()
          input.send(codes[curr.dir] ?: 0L)
          val result = output.receive()
          if (curr is Move.Forward) {
            val newPosition = curr.position + curr.dir.step
            when (result) {
              0L -> {
                map[newPosition] = 0L to -1
              }
              1L, 2L -> {
                val prev = map[newPosition]
                if (prev == null || prev.second > curr.distance) {
                  map[newPosition] = result to curr.distance
                  stack += Move.Return(returns[curr.dir] ?: throw IllegalStateException())
                  stack += Neighbors4.filter { it != returns[curr.dir] }
                      .filter { (map[newPosition + it.step]?.second ?: Int.MAX_VALUE) > curr.distance + 1 }
                      .map { Move.Forward(it, newPosition, curr.distance + 1) }
                }
              }
            }
          }
        }
      }
    }

    return map.values.first { it.first == 2L }.second
  }

  override fun second(data: String): Any? {
    val program = data
        .trim()
        .parseLongs(",")
        .toLongArray()

    val map = mutableMapOf(Vector2D() to (1L to 0))

    runBlocking {
      val input = Channel<Long>(Channel.UNLIMITED)
      val output = Channel<Long>(Channel.UNLIMITED)

      GlobalScope.launch {
        val intcode = Intcode(program)
        intcode.eval(input, output)
        output.close()
      }

      val stack = ArrayDeque<Move>(Neighbors4.map { Move.Forward(it, Vector2D(), 1) })

      launch {
        while (stack.isNotEmpty()) {
          val curr = stack.removeFirst()
          input.send(codes[curr.dir] ?: 0L)
          val result = output.receive()
          if (curr is Move.Forward) {
            val newPosition = curr.position + curr.dir.step
            when (result) {
              0L -> {
                map[newPosition] = 0L to -1
              }
              1L, 2L -> {
                val prev = map[newPosition]
                if (prev == null || prev.second > curr.distance) {
                  map[newPosition] = result to curr.distance
                  stack += Move.Return(returns[curr.dir] ?: throw IllegalStateException())
                  stack += Neighbors4.filter { it != returns[curr.dir] }
                      .filter { (map[newPosition + it.step]?.second ?: Int.MAX_VALUE) > curr.distance + 1 }
                      .map { Move.Forward(it, newPosition, curr.distance + 1) }
                }
              }
            }
          }
        }
      }
    }

    val start = map.entries.first { it.value.first == 2L }.key
    val filledMap = mutableMapOf(start to 0)
    val queue = ArrayDeque(listOf(start to 0))
    while (queue.isNotEmpty()) {
      val curr = queue.removeFirst()
      Neighbors4.map { curr.first + it.step }
          .filter { it !in filledMap }
          .filter { map[it]?.first == 1L }
          .forEach {
            filledMap[it] = curr.second + 1
            queue.add(it to curr.second + 1)
          }
    }

    return filledMap.values.maxOrNull()
  }

  sealed class Move {
    abstract val dir: Directions

    data class Forward(
        override val dir: Directions,
        val position: Vector2D,
        val distance: Int
    ) : Move()

    data class Return(
        override val dir: Directions
    ) : Move()
  }

  private val returns = mapOf(U to D, D to U, L to R, R to L)
  private val codes = mapOf(U to 1L, D to 2L, L to 3L, R to 4L)
}

fun main() = SomeDay.mainify(Day15)
