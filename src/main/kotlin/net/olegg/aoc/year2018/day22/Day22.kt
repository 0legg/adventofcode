package net.olegg.aoc.year2018.day22

import net.olegg.aoc.someday.SomeDay
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

    val erosion = ErosionCache(depth.toLong(), tx, ty)

    return (0..tx).sumBy { x ->
      (0..ty).sumBy { y ->
        erosion.get(x, y).toInt() % 3
      }
    }
  }

  override fun second(data: String): Any? {
    val (depthLine, targetLine) = data.trim().lines().map { it.substringAfter(": ") }
    val depth = depthLine.toInt()
    val (tx, ty) = targetLine.parseInts(",")
    val target = Config(tx, ty, Tool.Torch)
    val directions = listOf((1 to 0), (-1 to 0), (0 to 1), (0 to -1))

    val erosions = ErosionCache(depth.toLong(), tx, ty)
    val queue = PriorityQueue<Pair<Config, Int>>(
        compareBy({ it.second }, { it.first.x }, { it.first.y }, { it.first.tool }))
    val start = Config(0, 0, Tool.Torch)
    val visited = mutableSetOf<Config>()
    queue.add(start to 0)
    while (queue.isNotEmpty()) {
      val (curr, time) = queue.poll()
      val (x, y, tool) = curr
      if (curr == target) {
        return time
      }
      if (curr in visited) continue
      visited += curr
      val surface = erosions.get(x, y) % 3
      if (surface !in tool.surfaces) continue
      queue += Tool.values()
          .filter { it != tool }
          .filter { surface in it.surfaces }
          .map { curr.copy(tool = it) to time + 7 }
      queue += directions
          .map { (dx, dy) -> curr.copy(x = x + dx, y = y + dy) }
          .filter { it.x >= 0 && it.y >= 0 }
          .filter { erosions.get(it.x, it.y) % 3 in it.tool.surfaces }
          .map { it to time + 1 }
    }
    return 0
  }

  private class ErosionCache(val depth: Long, val tx: Int, val ty: Int) {
    private val cache = mutableMapOf<Pair<Int, Int>, Long>()

    fun get(x: Int, y: Int): Long {
      return cache.getOrElse(x to y) {
        when {
          x == 0 && y == 0 -> 0L
          x == tx && y == ty -> 0L
          y == 0 -> x * 16807L
          x == 0 -> y * 48271L
          else -> get(x - 1, y) * get(x, y - 1)
        }.let {
          (it + depth) % 20183L
        }.also {
          cache[x to y] = it
        }
      }
    }
  }

  private enum class Tool(val surfaces: Set<Long>) {
    Torch(setOf(0, 2)),
    Climb(setOf(0, 1)),
    Neither(setOf(1, 2))
  }
  private data class Config(val x: Int, val y: Int, val tool: Tool)
}

fun main() = SomeDay.mainify(Day22)
