package net.olegg.aoc.year2018.day23

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2018.DayOf2018
import java.util.PriorityQueue
import kotlin.math.abs

/**
 * See [Year 2018, Day 23](https://adventofcode.com/2018/day/23)
 */
object Day23 : DayOf2018(23) {
  private val PATTERN = "pos=<(-?\\d+),(-?\\d+),(-?\\d+)>, r=(-?\\d+)".toRegex()

  override fun first(data: String): Any? {
    val bots = data
      .trim()
      .lines()
      .mapNotNull { line ->
        PATTERN.matchEntire(line)?.let { match ->
          val (x, y, z, r) = match.groupValues.drop(1).map { it.toLong() }
          return@mapNotNull Bot(x, y, z, r)
        }
      }

    val strong = bots.maxByOrNull { it.r } ?: bots.first()

    return bots.count { strong.distance(it) <= strong.r }
  }

  override fun second(data: String): Any? {
    val bots = data
      .trim()
      .lines()
      .mapNotNull { line ->
        PATTERN.matchEntire(line)?.let { match ->
          val (x, y, z, r) = match.groupValues.drop(1).map { it.toLong() }
          return@mapNotNull Bot(x, y, z, r)
        }
      }

    var best = bots.count { it.distance(0, 0, 0) <= it.r } to 0L
    val minX = bots.map { it.x }.minOrNull() ?: 0
    val maxX = bots.map { it.x }.maxOrNull() ?: 0
    val minY = bots.map { it.y }.minOrNull() ?: 0
    val maxY = bots.map { it.y }.maxOrNull() ?: 0
    val minZ = bots.map { it.z }.minOrNull() ?: 0
    val maxZ = bots.map { it.z }.maxOrNull() ?: 0

    val queue = PriorityQueue<Pair<Box, Int>>(compareBy({ -it.second }, { it.first.size }))
    queue.add(Box(minX, minY, minZ, maxX, maxY, maxZ) to bots.count())

    while (queue.isNotEmpty()) {
      val (box, botsIn) = queue.poll()
      if (botsIn < best.first) continue
      val selected = bots.filter { it.distance(box) <= it.r }
      if (box.size <= 32) {
        box.points().forEach { (x, y, z) ->
          val distance = abs(x) + abs(y) + abs(z)
          val botsCount = selected.count { it.distance(x, y, z) <= it.r }
          if (botsCount > best.first || (botsCount == best.first && distance < best.second)) {
            best = botsCount to distance
            queue.removeIf { it.second < botsCount }
          }
        }
        continue
      }
      val splits = box.split()
        .map { newBox -> newBox to selected.count { it.distance(newBox) <= it.r } }
        .filter { it.second >= best.first }

      queue.addAll(splits)
    }

    return best
  }

  data class Bot(
    val x: Long,
    val y: Long,
    val z: Long,
    val r: Long
  ) {
    fun distance(other: Bot): Long {
      return abs(x - other.x) + abs(y - other.y) + abs(z - other.z)
    }

    fun distance(otherX: Long, otherY: Long, otherZ: Long): Long {
      return abs(x - otherX) + abs(y - otherY) + abs(z - otherZ)
    }

    fun distance(box: Box): Long {
      return distance(x.coerceIn(box.minX, box.maxX), y.coerceIn(box.minY, box.maxY), z.coerceIn(box.minZ, box.maxZ))
    }
  }

  data class Box(
    val minX: Long,
    val minY: Long,
    val minZ: Long,
    val maxX: Long,
    val maxY: Long,
    val maxZ: Long
  ) {
    fun split(): List<Box> {
      val midX = (minX + maxX) / 2
      val midY = (minY + maxY) / 2
      val midZ = (minZ + maxZ) / 2
      return listOf(
        Box(minX, minY, minZ, midX, midY, midZ),
        Box(midX + 1, minY, minZ, maxX, midY, midZ),
        Box(minX, midY + 1, minZ, midX, maxY, midZ),
        Box(midX + 1, midY + 1, minZ, maxX, maxY, midZ),
        Box(minX, minY, midZ + 1, midX, midY, maxZ),
        Box(midX + 1, minY, midZ + 1, maxX, midY, maxZ),
        Box(minX, midY + 1, midZ + 1, midX, maxY, maxZ),
        Box(midX + 1, midY + 1, midZ + 1, maxX, maxY, maxZ)
      )
    }

    val size = maxOf(maxX - minX, maxY - minY, maxZ - minZ)

    fun points(): Sequence<Triple<Long, Long, Long>> {
      return (minX..maxX).asSequence().flatMap { x ->
        (minY..maxY).asSequence().flatMap { y ->
          (minZ..maxZ).asSequence().map { z ->
            Triple(x, y, z)
          }
        }
      }
    }
  }
}

fun main() = SomeDay.mainify(Day23)
