package net.olegg.aoc.year2018.day22

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Neighbors4
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.year2018.DayOf2018
import java.util.PriorityQueue

/**
 * See [Year 2018, Day 22](https://adventofcode.com/2018/day/22)
 */
object Day22 : DayOf2018(22) {
  override fun first(data: String): Any? {
    val (depthLine, targetLine) = data.trim().lines().map { it.substringAfter(": ") }
    val depth = depthLine.toInt()
    val (tx, ty) = targetLine.parseInts(",")
    val t = Vector2D(tx, ty)
    val erosion = ErosionCache(depth.toLong(), t)

    return (0..tx).sumOf { x ->
      (0..ty).sumOf { y ->
        erosion[Vector2D(x, y)].toInt() % 3
      }
    }
  }

  override fun second(data: String): Any? {
    val (depthLine, targetLine) = data.trim().lines().map { it.substringAfter(": ") }
    val depth = depthLine.toInt()
    val (tx, ty) = targetLine.parseInts(",")
    val t = Vector2D(tx, ty)
    val target = Config(t, Tool.Torch)

    val erosions = ErosionCache(depth.toLong(), t)
    val queue = PriorityQueue<Pair<Config, Int>>(
      compareBy({ it.second }, { it.first.pos.x }, { it.first.pos.y }, { it.first.tool })
    )
    val start = Config(Vector2D(), Tool.Torch)
    val visited = mutableSetOf<Config>()
    queue.add(start to 0)
    while (queue.isNotEmpty()) {
      val (curr, time) = queue.poll()
      val (pos, tool) = curr
      if (curr == target) {
        return time
      }
      if (curr in visited) continue
      visited += curr
      val surface = erosions[pos] % 3
      if (surface !in tool.surfaces) continue
      queue += Tool.values()
        .filter { it != tool }
        .filter { surface in it.surfaces }
        .map { curr.copy(tool = it) to time + 7 }
      queue += Neighbors4
        .map { curr.copy(pos = curr.pos + it.step) }
        .filter { it.pos.x >= 0 && it.pos.y >= 0 }
        .filter { erosions[it.pos] % 3 in it.tool.surfaces }
        .map { it to time + 1 }
    }
    return 0
  }

  private class ErosionCache(val depth: Long, val t: Vector2D) {
    private val cache = mutableMapOf<Vector2D, Long>()

    operator fun get(pos: Vector2D): Long {
      return cache.getOrElse(pos) {
        when {
          pos.x == 0 && pos.y == 0 -> 0L
          pos == t -> 0L
          pos.y == 0 -> pos.x * 16807L
          pos.x == 0 -> pos.y * 48271L
          else -> get(pos.copy(x = pos.x - 1)) * get(pos.copy(y = pos.y - 1))
        }.let {
          (it + depth) % 20183L
        }.also {
          cache[pos] = it
        }
      }
    }
  }

  private enum class Tool(val surfaces: Set<Long>) {
    Torch(setOf(0, 2)),
    Climb(setOf(0, 1)),
    Neither(setOf(1, 2))
  }

  private data class Config(
    val pos: Vector2D,
    val tool: Tool
  )
}

fun main() = SomeDay.mainify(Day22)
