package net.olegg.adventofcode.year2016.day8

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2016.DayOf2016

/**
 * @see <a href="http://adventofcode.com/2016/day/8">Year 2016, Day 8</a>
 */
class Day8 : DayOf2016(8) {

    val match2 = "[^\\d]*([\\d]+)[^\\d]*(\\d+)[^\\d]*".toPattern()

    fun matrix(width: Int, height: Int) = Array(height) { Array(width) { false } }

    override fun first(data: String): Any? {
        return applyOperations(50, 6, data.lines())
                .sumBy { it.count { it } }
                .toString()
    }

    override fun second(data: String): Any? {
        return applyOperations(50, 6, data.lines())
                .joinToString(separator = "\n") { it.joinToString(separator = "") { if (it) "#" else "." } }
    }

    fun applyOperations(width: Int, height: Int, ops: List<String>): Array<Array<Boolean>> {
        return ops.fold(matrix(width, height)) { screen, op ->
            val data = match2.matcher(op).let {
                it.find()
                return@let (it.group(1).toInt() to it.group(2).toInt())
            }
            screen.apply {
                when {
                    op.startsWith("rect") -> {
                        (0 until data.second).forEach { y ->
                            (0 until data.first).forEach { x ->
                                screen[y][x] = true
                            }
                        }
                    }
                    op.startsWith("rotate row") -> {
                        val row = Array(width) { false }
                        (0 until width).forEach { row[(it + data.second) % width] = screen[data.first][it] }
                        (0 until width).forEach { screen[data.first][it] = row[it] }
                    }
                    op.startsWith("rotate column") -> {
                        val column = Array(height) { false }
                        (0 until height).forEach { column[(it + data.second) % height] = screen[it][data.first] }
                        (0 until height).forEach { screen[it][data.first] = column[it] }
                    }
                }
            }
        }
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day8::class)
