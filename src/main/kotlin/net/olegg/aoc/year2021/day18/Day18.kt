package net.olegg.aoc.year2021.day18

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2021.DayOf2021
import net.olegg.aoc.year2021.day18.Day18.Expression.Literal
import net.olegg.aoc.year2021.day18.Day18.Expression.Tuple

/**
 * See [Year 2021, Day 18](https://adventofcode.com/2021/day/18)
 */
object Day18 : DayOf2021(18) {
  override fun first(): Any? {
    return lines
      .map { line ->
        parseNumber(ArrayDeque(line.toList()))
      }
      .reduce { a, b -> a + b }
      .magnitude
  }

  override fun second(): Any? {
    val numbers = lines.map { line ->
      parseNumber(ArrayDeque(line.toList()))
    }

    return numbers.maxOf { a ->
      numbers.maxOf { b ->
        if (a === b) 0 else (a.deepcopy() + b.deepcopy()).magnitude
      }
    }
  }

  private fun parseNumber(
    tokens: ArrayDeque<Char>,
  ): Expression {
    return when (val token = tokens.removeFirst()) {
      in '0'..'9' -> Literal(token.digitToInt())
      '[' -> {
        val left = parseNumber(tokens)
        check(tokens.removeFirst() == ',') { "Unexpected token $token, expected \',\'" }
        val right = parseNumber(tokens)
        check(tokens.removeFirst() == ']') { "Unexpected token $token, expected \']\'" }
        Tuple(left, right)
      }
      else -> error("Unexpected token $token")
    }
  }

  sealed class Expression {
    abstract val magnitude: Int
    abstract fun deepcopy(): Expression
    var parent: Expression? = null

    data class Tuple(
      var left: Expression,
      var right: Expression,
    ) : Expression() {
      init {
        left.parent = this
        right.parent = this
      }

      override val magnitude: Int
        get() = 3 * left.magnitude + 2 * right.magnitude

      override fun deepcopy(): Expression = Tuple(
        left = left.deepcopy(),
        right = right.deepcopy(),
      )

      fun isSimple() = left is Literal && right is Literal
      override fun toString(): String = "[$left,$right]"
    }

    data class Literal(
      var value: Int,
    ) : Expression() {
      override val magnitude: Int
        get() = value

      override fun deepcopy() = Literal(value)
      override fun toString(): String = "$value"
    }

    operator fun plus(other: Expression): Expression = Tuple(
      left = this,
      right = other,
    ).reduced()

    fun reduced(): Expression {
      var curr = this
      do {
        val exploded = curr.explode()
        curr = exploded.first
        var changed = exploded.second
        if (!changed) {
          val split = curr.split()
          curr = split.first
          changed = split.second
        }
      } while (changed)
      return curr
    }

    private fun explode(depth: Int = 1): Pair<Expression, Boolean> {
      return when {
        this is Tuple && this.isSimple() && depth > 4 -> {
          var lcurr: Expression? = this
          while (lcurr != null) {
            val lparent = lcurr.parent as? Tuple
            if (lcurr === lparent?.left) {
              lcurr = lparent
            } else {
              val fromLeftTree = lparent?.left?.findRightmostLiteral()
              if (fromLeftTree != null) {
                fromLeftTree.value += (this.left as Literal).value
                break
              } else {
                lcurr = lparent
              }
            }
          }

          var rcurr: Expression? = this
          while (rcurr != null) {
            val rparent = rcurr.parent as? Tuple
            if (rcurr === rparent?.right) {
              rcurr = rparent
            } else {
              val fromRightTree = rparent?.right?.findLeftmostLiteral()
              if (fromRightTree != null) {
                fromRightTree.value += (this.right as Literal).value
                break
              } else {
                rcurr = rparent
              }
            }
          }

          Literal(0) to true
        }
        this is Tuple -> {
          val (newLeft, leftChanged) = left.explode(depth + 1)
          val (newRight, rightChanged) = if (leftChanged) (right to false) else right.explode(depth + 1)
          Tuple(
            left = newLeft,
            right = newRight,
          ) to (leftChanged || rightChanged)
        }
        else -> this to false
      }
    }

    private fun split(): Pair<Expression, Boolean> {
      return when {
        this is Literal && this.value > 9 -> {
          Tuple(
            left = Literal(this.value / 2),
            right = Literal(this.value - this.value / 2),
          ) to true
        }
        this is Tuple -> {
          val (newLeft, leftChanged) = left.split()
          val (newRight, rightChanged) = if (leftChanged) (right to false) else right.split()
          Tuple(
            left = newLeft,
            right = newRight,
          ) to (leftChanged || rightChanged)
        }
        else -> this to false
      }
    }

    private fun findLeftmostLiteral(): Literal? {
      return when (this) {
        is Literal -> this
        is Tuple -> left.findLeftmostLiteral() ?: right.findLeftmostLiteral()
      }
    }

    private fun findRightmostLiteral(): Literal? {
      return when (this) {
        is Literal -> this
        is Tuple -> right.findRightmostLiteral() ?: left.findRightmostLiteral()
      }
    }
  }
}

fun main() = SomeDay.mainify(Day18)
