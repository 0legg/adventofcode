package net.olegg.adventofcode.year2016.day21

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2016.DayOf2016

/**
 * @see <a href="http://adventofcode.com/2016/day/21">Year 2016, Day 21</a>
 */
class Day21 : DayOf2016(21) {

    val sp = "swap position (\\d+) with position (\\d+)".toRegex()
    val sl = "swap letter (\\w) with letter (\\w)".toRegex()
    val rl = "rotate left (\\d+) steps?".toRegex()
    val rr = "rotate right (\\d+) steps?".toRegex()
    val rb = "rotate based on position of letter (\\w)".toRegex()
    val rp = "reverse positions (\\d+) through (\\d+)".toRegex()
    val mp = "move position (\\d+) to position (\\d+)".toRegex()

    override fun first(data: String): String {
        val ops = data.lines().filter { it.isNotBlank() }
        return scramble("abcdefgh", ops)
    }

    fun scramble(password: String, operations: List<String>): String {
        return operations.fold(StringBuilder(password)) { acc, op ->
            return@fold acc.also {
                when {
                    op.matches(sp) -> {
                        sp.find(op)?.groupValues?.let { it[1].toInt() to it[2].toInt() }?.let { positions ->
                            val chars = acc[positions.first] to acc[positions.second]
                            acc[positions.first] = chars.second
                            acc[positions.second] = chars.first
                        }
                    }
                    op.matches(sl) -> {
                        sl.find(op)?.groupValues?.let { it[1][0] to it[2][0] }?.let { chars ->
                            val positions = acc.indexOf(chars.first) to acc.indexOf(chars.second)
                            acc[positions.first] = chars.second
                            acc[positions.second] = chars.first
                        }
                    }
                    op.matches(rl) -> {
                        rl.find(op)?.groupValues?.let { it[1].toInt() }?.let { shift ->
                            val sub = acc.substring(0, shift % acc.length)
                            acc.delete(0, shift)
                            acc.append(sub)
                        }
                    }
                    op.matches(rr) -> {
                        rr.find(op)?.groupValues?.let { it[1].toInt() }?.let { shift ->
                            val sub = acc.substring(0, acc.length - shift % acc.length)
                            acc.delete(0, acc.length - shift % acc.length)
                            acc.append(sub)
                        }
                    }
                    op.matches(rb) -> {
                        rb.find(op)?.groupValues?.let { it[1][0] }?.let { char ->
                            val shift = acc.indexOf(char).let { if (it >= 4) it + 2 else it + 1 } % acc.length
                            val sub = acc.substring(0, acc.length - shift % acc.length)
                            acc.delete(0, acc.length - shift % acc.length)
                            acc.append(sub)
                        }
                    }
                    op.matches(rp) -> {
                        rp.find(op)?.groupValues?.let { it[1].toInt() to it[2].toInt() }?.let { positions ->
                            val replacement = acc.substring(positions.first, positions.second + 1).reversed()
                            acc.replace(positions.first, positions.second + 1, replacement)
                        }
                    }
                    op.matches(mp) -> {
                        mp.find(op)?.groupValues?.let { it[1].toInt() to it[2].toInt() }?.let { positions ->
                            val char = acc[positions.first]
                            acc.deleteCharAt(positions.first)
                            acc.insert(positions.second, char)
                        }
                    }
                    else -> {
                        println("$op unsupported")
                    }
                }
            }
        }.toString()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day21::class)
