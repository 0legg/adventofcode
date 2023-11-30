package net.olegg.aoc.utils

import kotlin.math.abs

fun gcd(
  a: Int,
  b: Int
): Int {
  val aa = abs(a)
  val ab = abs(b)
  var (ta, tb) = if (aa < ab) (aa to ab) else (ab to aa)
  while (ta != 0) {
    val na = tb % ta
    tb = ta
    ta = na
  }
  return tb
}

fun gcd(
  a: Long,
  b: Long
): Long {
  val aa = abs(a)
  val ab = abs(b)
  var (ta, tb) = if (aa < ab) (aa to ab) else (ab to aa)
  while (ta != 0L) {
    val na = tb % ta
    tb = ta
    ta = na
  }
  return tb
}

fun lcf(
  a: Int,
  b: Int
): Int {
  return a / gcd(a, b) * b
}

fun lcf(
  a: Long,
  b: Long
): Long {
  return a / gcd(a, b) * b
}
