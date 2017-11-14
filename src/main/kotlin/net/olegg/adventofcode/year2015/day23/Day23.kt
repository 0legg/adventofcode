package net.olegg.adventofcode.year2015.day23

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2015.DayOf2015

/**
 * @see <a href="http://adventofcode.com/2015/day/23">Year 2015, Day 23</a>
 */
class Day23 : DayOf2015(23) {
    val commands = data.lines()

    val hlfMatcher = "^hlf (\\w)$".toRegex()
    val tplMatcher = "^tpl (\\w)$".toRegex()
    val incMatcher = "^inc (\\w)$".toRegex()
    val jmpMatcher = "^jmp ([+-]?\\d+)$".toRegex()
    val jieMatcher = "^jie (\\w), ([+-]?\\d+)$".toRegex()
    val jioMatcher = "^jio (\\w), ([+-]?\\d+)$".toRegex()

    fun emulate(initialState: Triple<Int, Int, Int>): Triple<Int, Int, Int> {
        var state = initialState
        while (state.third in commands.indices) {
            with(commands[state.third]) {
                state = when {
                    matches(hlfMatcher) -> {
                        let {
                            when (hlfMatcher.find(it)?.groups?.get(1)?.value) {
                                "a" -> state.copy(first = state.first / 2, third = state.third + 1)
                                "b" -> state.copy(second = state.second / 2, third = state.third + 1)
                                else -> state
                            }
                        }
                    }
                    matches(tplMatcher) -> {
                        let {
                            when (tplMatcher.find(it)?.groups?.get(1)?.value) {
                                "a" -> state.copy(first = state.first * 3, third = state.third + 1)
                                "b" -> state.copy(second = state.second * 3, third = state.third + 1)
                                else -> state
                            }
                        }
                    }
                    matches(incMatcher) -> {
                        let {
                            when (incMatcher.find(it)?.groups?.get(1)?.value) {
                                "a" -> state.copy(first = state.first + 1, third = state.third + 1)
                                "b" -> state.copy(second = state.second + 1, third = state.third + 1)
                                else -> state
                            }
                        }
                    }
                    matches(jmpMatcher) -> {
                        let {
                            state.copy(third = state.third + (jmpMatcher.find(it)?.groups?.get(1)?.value?.toInt() ?: 0))
                        }
                    }

                    matches(jieMatcher) -> {
                        let {
                            val match = jieMatcher.find(it)?.groups
                            when (match?.get(1)?.value) {
                                "a" -> state.copy(third = state.third + if (state.first % 2 == 0) (match[2]?.value?.toInt() ?: 0) else 1)
                                "b" -> state.copy(third = state.third + if (state.second % 2 == 0) (match[2]?.value?.toInt() ?: 0) else 1)
                                else -> state
                            }
                        }
                    }

                    matches(jioMatcher) -> {
                        let {
                            val match = jioMatcher.find(it)?.groups
                            when (match?.get(1)?.value) {
                                "a" -> state.copy(third = state.third + if (state.first == 1) (match[2]?.value?.toInt() ?: 0) else 1)
                                "b" -> state.copy(third = state.third + if (state.second == 1) (match[2]?.value?.toInt() ?: 0) else 1)
                                else -> state
                            }
                        }
                    }

                    else -> state
                }
            }
        }
        return state
    }

    override fun first(data: String): String {
        return emulate(Triple(0, 0, 0)).second.toString()
    }

    override fun second(data: String): String {
        return emulate(Triple(1, 0, 0)).second.toString()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day23::class)
