package net.olegg.aoc.utils

import kotlin.math.abs
import kotlin.math.sign

data class Vector3D(
  var x: Int = 0,
  var y: Int = 0,
  var z: Int = 0,
) {
  operator fun plus(other: Vector3D) = Vector3D(x + other.x, y + other.y, z + other.z)
  operator fun plusAssign(other: Vector3D) = run {
    x += other.x
    y += other.y
    z += other.z
  }
  operator fun minus(other: Vector3D) = Vector3D(x - other.x, y - other.y, z - other.z)
  operator fun minusAssign(other: Vector3D) = run {
    x -= other.x
    y -= other.y
    z -= other.z
  }
  operator fun unaryMinus() = Vector3D(-x, -y, -z)
  operator fun times(other: Int) = Vector3D(x * other, y * other, z * other)
  operator fun timesAssign(other: Int) = run {
    x *= other
    y *= other
    z *= other
  }
  operator fun times(other: Vector3D) = x * other.x + y * other.y + z * other.z
  operator fun div(other: Int) = Vector3D(x / other, y / other, z / other)
  operator fun divAssign(other: Int) = run {
    x /= other
    y /= other
    z /= other
  }
  operator fun get(index: Int): Int = when (index) {
    0 -> x
    1 -> y
    2 -> z
    else -> throw IllegalArgumentException("Index: $index")
  }

  fun length2() = x * x + y * y + z * z
  fun manhattan() = abs(x) + abs(y) + abs(z)
  fun toList() = listOf(x, y, z)
  fun toTriple() = Triple(x, y, z)
  fun volume() = abs(x * y * z)
  fun dir() = when {
    x == 0 && y == 0 -> Vector3D(0, 0, z.sign)
    x == 0 && z == 0 -> Vector3D(0, y.sign, 0)
    y == 0 && z == 0 -> Vector3D(x.sign, 0, 0)
    else -> error("Not aligned with axis: $this")
  }
}
