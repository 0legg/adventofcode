package net.olegg.aoc.year2021.day19

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Vector3D
import net.olegg.aoc.utils.pairs
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.year2021.DayOf2021

/**
 * See [Year 2021, Day 19](https://adventofcode.com/2021/day/19)
 */
object Day19 : DayOf2021(19) {
  override fun first(): Any? {
    return findAllData().first.size
  }

  override fun second(): Any? {
    return findAllData()
      .second
      .toList()
      .pairs()
      .maxOf { (a, b) -> (a - b).manhattan() }
  }

  private fun findAllData(): Pair<Set<Vector3D>, Set<Vector3D>> {
    val visibleBeacons = data
      .split("\n\n")
      .map { block ->
        block.lines()
          .drop(1)
          .map { it.parseInts(",") }
          .map { Vector3D(it[0], it[1], it[2]) }
      }

    val dists = visibleBeacons
      .map { beacons ->
        beacons.flatMapIndexed { index, first ->
          beacons.drop(index + 1).map { second ->
            (second - first).length2()
          }
        }.toSet()
      }

    val possiblePairs = visibleBeacons.indices
      .toList()
      .pairs()
      .filter { (indexA, indexB) ->
        dists[indexA].intersect(dists[indexB]).size >= 66
      }
      .flatMap { listOf(it, it.second to it.first) }
      .groupBy { it.first }

    val oriented = mutableSetOf(0)
    val beacons = visibleBeacons[0].toMutableSet()
    val scanners = mutableSetOf(Vector3D())
    val queue = ArrayDeque(possiblePairs[0].orEmpty())
    val orientedBeacons = visibleBeacons.toMutableList()

    while (queue.isNotEmpty()) {
      val curr = queue.removeFirst()
      val (base, new) = when {
        curr.first in oriented && curr.second in oriented -> continue
        curr.first in oriented -> curr.first to curr.second
        curr.second in oriented -> curr.second to curr.first
        else -> error("One of points should be already oriented")
      }

      val baseDists = orientedBeacons[base]
        .indices
        .toList()
        .pairs()
        .map { (a, b) ->
          val beaconA = orientedBeacons[base][a]
          val beaconB = orientedBeacons[base][b]
          Triple(a, b, (beaconB - beaconA).length2())
        }
        .toList()

      val newDists = visibleBeacons[new]
        .indices
        .toList()
        .pairs()
        .map { (a, b) ->
          val beaconA = visibleBeacons[new][a]
          val beaconB = visibleBeacons[new][b]
          Triple(a, b, (beaconB - beaconA).length2())
        }
        .toList()

      val matchCount = mutableMapOf<Pair<Int, Int>, Int>()
      baseDists.forEach { baseTriple ->
        newDists.forEach { newTriple ->
          if (baseTriple.third == newTriple.third) {
            matchCount[baseTriple.first to newTriple.first] =
              matchCount.getOrDefault(baseTriple.first to newTriple.first, 0) + 1
            matchCount[baseTriple.first to newTriple.second] =
              matchCount.getOrDefault(baseTriple.first to newTriple.second, 0) + 1
            matchCount[baseTriple.second to newTriple.first] =
              matchCount.getOrDefault(baseTriple.second to newTriple.first, 0) + 1
            matchCount[baseTriple.second to newTriple.second] =
              matchCount.getOrDefault(baseTriple.second to newTriple.second, 0) + 1
          }
        }
      }

      val matches = matchCount.filterValues { it == 11 }.keys

      val rotation = ALL_ROTATIONS.first { currRotation ->
        matches
          .map { (baseIndex, newIndex) ->
            val baseA = orientedBeacons[base][baseIndex]
            val newA = visibleBeacons[new][newIndex]
            val convertedA = currRotation(newA)
            baseA - convertedA
          }
          .distinct()
          .size == 1
      }

      val delta = matches.first().let { (baseIndex, newIndex) ->
        val baseA = orientedBeacons[base][baseIndex]
        val newA = visibleBeacons[new][newIndex]
        val convertedA = rotation(newA)
        baseA - convertedA
      }

      val rotatedPoints = visibleBeacons[new]
        .map { point ->
          rotation(point) + delta
        }

      beacons += rotatedPoints
      scanners += rotation(Vector3D()) + delta
      orientedBeacons[new] = rotatedPoints
      oriented += new
      queue += possiblePairs[new].orEmpty()
    }

    return beacons to scanners
  }

  private val ALL_ROTATIONS: List<Vector3D.() -> Vector3D> = listOf(
    { Vector3D(x, y, z) },
    { Vector3D(x, y, -z) },
    { Vector3D(x, -y, z) },
    { Vector3D(x, -y, -z) },
    { Vector3D(-x, y, z) },
    { Vector3D(-x, y, -z) },
    { Vector3D(-x, -y, z) },
    { Vector3D(-x, -y, -z) },
    { Vector3D(x, z, y) },
    { Vector3D(x, z, -y) },
    { Vector3D(x, -z, y) },
    { Vector3D(x, -z, -y) },
    { Vector3D(-x, z, y) },
    { Vector3D(-x, z, -y) },
    { Vector3D(-x, -z, y) },
    { Vector3D(-x, -z, -y) },
    { Vector3D(y, x, z) },
    { Vector3D(y, x, -z) },
    { Vector3D(y, -x, z) },
    { Vector3D(y, -x, -z) },
    { Vector3D(-y, x, z) },
    { Vector3D(-y, x, -z) },
    { Vector3D(-y, -x, z) },
    { Vector3D(-y, -x, -z) },
    { Vector3D(y, z, x) },
    { Vector3D(y, z, -x) },
    { Vector3D(y, -z, x) },
    { Vector3D(y, -z, -x) },
    { Vector3D(-y, z, x) },
    { Vector3D(-y, z, -x) },
    { Vector3D(-y, -z, x) },
    { Vector3D(-y, -z, -x) },
    { Vector3D(z, y, x) },
    { Vector3D(z, y, -x) },
    { Vector3D(z, -y, x) },
    { Vector3D(z, -y, -x) },
    { Vector3D(-z, y, x) },
    { Vector3D(-z, y, -x) },
    { Vector3D(-z, -y, x) },
    { Vector3D(-z, -y, -x) },
    { Vector3D(z, x, y) },
    { Vector3D(z, x, -y) },
    { Vector3D(z, -x, y) },
    { Vector3D(z, -x, -y) },
    { Vector3D(-z, x, y) },
    { Vector3D(-z, x, -y) },
    { Vector3D(-z, -x, y) },
    { Vector3D(-z, -x, -y) },
  )
}

fun main() = SomeDay.mainify(Day19)
