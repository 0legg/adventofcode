package net.olegg.aoc.year2019.day22

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2019.DayOf2019
import java.math.BigInteger

/**
 * See [Year 2019, Day 22](https://adventofcode.com/2019/day/22)
 */
object Day22 : DayOf2019(22) {
  override fun first(): Any? {
    val deckSize = BigInteger.valueOf(10007L)

    val input = lines
      .map {
        val split = it.split(" ")
        when (split[1]) {
          "into" -> Pair(BigInteger.valueOf(-1L), BigInteger.valueOf(-1L))
          "with" -> Pair(split.last().toBigInteger(), BigInteger.ZERO)
          else -> Pair(BigInteger.ONE, -split[1].toBigInteger())
        }
      }

    val compressed = input.reduce { acc, vector -> combine(acc, vector, deckSize) }

    return shuffle(BigInteger.valueOf(2019L), deckSize, BigInteger.ONE, compressed)
  }

  override fun second(): Any? {
    val deckSize = BigInteger.valueOf(119315717514047L)

    val input = lines
      .map {
        val split = it.split(" ")
        when (split[1]) {
          "into" -> Pair(BigInteger.valueOf(-1L), BigInteger.valueOf(-1L))
          "with" -> Pair(split.last().toBigInteger() inv deckSize, BigInteger.ZERO)
          else -> Pair(BigInteger.ONE, split[1].toBigInteger())
        }
      }
      .asReversed()

    val compressed = input.reduce { acc, vector -> combine(acc, vector, deckSize) }

    return shuffle(BigInteger.valueOf(2020L), deckSize, BigInteger.valueOf(101741582076661L), compressed)
  }

  private fun shuffle(
    position: BigInteger,
    deckSize: BigInteger,
    iterations: BigInteger,
    operation: Pair<BigInteger, BigInteger>
  ): BigInteger {
    return generateSequence(Triple(position, iterations, operation)) { (curr, iters, op) ->
      val newOp = combine(op, op, deckSize)
      val next = if (iters.testBit(0)) (curr * op.first + op.second) mod deckSize else curr
      return@generateSequence Triple(next, iters shr 1, newOp)
    }
      .first { it.second == BigInteger.ZERO }
      .first
  }

  private infix fun BigInteger.mod(modulo: BigInteger): BigInteger {
    return (this % modulo + modulo) % modulo
  }

  private infix fun BigInteger.inv(modulo: BigInteger): BigInteger {
    return generateSequence(Triple(BigInteger.ONE, modulo - BigInteger.valueOf(2), this)) { (curr, iters, power) ->
      val next = if (iters.testBit(0)) (curr * power) mod modulo else curr
      return@generateSequence Triple(next, iters shr 1, (power * power) mod modulo)
    }
      .first { it.second == BigInteger.ZERO }
      .first
  }

  private fun combine(
    first: Pair<BigInteger, BigInteger>,
    second: Pair<BigInteger, BigInteger>,
    modulo: BigInteger
  ) = Pair((first.first * second.first) mod modulo, (first.second * second.first + second.second) mod modulo)
}

fun main() = SomeDay.mainify(Day22)
