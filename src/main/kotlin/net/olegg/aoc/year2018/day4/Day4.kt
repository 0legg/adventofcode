package net.olegg.aoc.year2018.day4

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2018.DayOf2018
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

/**
 * See [Year 2018, Day 4](https://adventofcode.com/2018/day/4)
 */
object Day4 : DayOf2018(4) {
  override fun first(): Any? {
    val events = lines
      .mapNotNull { Event.fromString(it) }
      .sortedBy { it.time }
      .scan(Event(-1, LocalDateTime.MIN, Event.Type.AWAKE)) { prev, event ->
        if (event.id == -1) event.copy(id = prev.id) else event
      }
      .drop(1)

    val sleeps = events
      .zipWithNext { prev, curr ->
        if (prev.type == Event.Type.SLEEP) Triple(prev.id, prev.time, curr.time) else null
      }
      .filterNotNull()
      .groupBy { it.first }

    val sleeper = sleeps.maxBy { entry ->
      entry.value.sumOf { it.second.until(it.third, ChronoUnit.MINUTES).toInt() }
    }

    val minutes = IntArray(60)
    sleeper.value.forEach { (_, prev, curr) ->
      (prev.minute until curr.minute).forEach { minutes[it] += 1 }
    }

    return sleeper.key * (minutes.withIndex().maxBy { it.value }.index)
  }

  override fun second(): Any? {
    val events = lines
      .mapNotNull { Event.fromString(it) }
      .sortedBy { it.time }
      .scan(Event(-1, LocalDateTime.MIN, Event.Type.AWAKE)) { prev, event ->
        if (event.id == -1) event.copy(id = prev.id) else event
      }
      .drop(1)

    val sleeps = events
      .zipWithNext { prev, curr ->
        if (prev.type == Event.Type.SLEEP) Triple(prev.id, prev.time, curr.time) else null
      }
      .filterNotNull()
      .groupBy { it.first }

    val freqs = sleeps.mapValues { entry ->
      val minutes = IntArray(60)
      entry.value.forEach { (_, prev, curr) ->
        (prev.minute until curr.minute).forEach { minutes[it] += 1 }
      }
      return@mapValues minutes
    }

    return freqs
      .maxBy { it.value.max() }
      .let { sleeper ->
        sleeper.key * (sleeper.value.withIndex().maxBy { it.value }.index)
      }
  }

  data class Event(val id: Int, val time: LocalDateTime, val type: Type) {
    companion object {
      private val SHIFT_PATTERN = "\\[(.*)\\] Guard #(\\d+) begins shift".toPattern()
      private val SLEEP_PATTERN = "\\[(.*)\\] falls asleep".toPattern()
      private val AWAKE_PATTERN = "\\[(.*)\\] wakes up".toPattern()
      private val FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

      fun fromString(data: String): Event? {
        val shift = SHIFT_PATTERN.matcher(data)
        val sleep = SLEEP_PATTERN.matcher(data)
        val awake = AWAKE_PATTERN.matcher(data)
        return when {
          shift.matches() -> {
            val time = shift.toMatchResult().group(1)
            val id = shift.toMatchResult().group(2).toInt()
            Event(id, parseTime(time), Type.AWAKE)
          }
          sleep.matches() -> {
            val time = sleep.toMatchResult().group(1)
            Event(-1, parseTime(time), Type.SLEEP)
          }
          awake.matches() -> {
            val time = awake.toMatchResult().group(1)
            Event(-1, parseTime(time), Type.AWAKE)
          }
          else -> null
        }
      }

      private fun parseTime(token: String): LocalDateTime {
        val time = LocalDateTime.parse(token, FORMATTER)
        return if (time.hour == 23) time.plusDays(1).truncatedTo(ChronoUnit.DAYS) else time
      }
    }

    enum class Type {
      AWAKE,
      SLEEP
    }
  }
}

fun main() = SomeDay.mainify(Day4)
