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

  data class Stats(
    val hp: Int,
    val armor: Int,
    val power: Int
  )

  private val ME = Stats(
    hp = HP,
    armor = ARMOR,
    power = MANA,
  )

  private val BOSS = lines
    .map { it.substringAfterLast(": ").toInt() }
    .let { (hp, hits) -> Stats(hp, 0, hits) }

  data class Spell(
    val cost: Int,
    val duration: Int,
    val action: (Pair<Stats, Stats>) -> Pair<Stats, Stats>
  )

  private val MAGIC_MISSILE = Spell(53, 1) { (me, boss) ->
    me to boss.copy(hp = boss.hp - 4)
  }
  private val DRAIN = Spell(73, 1) { (me, boss) ->
    me.copy(hp = me.hp + 2) to boss.copy(hp = boss.hp - 2)
  }
  private val SHIELD = Spell(113, 6) { (me, boss) ->
    me.copy(armor = 7) to boss
  }
  private val POISON = Spell(173, 6) { (me, boss) ->
    me to boss.copy(hp = boss.hp - 3)
  }
  private val RECHARGE = Spell(229, 5) { (me, boss) ->
    me.copy(power = me.power + 101) to boss
  }

  private val BOSS_HIT = Spell(0, 1) { (me, boss) ->
    me.copy(hp = me.hp - max(boss.power - me.armor, 1)) to boss
  }
  private val HARD_BOSS_HIT = Spell(0, 1) { (me, boss) ->
    me.copy(hp = me.hp - max(boss.power - me.armor, 1) - 1) to boss
  }

  private val SPELLS = listOf(MAGIC_MISSILE, DRAIN, SHIELD, POISON, RECHARGE)

  data class Game(
    val me: Stats,
    val boss: Stats,
    val spells: Map<Spell, Int> = mapOf(),
    val mana: Int = 0,
    val myMove: Boolean = true
  )

  private fun countMana(
    mySpells: List<Spell>,
    bossSpells: List<Spell>
  ): Int {
    val queue = ArrayDeque(listOf(Game(ME, BOSS)))
    var best = Int.MAX_VALUE
    while (queue.isNotEmpty()) {
      val game = queue.removeFirst()

      val states = game.spells.keys
        .fold(game.me to game.boss) { acc, spell -> spell.action(acc) }
        .let { state ->
          if (game.spells[SHIELD] != 1) {
            state
          } else {
            state.copy(first = state.first.copy(armor = 0))
          }
        }

      if (states.first.hp <= 0) {
        continue
      }
      if (states.second.hp <= 0) {
        best = min(best, game.mana)
        continue
      }
      val activeSpells = game.spells
        .mapValues { it.value - 1 }
        .filterValues { it > 0 }

      val spells = if (game.myMove) {
        mySpells
          .filter { it.cost <= states.first.power }
          .filterNot { it in activeSpells }
          .filter { game.mana + it.cost < best }
      } else {
        bossSpells
      }

      queue += spells.map { spell ->
        game.copy(
          me = states.first.copy(power = states.first.power - spell.cost),
          boss = states.second,
          spells = activeSpells + (spell to spell.duration),
          mana = game.mana + spell.cost,
          myMove = !game.myMove,
        )
      }
    }
    return best
  }

  override fun first(): Any? {
    return countMana(SPELLS, listOf(BOSS_HIT))
  }

  override fun second(): Any? {
    return countMana(SPELLS, listOf(HARD_BOSS_HIT))
  }
}

fun main() = SomeDay.mainify(Day22)
