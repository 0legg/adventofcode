package net.olegg.aoc.utils

import net.olegg.aoc.utils.Directions.D
import net.olegg.aoc.utils.Directions.DL
import net.olegg.aoc.utils.Directions.DR
import net.olegg.aoc.utils.Directions.L
import net.olegg.aoc.utils.Directions.R
import net.olegg.aoc.utils.Directions.U
import net.olegg.aoc.utils.Directions.UL
import net.olegg.aoc.utils.Directions.UR

//region 2D operations
data class Vector2D (
    var x: Int = 0,
    var y: Int = 0
) {
  operator fun plus(other: Vector2D) = Vector2D(x + other.x, y + other.y)
  operator fun plusAssign(other: Vector2D) = run { x += other.x; y += other.y }
  operator fun minus(other: Vector2D) = Vector2D(x - other.x, y - other.y)
  operator fun minusAssign(other: Vector2D) = run { x -= other.x; y -= other.y }
  operator fun unaryMinus() = Vector2D(-x, -y)
  operator fun times(other: Int) = Vector2D(x * other, y * other)
  operator fun timesAssign(other: Int) = run { x *= other; y *= other }

  fun length2() = x * x + y * y
}

enum class Directions(val step: Vector2D) {
  U(0, -1),
  D(0, 1),
  L(-1, 0),
  R(1, 0),
  UL(-1, -1),
  UR(1, -1),
  DL(-1, 1),
  DR(1, 1);

  constructor(x: Int, y: Int): this(Vector2D(x, y))
}

val Neighbors4 = listOf(U, D, L, R)
val Neighbors8 = listOf(UL, U, UR, L, R, DL, D, DR)
//endregion
