package day22

import someday.SomeDay
import java.util.*

/**
 * Created by olegg on 22/12/15.
 */
class Day22: SomeDay(22) {
    val hp = 50
    val mana = 500
    val armor = 0
    val me = Triple(hp, armor, mana)
    val boss = with(data.lines().map { it.substringAfterLast(": ").toInt() }) { get(0) to get(1) }

    data class Spell(val cost: Int, val duration: Int,
                     val action: (Pair<Triple<Int, Int, Int>, Pair<Int, Int>>) -> Pair<Triple<Int, Int, Int>, Pair<Int, Int>>)

    val magicMissile = Spell(53, 1) { it.first to it.second.copy(first = it.second.first - 4) }
    val drain = Spell(73, 1) { it.first.copy(first = it.first.first + 2) to it.second.copy(first = it.second.first - 2) }
    val shield = Spell(113, 6) { it.first.copy(second = 7) to it.second }
    val poison = Spell(173, 6) { it.first to it.second.copy(first = it.second.first - 3)}
    val recharge = Spell(229, 5) { it.first.copy(third = it.first.third + 101) to it.second }

    val bossHit = Spell(0, 1) { it.first.copy(first = it.first.first - (it.second.second - it.first.second).coerceAtLeast(1) ) to it.second }
    val hardBossHit = Spell(0, 1) { it.first.copy(first = it.first.first - (it.second.second - it.first.second).coerceAtLeast(1) - 1 ) to it.second }

    val spells = listOf(magicMissile, drain, shield, poison, recharge)

    data class Game(val me: Triple<Int, Int, Int>, val boss: Pair<Int, Int>, val spells: Map<Spell, Int> = mapOf(), val mana: Int = 0, val myMove: Boolean = true)

    fun countMana(mySpells: List<Spell>, bossSpells: List<Spell>): Int {
        val queue = LinkedList(listOf(Game(me, boss)))
        var best = Int.MAX_VALUE
        while (queue.isNotEmpty()) {
            val game = queue.pop()
            if (game.mana >= best) {
                continue
            }
            val states = with(game.spells.keys.fold(game.me to game.boss) { acc, spell -> spell.action(acc) }) {
                copy(first = if (game.spells.get(shield) != 1) first else first.copy(second = 0))
            }
            if (states.first.first <= 0){
                continue
            }
            if (states.second.first <= 0) {
                best = best.coerceAtMost(game.mana)
                continue
            }
            val activeSpells = game.spells.mapValues { it.value - 1 }.filterValues { it > 0 }
            val cast = if (game.myMove) mySpells.filter { it.cost <= states.first.third }.filterNot { activeSpells.containsKey(it) }.filter { game.mana + it.cost < best } else bossSpells

            queue += cast.map { game.copy(
                    me = states.first.copy(third = states.first.third - it.cost),
                    boss = states.second,
                    spells = activeSpells + (it to it.duration),
                    mana = game.mana + it.cost,
                    myMove = !game.myMove
            ) }
        }
        return best
    }

    override fun first(): String {
        return countMana(spells, listOf(bossHit)).toString()
    }

    override fun second(): String {
        return countMana(spells, listOf(hardBossHit)).toString()
    }
}

fun main(args: Array<String>) {
    val day = Day22()
    println(day.first())
    println(day.second())
}