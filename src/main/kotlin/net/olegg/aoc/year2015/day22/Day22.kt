package net.olegg.aoc.year2015.day22

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2015.DayOf2015
import kotlin.math.max
import kotlin.math.min

/**
 * See [Year 2015, Day 22](https://adventofcode.com/2015/day/22)
 */
object Day22 : DayOf2015(22) {
  private const val HP = 50
  private const val MANA = 500
  private const val ARMOR = 0

  private val me = Triple(HP, ARMOR, MANA)
  private val boss = data
      .trim()
      .lines()
      .map { it.substringAfterLast(": ").toInt() }
      .let { it[0] to it[1] }

  data class Spell(
      val cost: Int,
      val duration: Int,
      val action: (Pair<Triple<Int, Int, Int>, Pair<Int, Int>>) -> Pair<Triple<Int, Int, Int>, Pair<Int, Int>>
  )

  private val magicMissile = Spell(53, 1) { (me, boss) ->
    me to boss.copy(first = boss.first - 4)
  }
  private val drain = Spell(73, 1) { (me, boss) ->
    me.copy(first = me.first + 2) to boss.copy(first = boss.first - 2)
  }
  private val shield = Spell(113, 6) { (me, boss) ->
    me.copy(second = 7) to boss
  }
  private val poison = Spell(173, 6) { (me, boss) ->
    me to boss.copy(first = boss.first - 3)
  }
  private val recharge = Spell(229, 5) { (me, boss) ->
    me.copy(third = me.third + 101) to boss
  }

  private val bossHit = Spell(0, 1) { (me, boss) ->
    me.copy(first = me.first - max(boss.second - me.second, 1)) to boss
  }
  private val hardBossHit = Spell(0, 1) { (me, boss) ->
    me.copy(first = me.first - max(boss.second - me.second, 1) - 1) to boss
  }

  private val spells = listOf(magicMissile, drain, shield, poison, recharge)

  data class Game(
      val me: Triple<Int, Int, Int>,
      val boss: Pair<Int, Int>,
      val spells: Map<Spell, Int> = mapOf(),
      val mana: Int = 0,
      val myMove: Boolean = true
  )

  fun countMana(mySpells: List<Spell>, bossSpells: List<Spell>): Int {
    val queue = ArrayDeque(listOf(Game(me, boss)))
    var best = Int.MAX_VALUE
    while (queue.isNotEmpty()) {
      val game = queue.removeFirst()

      val states = game.spells.keys
          .fold(game.me to game.boss) { acc, spell -> spell.action(acc) }
          .let { state ->
            if (game.spells[shield] != 1) {
              state
            } else {
              state.copy(first = state.first.copy(second = 0))
            }
          }

      if (states.first.first <= 0) {
        continue
      }
      if (states.second.first <= 0) {
        best = min(best, game.mana)
        continue
      }
      val activeSpells = game.spells
          .mapValues { it.value - 1 }
          .filterValues { it > 0 }

      val spells = if (game.myMove) {
        mySpells
            .filter { it.cost <= states.first.third }
            .filterNot { it in activeSpells }
            .filter { game.mana + it.cost < best }
      } else {
        bossSpells
      }

      queue += spells.map { spell ->
        game.copy(
            me = states.first.copy(third = states.first.third - spell.cost),
            boss = states.second,
            spells = activeSpells + (spell to spell.duration),
            mana = game.mana + spell.cost,
            myMove = !game.myMove
        )
      }
    }
    return best
  }

  override fun first(data: String): Any? {
    return countMana(spells, listOf(bossHit))
  }

  override fun second(data: String): Any? {
    return countMana(spells, listOf(hardBossHit))
  }
}

fun main() = SomeDay.mainify(Day22)
