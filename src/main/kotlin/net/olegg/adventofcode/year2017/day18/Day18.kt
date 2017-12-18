package net.olegg.adventofcode.year2017.day18

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2017.DayOf2017
import java.util.ArrayDeque
import java.util.Queue

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

    override fun second(data: String): String {
        val regs = Array(2) { mutableMapOf("p" to it.toLong()) }
        val stacks = Array(2) { ArrayDeque<Long>() }
        val position = Array(2) { 0 }
        var active = 0
        val send = Array(2) { 0L }
        val locked = Array(2) { false }
        val stopped = Array(2) { false }
        val ops = data.trimIndent()
                .lines()
                .map { it.split("\\s+".toRegex()).toList() }

        while (!locked[active] && !stopped[active]) {
            if (position[active] !in ops.indices) {
                stopped[active] = true
                active = 1 - active
                continue
            }

            val op = ops[position[active]]
            position[active] += when (op[0]) {
                "snd" -> {
                    stacks[1 - active].add(extract(regs[active], op[1]))
                    locked[1 - active] = false
                    send[active] = send[active] + 1
                    1
                }
                "set" -> { regs[active][op[1]] = extract(regs[active], op[2]); 1 }
                "add" -> { regs[active][op[1]] = extract(regs[active], op[1]) + extract(regs[active], op[2]); 1 }
                "mul" -> { regs[active][op[1]] = extract(regs[active], op[1]) * extract(regs[active], op[2]); 1 }
                "mod" -> { regs[active][op[1]] = (extract(regs[active], op[1]) % extract(regs[active], op[2]) + extract(regs[active], op[2])) % extract(regs[active], op[2]); 1 }
                "rcv" -> if (stacks[active].isNotEmpty()) {
                    regs[active][op[1]] = stacks[active].pollFirst()
                    1
                } else {
                    locked[active] = true
                    active = 1 - active
                    0
                }
                "jgz" -> { if (extract(regs[active], op[1]) > 0L) extract(regs[active], op[2]).toInt() else 1 }
                else -> 1
            }
        }

        return send[1].toString()
    }

    private fun extract(map: Map<String, Long>, field: String): Long {
        return field.toLongOrNull() ?: map.getOrDefault(field, 0)
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day18::class)
