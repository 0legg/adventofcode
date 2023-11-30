package net.olegg.aoc.utils

import kotlin.math.abs

data class Vector4D(
  var x: Int = 0,
  var y: Int = 0,
  var z: Int = 0,
  var w: Int = 0,
) {
  operator fun plus(other: Vector4D) = Vector4D(x + other.x, y + other.y, z + other.z, w + other.w)
  operator fun plusAssign(other: Vector4D) = run {
    x += other.x
    y += other.y
    z += other.z
    w += other.w
  }
  operator fun minus(other: Vector4D) = Vector4D(x - other.x, y - other.y, z - other.z, w - other.w)
  operator fun minusAssign(other: Vector4D) = run {
    x -= other.x
    y -= other.y
    z -= other.z
    w -= other.w
  }
  operator fun unaryMinus() = Vector4D(-x, -y, -z, -w)
  operator fun times(other: Int) = Vector4D(x * other, y * other, z * other, w * other)
  operator fun timesAssign(other: Int) = run {
    x *= other
    y *= other
    z *= other
    w *= other
  }
  operator fun times(other: Vector4D) = x * other.x + y * other.y + z * other.z + w * other.w
  operator fun div(other: Int) = Vector4D(x / other, y / other, z / other, w / other)
  operator fun divAssign(other: Int) = run {
    x /= other
    y /= other
    z /= other
    w /= other
  }
  fun length2() = x * x + y * y + z * z + w * w
  fun manhattan() = abs(x) + abs(y) + abs(z) + abs(w)
  fun toList() = listOf(x, y, w, z)
  fun volume() = abs(x * y * w * z)
}
