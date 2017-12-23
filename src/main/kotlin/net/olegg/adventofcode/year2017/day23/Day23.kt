package net.olegg.adventofcode.year2017.day23

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2017.DayOf2017

/**
 * @see <a href="http://adventofcode.com/2017/day/23">Year 2017, Day 23</a>
 */
class Day23 : DayOf2017(23) {
    override fun first(data: String): String {
        val ops = data.trimIndent()
                .lines()
                .map { it.split("\\s+".toRegex()).toList() }

        var position = 0
        val regs = mutableMapOf<String, Long>()
        var muls = 0

        while (position in ops.indices) {
            val op = ops[position]
            when (op[0]) {
                "set" -> regs[op[1]] = extract(regs, op[2])
                "sub" -> regs[op[1]] = extract(regs, op[1]) - extract(regs, op[2])
                "mul" -> { regs[op[1]] = extract(regs, op[1]) * extract(regs, op[2]); muls += 1 }
                "jnz" -> if (extract(regs, op[1]) != 0L) position += (extract(regs, op[2]) - 1).toInt()
            }
            position += 1
        }

        return muls.toString()
    }

    private fun extract(map: Map<String, Long>, field: String): Long {
        return field.toLongOrNull() ?: map.getOrDefault(field, 0L)
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day23::class)
