package net.olegg.adventofcode.year2016.day12

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2016.DayOf2016

class Day12 : DayOf2016(12) {
    override fun first(data: String): String {
        val program = data.lines().filter { it.isNotBlank() }
        val registers = IntArray(4)
        var position = 0
        while (position in program.indices) {
            val parsed = program[position].split("\\s+".toRegex())
            position += when (parsed[0]) {
                "cpy" -> { registers[register(parsed[2])] = value(parsed[1], registers); 1 }
                "inc" -> { registers[register(parsed[1])] += 1; 1 }
                "dec" -> { registers[register(parsed[1])] -= 1; 1 }
                "jnz" -> { if (value(parsed[1], registers) == 0) 1 else value(parsed[2], registers) }
                else -> throw RuntimeException("unimplemented")
            }
        }
        return registers[0].toString()
    }

    fun value(index: String, registers: IntArray) = when (index[0]) {
        in 'a'..'d' -> registers[register(index)]
        else -> index.toInt()
    }

    fun register(register: String) = register[0] - 'a'
}

fun main(args: Array<String>) = SomeDay.mainify(Day12::class)
