package net.olegg.adventofcode.year2017.day24

import java.util.ArrayDeque
import java.util.BitSet
import kotlin.math.max
import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2017.DayOf2017

/**
 * @see <a href="http://adventofcode.com/2017/day/24">Year 2017, Day 24</a>
 */
class Day24 : DayOf2017(24) {
  override fun first(data: String): Any? {
    val ports = data
        .trim()
        .lines()
        .map { it.split("/") }
        .mapIndexed { index, value ->
          Triple((value.min()?.toInt() ?: 0), (value.max()?.toInt() ?: 0), index)
        }

    var best = 0
    val queue = ArrayDeque(ports.filter { it.first == 0 || it.second == 0 }
        .map { port ->
          val next = if (port.first == 0) port.second else port.first
          return@map Triple(next, next, BitSet(ports.size).also { it.set(port.third) })
        }
    )

    val visited = queue.map { it.first to it.third }.toMutableSet()

    while (queue.isNotEmpty()) {
      val curr = queue.pop()
      best = max(best, curr.second)
      ports
          .filter { !curr.third[it.third] }
          .filter { it.first == curr.first || it.second == curr.first }
          .map { port ->
            val next = if (port.first == curr.first) port.second else port.first
            Triple(
                next,
                curr.second + port.first + port.second,
                BitSet(ports.size).also { it.or(curr.third) }.also { it.set(port.third) }
            )
          }
          .filterNot { (it.first to it.third) in visited }
          .forEach { port ->
            visited.add(port.first to port.third)
            queue.push(port)
          }
    }

    return best
  }

  override fun second(data: String): Any? {
    val ports = data.trim().lines()
        .map { it.split("/") }
        .mapIndexed { index, value -> Triple((value.min()?.toInt() ?: 0), (value.max()?.toInt() ?: 0), index) }

    val queue = ArrayDeque(ports.filter { it.first == 0 || it.second == 0 }
        .map { port ->
          val next = if (port.first == 0) port.second else port.first
          return@map Triple(next, (2 to next), BitSet(ports.size).also { it.set(port.third) })
        }
    )

    val visited = queue.map { it.first to it.third }.toMutableSet()
    var best = 0 to 0

    while (queue.isNotEmpty()) {
      val curr = queue.pop()
      best = listOf(best, curr.second).maxWith(compareBy({ it.first }, { it.second })) ?: (0 to 0)
      ports.filter { !curr.third[it.third] }
          .filter { it.first == curr.first || it.second == curr.first }
          .map { port ->
            val next = if (port.first == curr.first) port.second else port.first
            Triple(
                next,
                (curr.second.first + 1 to curr.second.second + port.first + port.second),
                BitSet(ports.size).also { it.or(curr.third) }.also { it.set(port.third) }
            )
          }
          .filterNot { (it.first to it.third) in visited }
          .forEach { port ->
            visited.add(port.first to port.third)
            queue.push(port)
          }
    }

    return best.second
  }
}

fun main(args: Array<String>) = SomeDay.mainify(Day24::class)
