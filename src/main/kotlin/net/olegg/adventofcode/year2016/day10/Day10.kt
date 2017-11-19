package net.olegg.adventofcode.year2016.day10

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2016.DayOf2016

/**
 * @see <a href="http://adventofcode.com/2016/day/10">Year 2016, Day 10</a>
 */
class Day10 : DayOf2016(10) {
    val valuePattern = "value (\\d+) goes to bot (\\d+)".toPattern()
    val givePattern = "bot (\\d+) gives low to (\\w+) (\\d+) and high to (\\w+) (\\d+)".toPattern()

    override fun first(data: String): String {
        val bots = mutableMapOf<Int, MutableSet<Int>>()
        val actions = mutableMapOf<Int, Pair<Int, Int>>()
        val search = setOf(17, 61)

        val commands = data.lines().partition { valuePattern.toRegex().matches(it) }

        commands.first.forEach {
            with(valuePattern.matcher(it)) {
                if (find()) {
                    bots.putIfAbsent(group(2).toInt(), mutableSetOf())
                    bots.getOrDefault(group(2).toInt(), mutableSetOf()) += group(1).toInt()
                }
            }
        }

        commands.second.forEach {
            with(givePattern.matcher(it)) {
                if (find()) {
                    actions[group(1).toInt()] = Pair(
                            if (group(2) == "output") -group(3).toInt() - 1 else group(3).toInt(),
                            if (group(4) == "output") -group(5).toInt() - 1 else group(5).toInt())
                }
            }
        }

        while (!bots.containsValue(search)) {
            val active = bots.filter { it.value.size == 2 }
            active.forEach { bot ->
                actions[bot.key]?.let { action ->
                    bots.putIfAbsent(action.first, mutableSetOf())
                    bot.value.min()?.let { bots.getOrDefault(action.first, mutableSetOf()) += it }
                    bots.putIfAbsent(action.second, mutableSetOf())
                    bot.value.max()?.let { bots.getOrDefault(action.second, mutableSetOf()) += it }
                }
            }
            active.keys.filter { it >= 0 }.forEach {
                bots.remove(it)
                actions.remove(it)
            }
        }

        return bots.filter { it.value == search }.keys.joinToString()
    }

    override fun second(data: String): String {
        val bots = mutableMapOf<Int, MutableSet<Int>>()
        val actions = mutableMapOf<Int, Pair<Int, Int>>()
        val search = setOf(17, 61)

        val commands = data.lines().partition { valuePattern.toRegex().matches(it) }

        commands.first.forEach {
            with(valuePattern.matcher(it)) {
                if (find()) {
                    bots.putIfAbsent(group(2).toInt(), mutableSetOf())
                    bots.getOrDefault(group(2).toInt(), mutableSetOf()) += group(1).toInt()
                }
            }
        }

        commands.second.forEach {
            with(givePattern.matcher(it)) {
                if (find()) {
                    actions[group(1).toInt()] = Pair(
                            if (group(2) == "output") -group(3).toInt() - 1 else group(3).toInt(),
                            if (group(4) == "output") -group(5).toInt() - 1 else group(5).toInt())
                }
            }
        }

        while (bots.count { it.value.size == 2 } > 0 && actions.isNotEmpty()) {
            val active = bots.filter { it.value.size == 2 }
            active.forEach { bot ->
                actions[bot.key]?.let { action ->
                    bots.putIfAbsent(action.first, mutableSetOf())
                    bot.value.min()?.let { bots.getOrDefault(action.first, mutableSetOf()) += it }
                    bots.putIfAbsent(action.second, mutableSetOf())
                    bot.value.max()?.let { bots.getOrDefault(action.second, mutableSetOf()) += it }
                }
            }
            active.keys.filter { it >= 0 }.forEach {
                bots.remove(it)
                actions.remove(it)
            }
        }

        return ((bots[-1]?.sum() ?: 0) * (bots[-2]?.sum() ?: 0) * (bots[-3]?.sum() ?: 0)).toString()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day10::class)
