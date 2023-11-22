package net.olegg.aoc.utils

enum class Directions(val step: Vector2D) {
  U(0, -1),
  D(0, 1),
  L(-1, 0),
  R(1, 0),
  UL(-1, -1),
  UR(1, -1),
  DL(-1, 1),
  DR(1, 1),
  ;

  constructor(x: Int, y: Int) : this(Vector2D(x, y))

  companion object {
    val NEXT_4 = listOf(U, D, L, R)
    val NEXT_8 = listOf(UL, U, UR, L, R, DL, D, DR)
    val CCW = mapOf(U to L, L to D, D to R, R to U)
    val CW = mapOf(U to R, R to D, D to L, L to U)
    val REV = mapOf(U to D, D to U, L to R, R to L)
  }
}
