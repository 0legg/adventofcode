package net.olegg.aoc.utils

import kotlin.math.abs

data class Vector2D(
  var x: Int = 0,
  var y: Int = 0,
) {
  operator fun plus(other: Vector2D) = Vector2D(x + other.x, y + other.y)
  operator fun plusAssign(other: Vector2D) = run { x += other.x; y += other.y }
  operator fun minus(other: Vector2D) = Vector2D(x - other.x, y - other.y)
  operator fun minusAssign(other: Vector2D) = run { x -= other.x; y -= other.y }
  operator fun unaryMinus() = Vector2D(-x, -y)
  operator fun times(other: Int) = Vector2D(x * other, y * other)
  operator fun timesAssign(other: Int) = run { x *= other; y *= other }
  operator fun div(other: Int) = Vector2D(x / other, y / other)

  fun length2() = x * x + y * y
  fun manhattan() = abs(x) + abs(y)
  fun toList() = listOf(x, y)
}
