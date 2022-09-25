package net.olegg.aoc.year2015.day21

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Vector3D
import net.olegg.aoc.utils.pairs
import net.olegg.aoc.year2015.DayOf2015

/**
 * See [Year 2015, Day 21](https://adventofcode.com/2015/day/21)
 */
object Day21 : DayOf2015(21) {
  private const val hp = 100
  private val boss = lines.map { it.substringAfterLast(": ").toInt() }
  private val weapons = listOf(
    Vector3D(8, 4, 0),
    Vector3D(10, 5, 0),
    Vector3D(25, 6, 0),
    Vector3D(40, 7, 0),
    Vector3D(74, 8, 0)
  )

  private val armor = listOf(
    Vector3D(13, 0, 1),
    Vector3D(31, 0, 2),
    Vector3D(53, 0, 3),
    Vector3D(75, 0, 4),
    Vector3D(102, 0, 5),
    Vector3D(0, 0, 0)
  )

  private val rings = listOf(
    Vector3D(25, 1, 0),
    Vector3D(50, 2, 0),
    Vector3D(100, 3, 0),
    Vector3D(20, 0, 1),
    Vector3D(40, 0, 2),
    Vector3D(80, 0, 3),
    Vector3D(0, 0, 0)
  )

  private val ringBuilds = rings
    .pairs()
    .map { (left, right) -> left + right } +
    Vector3D(0, 0, 0)
  private val builds = weapons
    .flatMap { weapon -> armor.map { armor -> weapon + armor } }
    .flatMap { set -> ringBuilds.map { ringBuild -> set + ringBuild } }
    .sortedBy { it.x }

  override fun first(): Any? {
    return builds.first { (_, damage, armor) ->
      val my = (damage - boss[2]).coerceAtLeast(1)
      val his = (boss[1] - armor).coerceAtLeast(1)
      (boss[0] + my - 1) / my <= (hp + his - 1) / his
    }.x
  }

  override fun second(): Any? {
    return builds.reversed().first { (_, damage, armor) ->
      val my = (damage - boss[2]).coerceAtLeast(1)
      val his = (boss[1] - armor).coerceAtLeast(1)
      (boss[0] + my - 1) / my > (hp + his - 1) / his
    }.x
  }
}

fun main() = SomeDay.mainify(Day21)
