package net.olegg.adventofcode.year2017.day18

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2017.DayOf2017

/**
 * @see <a href="http://adventofcode.com/2017/day/18">Year 2017, Day 18</a>
 */
class Day18 : DayOf2017(18) {
    override fun first(data: String): String {
        var sound = 0L
        val ops = data.trimIndent()
                .lines()
                .map { it.split("\\s+".toRegex()).toList() }

        var position = 0
        val regs = mutableMapOf<String, Long>()

        while (position in ops.indices) {
            val op = ops[position]
            when (op[0]) {
                "snd" -> sound = extract(regs, op[1])
                "set" -> regs[op[1]] = extract(regs, op[2])
                "add" -> regs[op[1]] = extract(regs, op[1]) + extract(regs, op[2])
                "mul" -> regs[op[1]] = extract(regs, op[1]) * extract(regs, op[2])
                "mod" -> regs[op[1]] = (extract(regs, op[1]) % extract(regs, op[2]) + extract(regs, op[2])) % extract(regs, op[2])
                "rcv" -> if (extract(regs, op[1]) != 0L) return sound.toString()
                "jgz" -> if (extract(regs, op[1]) > 0L) position += (extract(regs, op[2]) - 1).toInt()
            }
            position += 1
        }

        return "-1"
    }

    private fun extract(map: Map<String, Long>, field: String): Long {
        return field.toLongOrNull() ?: map.getOrDefault(field, 0)
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day18::class)
