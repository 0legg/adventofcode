package net.olegg.aoc.utils

import kotlin.math.abs

fun gcd(a: Int, b: Int): Int {
  var (ta, tb) = (minOf(abs(a), abs(b)) to maxOf(abs(a), abs(b)))
  while (ta != 0) {
    val na = tb % ta
    tb = ta
    ta = na
  }
  return tb
}

fun gcd(a: Long, b: Long): Long {
  var (ta, tb) = (minOf(abs(a), abs(b)) to maxOf(abs(a), abs(b)))
  while (ta != 0L) {
    val na = tb % ta
    tb = ta
    ta = na
  }
  return tb
}

fun lcf(a: Int, b: Int): Int {
  return a * b / gcd(a, b)
}

fun lcf(a: Long, b: Long): Long {
  return a * b / gcd(a, b)
}
