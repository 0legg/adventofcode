package day21

import someday.SomeDay

/**
 * Created by olegg on 21/12/15.
 */
class Day21: SomeDay(21) {
    val hp = 100
    val boss = data.lines().map { it.substringAfterLast(": ").toInt() }
    val weapons = listOf(
            Triple(8, 4, 0),
            Triple(10, 5, 0),
            Triple(25, 6, 0),
            Triple(40, 7, 0),
            Triple(74, 8, 0)
    )

    val armor = listOf(
            Triple(13, 0, 1),
            Triple(31, 0, 2),
            Triple(53, 0, 3),
            Triple(75, 0, 4),
            Triple(102, 0, 5),
            Triple(0, 0, 0)
    )

    val rings = listOf(
            Triple(25, 1, 0),
            Triple(50, 2, 0),
            Triple(100, 3, 0),
            Triple(20, 0, 1),
            Triple(40, 0, 2),
            Triple(80, 0, 3),
            Triple(0, 0, 0)
    )

    override fun first(): String {
        val ringSets = (rings.flatMap { first -> rings.map { Pair(first, it) } }.filter { it.first.first < it.second.first } + Pair(Triple(0, 0, 0), Triple(0, 0, 0)))
        val ringBuilds = ringSets.map { Triple(it.first.first + it.second.first, it.first.second + it.second.second, it.first.third + it.second.third) }
        val builds = weapons.flatMap { weapon -> armor.map { Triple(weapon.first + it.first, weapon.second + it.second, weapon.third + it.third) } }
                .flatMap { set -> ringBuilds.map { Triple(set.first + it.first, set.second + it.second, set.third + it.third) } }.sortedBy { it.first }
        return builds.first {
            val my = (it.second - boss[2]).coerceAtLeast(1)
            val his = (boss[1] - it.third).coerceAtLeast(1)
            (boss[0] + my - 1) / my <= (hp + his - 1) / his
        }.first.toString()
    }
}

fun main(args: Array<String>) {
    val day = Day21()
    println(day.first())
}