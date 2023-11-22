package net.olegg.aoc.year2018.day23

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Vector3D
import net.olegg.aoc.year2018.DayOf2018
import java.util.PriorityQueue

/**
 * See [Year 2018, Day 23](https://adventofcode.com/2018/day/23)
 */
object Day23 : DayOf2018(23) {
  private val PATTERN = "pos=<(-?\\d+),(-?\\d+),(-?\\d+)>, r=(-?\\d+)".toRegex()

  override fun first(): Any? {
    val bots = lines
      .mapNotNull { line ->
        PATTERN.matchEntire(line)?.let { match ->
          val (x, y, z, r) = match.groupValues.drop(1).map { it.toInt() }
          return@mapNotNull Bot(Vector3D(x, y, z), r)
        }
      }

    val strong = bots.maxBy { it.r }

    return bots.count { strong.distance(it) <= strong.r }
  }

  override fun second(): Any? {
    val bots = lines
      .mapNotNull { line ->
        PATTERN.matchEntire(line)?.let { match ->
          val (x, y, z, r) = match.groupValues.drop(1).map { it.toInt() }
          return@mapNotNull Bot(Vector3D(x, y, z), r)
        }
      }

    var best = bots.count { it.pos.manhattan() <= it.r } to 0
    val minX = bots.minOf { it.pos.x }
    val maxX = bots.maxOf { it.pos.x }
    val minY = bots.minOf { it.pos.y }
    val maxY = bots.maxOf { it.pos.y }
    val minZ = bots.minOf { it.pos.z }
    val maxZ = bots.maxOf { it.pos.z }

    val queue = PriorityQueue<Pair<Box, Int>>(compareBy({ -it.second }, { it.first.size }))
    queue.add(Box(Vector3D(minX, minY, minZ), Vector3D(maxX, maxY, maxZ)) to bots.count())

    while (queue.isNotEmpty()) {
      val (box, botsIn) = queue.poll()
      if (botsIn < best.first) continue
      val selected = bots.filter { it.distance(box) <= it.r }
      if (box.size <= 32) {
        box.points().forEach { point ->
          val distance = point.manhattan()
          val botsCount = selected.count { it.distance(point) <= it.r }
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
    val pos: Vector3D,
    val r: Int,
  ) {
    fun distance(other: Bot): Int {
      return (pos - other.pos).manhattan()
    }

    fun distance(other: Vector3D): Int {
      return (pos - other).manhattan()
    }

    fun distance(
      otherX: Int,
      otherY: Int,
      otherZ: Int
    ): Int {
      return (pos - Vector3D(otherX, otherY, otherZ)).manhattan()
    }

    fun distance(box: Box): Int {
      return distance(
        pos.x.coerceIn(box.min.x, box.max.x),
        pos.y.coerceIn(box.min.y, box.max.y),
        pos.z.coerceIn(box.min.z, box.max.z),
      )
    }
  }

  data class Box(
    val min: Vector3D,
    val max: Vector3D,
  ) {
    fun split(): List<Box> {
      val midX = (min.x + max.x) / 2
      val midY = (min.y + max.y) / 2
      val midZ = (min.z + max.z) / 2
      return listOf(
        Box(Vector3D(min.x, min.y, min.z), Vector3D(midX, midY, midZ)),
        Box(Vector3D(midX + 1, min.y, min.z), Vector3D(max.x, midY, midZ)),
        Box(Vector3D(min.x, midY + 1, min.z), Vector3D(midX, max.y, midZ)),
        Box(Vector3D(midX + 1, midY + 1, min.z), Vector3D(max.x, max.y, midZ)),
        Box(Vector3D(min.x, min.y, midZ + 1), Vector3D(midX, midY, max.z)),
        Box(Vector3D(midX + 1, min.y, midZ + 1), Vector3D(max.x, midY, max.z)),
        Box(Vector3D(min.x, midY + 1, midZ + 1), Vector3D(midX, max.y, max.z)),
        Box(Vector3D(midX + 1, midY + 1, midZ + 1), Vector3D(max.x, max.y, max.z)),
      )
    }

    val size = maxOf(max.x - min.x, max.y - min.y, max.z - min.z)

    fun points(): Sequence<Vector3D> {
      return (min.x..max.x).asSequence().flatMap { x ->
        (min.y..max.y).asSequence().flatMap { y ->
          (min.z..max.z).asSequence().map { z ->
            Vector3D(x, y, z)
          }
        }
      }
    }
  }
}

fun main() = SomeDay.mainify(Day23)
