package day16

import someday.SomeDay

/**
 * Created by olegg on 12/20/15.
 */
class Day16: SomeDay(16) {
    val sues = data.lines().map {
        it.substring("Sue ".length, it.indexOf(':')).toInt() to it.substring(it.indexOf(':') + 1).trim().split(", ").map { val split = it.split(": "); split[0] to split[1].toInt() }.toMap()
    }

    val footprint = mapOf(
            "children" to 3,
            "cats" to 7,
            "samoyeds" to 2,
            "pomeranians" to 3,
            "akitas" to 0,
            "vizslas" to 0,
            "goldfish" to 5,
            "trees" to 3,
            "cars" to 2,
            "perfumes" to 1
    )

    override fun first(): String {
        return sues.filter { it.second.all { it.value == footprint[it.key] } }.map { it.first }.first().toString()
    }

    override fun second(): String {
        return sues.filter { it.second.all { when (it.key) {
            "cats", "trees" -> (it.value > footprint[it.key] ?: 0)
            "pomeranians", "goldfish"  -> (it.value < footprint[it.key] ?: 0)
            else -> it.value == footprint[it.key]
        } } }.map { it.first }.first().toString()
    }
}

fun main(args: Array<String>) {
    val day = Day16()
    println(day.first())
    println(day.second())
}