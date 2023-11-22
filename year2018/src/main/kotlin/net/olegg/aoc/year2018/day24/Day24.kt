package net.olegg.aoc.year2018.day24

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2018.DayOf2018

/**
 * See [Year 2018, Day 24](https://adventofcode.com/2018/day/24)
 */
object Day24 : DayOf2018(24) {
  override fun first(): Any? {
    val (immune, infection) = data
      .split("\n\n")
      .map { it.lines().drop(1) }
      .mapIndexed { system, group -> group.mapIndexedNotNull { index, line -> Group.from(line, index + 1, system) } }

    return solve(immune + infection).sumOf { it.units.toInt() }
  }

  override fun second(): Any? {
    val (immune, infection) = data
      .split("\n\n")
      .map { it.lines().drop(1) }
      .mapIndexed { system, group -> group.mapIndexedNotNull { index, line -> Group.from(line, index + 1, system) } }

    generateSequence(0) { it + 1 }.forEach { boost ->
      val boosted = immune.map { it.copy(attack = it.attack + boost) }

      val result = solve(boosted + infection)
      when {
        result.isEmpty() -> println("Boost $boost, stalemate")
        result.first().system == immune.first().system -> return result.sumOf { it.units }
        else -> println("Boost $boost, ${result.sumOf { it.units }} units remaining")
      }
    }

    return -1
  }

  private fun solve(initialBoard: List<Group>): List<Group> {
    val board = initialBoard.map { it.copy() }.toMutableList()
    while (board.distinctBy { it.system }.size == 2) {
      val attacks = mutableMapOf<Pair<Int, Int>, Group?>()

      board
        .sortedWith(
          compareByDescending<Group> { it.power() }
            .thenByDescending { it.initiative },
        )
        .forEach { group ->
          val targets = board
            .filter { it.system != group.system }
            .filterNot { it in attacks.values }

          targets
            .map { it to group.damage(it) }
            .filterNot { it.second == 0L }
            .sortedWith(
              compareByDescending<Pair<Group, Long>> { it.second }
                .thenByDescending { it.first.power() }
                .thenByDescending { it.first.initiative },
            )
            .firstOrNull()
            ?.let { attacks[group.system to group.index] = it.first }
        }

      val killed = board
        .sortedByDescending { it.initiative }
        .mapNotNull { group ->
          group.takeIf { it.units > 0 }
            ?.let {
              attacks[group.system to group.index]?.let { target ->
                (group.damage(target) / target.hit).also {
                  target.units -= it
                }
              }
            }
        }
        .sum()

      if (killed == 0L) {
        return emptyList()
      }
      board.removeIf { it.units <= 0 }
    }
    return board
  }

  data class Group(
    var units: Long,
    val hit: Long,
    val weak: Set<String>,
    val immune: Set<String>,
    val attack: Long,
    val type: String,
    val initiative: Int,
    val index: Int,
    val system: Int,
  ) {
    companion object {
      private val PATTERN = (
        "(-?\\d+) units each with (-?\\d+) hit points" +
          " ?\\(?([^)]*)\\)? with an attack that does (-?\\d+) (\\w+) damage at initiative (-?\\d+)"
        ).toRegex()

      fun from(
        string: String,
        index: Int,
        system: Int
      ): Group? {
        return PATTERN.matchEntire(string)?.let { match ->
          val (unitsRaw, hitRaw, specRaw, attackRaw, type, initiativeRaw) = match.destructured
          val weak = mutableSetOf<String>()
          val immune = mutableSetOf<String>()
          specRaw
            .takeIf { it.isNotBlank() }
            ?.split("; ")
            ?.forEach { spec ->
              val (kind, typesRaw) = spec.split(" to ")
              val types = typesRaw.split(", ")
              when (kind) {
                "weak" -> weak += types
                "immune" -> immune += types
              }
            }

          return@let Group(
            units = unitsRaw.toLong(),
            hit = hitRaw.toLong(),
            weak = weak,
            immune = immune,
            attack = attackRaw.toLong(),
            type = type,
            initiative = initiativeRaw.toInt(),
            index = index,
            system = system,
          )
        }
      }
    }

    fun damage(to: Group) = when (type) {
      in to.immune -> 0L
      in to.weak -> power() * 2L
      else -> power()
    }

    fun power() = units * attack
  }
}

fun main() = SomeDay.mainify(Day24)
