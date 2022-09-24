package net.olegg.aoc.year2021.day22

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Vector3D
import net.olegg.aoc.year2021.DayOf2021

/**
 * See [Year 2021, Day 22](https://adventofcode.com/2021/day/22)
 */
object Day22 : DayOf2021(22) {
  private val pattern = "(on|off) x=(-?\\d+)\\.\\.(-?\\d+),y=(-?\\d+)\\.\\.(-?\\d+),z=(-?\\d+)\\.\\.(-?\\d+)".toRegex()

  override fun first(): Any? {
    val ops = data.trim().lines()
      .mapNotNull { line ->
        val parsed = pattern.find(line)?.groupValues.orEmpty()
        if (parsed.size == 8) {
          val op = parsed[1]
          val from = Vector3D(parsed[2].toInt(), parsed[4].toInt(), parsed[6].toInt())
          val to = Vector3D(parsed[3].toInt(), parsed[5].toInt(), parsed[7].toInt())
          op to (from to to)
        } else {
          null
        }
      }

    val basicCube = Vector3D(-50, -50, -50) to Vector3D(50, 50, 50)

    return solve(ops.filter { (_, cube) -> intersects(cube, basicCube) })
  }

  override fun second(): Any? {
    val ops = data.trim().lines()
      .mapNotNull { line ->
        val parsed = pattern.find(line)?.groupValues.orEmpty()
        if (parsed.size == 8) {
          val op = parsed[1]
          val from = Vector3D(parsed[2].toInt(), parsed[4].toInt(), parsed[6].toInt())
          val to = Vector3D(parsed[3].toInt(), parsed[5].toInt(), parsed[7].toInt())
          op to (from to to)
        } else {
          null
        }
      }

    return solve(ops)
  }

  private fun intersects(
    old: Pair<Vector3D, Vector3D>,
    new: Pair<Vector3D, Vector3D>,
  ): Boolean {
    val (oldFrom, oldTo) = old
    val (newFrom, newTo) = new

    return !(oldTo.x < newFrom.x || oldTo.y < newFrom.y || oldTo.z < newFrom.z
      || oldFrom.x > newTo.x || oldFrom.y > newTo.y || oldFrom.z > newTo.z)
  }

  private fun solve(ops: List<Pair<String, Pair<Vector3D, Vector3D>>>): Long {
    val finalCubes = ops.fold(emptyList<Pair<Vector3D, Vector3D>>()) { acc, (op, cube) ->
      acc.flatMap { oldCube ->
        if (intersects(oldCube, cube)) {
          split(oldCube, cube)
        } else {
          listOf(oldCube)
        }
      } + if (op == "on") listOf(cube) else emptyList()
    }

    return finalCubes.sumOf { cube ->
      (cube.second - cube.first).toList().map { it + 1L }.reduce { a, b -> a * b }
    }
  }

  private fun split(
    old: Pair<Vector3D, Vector3D>,
    new: Pair<Vector3D, Vector3D>,
  ): List<Pair<Vector3D, Vector3D>> {
    val (oldFrom, oldTo) = old
    val (newFrom, newTo) = new

    val xParts = buildParts(oldFrom.x, oldTo.x, newFrom.x, newTo.x)
    val yParts = buildParts(oldFrom.y, oldTo.y, newFrom.y, newTo.y)
    val zParts = buildParts(oldFrom.z, oldTo.z, newFrom.z, newTo.z)

    return xParts.flatMap { (fromX, toX, partX) ->
      yParts.flatMap { (fromY, toY, partY) ->
        zParts.mapNotNull { (fromZ, toZ, partZ) ->
          (Vector3D(fromX, fromY, fromZ) to Vector3D(toX, toY, toZ)).takeIf { !partX || !partY ||!partZ }
        }
      }
    }
  }

  private fun buildParts(oldFrom: Int, oldTo: Int, newFrom: Int, newTo: Int): List<Triple<Int, Int, Boolean>> {
    return buildList {
      var partial = newFrom <= oldFrom
      var mark = oldFrom
      if (newFrom in ((mark + 1)..oldTo)) {
        add(Triple(mark, newFrom - 1, partial))
        mark = newFrom
        partial = true
      }
      if (newTo in (mark..oldTo)) {
        add(Triple(mark, newTo, partial))
        mark = newTo + 1
        partial = false
      }
      if (mark <= oldTo) {
        add(Triple(mark, oldTo, partial))
      }
    }
  }
}

fun main() = SomeDay.mainify(Day22)
