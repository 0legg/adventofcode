package net.olegg.adventofcode.year2017.day23

import java.math.BigInteger
import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2017.DayOf2017

/**
 * @see <a href="http://adventofcode.com/2017/day/23">Year 2017, Day 23</a>
 */
class Day23 : DayOf2017(23) {
    override fun first(data: String): Any? {
        val ops = data.trimIndent()
                .lines()
                .map { it.split("\\s+".toRegex()).toList() }

        var position = 0
        val regs = mutableMapOf<String, BigInteger>()
        var muls = 0

        while (position in ops.indices) {
            val op = ops[position]
            when (op[0]) {
                "set" -> regs[op[1]] = extract(regs, op[2])
                "sub" -> regs[op[1]] = extract(regs, op[1]) - extract(regs, op[2])
                "mul" -> { regs[op[1]] = extract(regs, op[1]) * extract(regs, op[2]); muls += 1 }
                "jnz" -> if (extract(regs, op[1]) != BigInteger.ZERO) position += (extract(regs, op[2]).toInt() - 1)
            }
            position += 1
        }

        return muls
    }

    override fun second(data: String): Any? {
        val ops = data.trimIndent()
                .lines()
                .map { it.split("\\s+".toRegex()).toList() }

        var position = 0
        val regs = mutableMapOf("a" to BigInteger.ONE)

        while (position in ops.indices) {
            val op = ops[position]
            when (op[0]) {
                "set" -> regs[op[1]] = extract(regs, op[2])
                "sub" -> regs[op[1]] = extract(regs, op[1]) - extract(regs, op[2])
                "mul" -> regs[op[1]] = extract(regs, op[1]) * extract(regs, op[2])
                "jnz" -> if (extract(regs, op[1]) != BigInteger.ZERO) position += (extract(regs, op[2]).toInt() - 1)
            }
            position += 1
        }

        return extract(regs, "h")
    }

    private fun extract(map: Map<String, BigInteger>, field: String): BigInteger {
        return field.toBigIntegerOrNull() ?: map.getOrDefault(field, BigInteger.ZERO)
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day23::class)
