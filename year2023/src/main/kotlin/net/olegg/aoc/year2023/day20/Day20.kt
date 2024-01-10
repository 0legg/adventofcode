package net.olegg.aoc.year2023.day20

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2023.DayOf2023

/**
 * See [Year 2023, Day 20](https://adventofcode.com/2023/day/20)
 */
object Day20 : DayOf2023(20) {
  override fun first(): Any? {
    var lowCount = 0L
    var highCount = 0L

    val nodes = lines.associate { line ->
      val (nameRaw, outputsRaw) = line.split(" -> ")
      val outputs = outputsRaw.split(", ")

      when (nameRaw[0]) {
        '&' -> nameRaw.drop(1) to Node.Conjunction(
          output = outputs,
          inputs = mutableMapOf(),
        )
        '%' -> nameRaw.drop(1) to Node.FlipFlop(
          output = outputs,
        )
        else -> nameRaw to Node.Broadcaster(
          output = outputs,
        )
      }
    }

    nodes.forEach { (name, node) ->
      node.output
        .mapNotNull { nodes[it] as? Node.Conjunction }
        .forEach { it.inputs[name] = false }
    }

    repeat(1000) {
      val queue = ArrayDeque(listOf(Triple("button", "broadcaster", false)))

      while (queue.isNotEmpty()) {
        val (src, dst, signal) = queue.removeFirst()
        if (signal) {
          highCount++
        } else {
          lowCount++
        }

        queue += nodes.getOrDefault(dst, Node.Noop)
          .handle(src, signal)
          .map { (newDst, newSignal) -> Triple(dst, newDst, newSignal) }
      }
    }

    return lowCount * highCount
  }

  override fun second(): Any? {
    val nodes = lines.associate { line ->
      val (nameRaw, outputsRaw) = line.split(" -> ")
      val outputs = outputsRaw.split(", ")

      when (nameRaw[0]) {
        '&' -> nameRaw.drop(1) to Node.Conjunction(
          output = outputs,
          inputs = mutableMapOf(),
        )
        '%' -> nameRaw.drop(1) to Node.FlipFlop(
          output = outputs,
        )
        else -> nameRaw to Node.Broadcaster(
          output = outputs,
        )
      }
    }

    nodes.forEach { (name, node) ->
      node.output
        .mapNotNull { nodes[it] as? Node.Conjunction }
        .forEach { it.inputs[name] = false }
    }

    val rxInputs = nodes
      .filterValues { it is Node.Conjunction && "ns" in it.output }
      .mapValues { -1L }
      .toMutableMap()

    generateSequence(1) { it + 1 }.forEach { push ->
      val queue = ArrayDeque(listOf(Triple("button", "broadcaster", false)))

      while (queue.isNotEmpty()) {
        val (src, dst, signal) = queue.removeFirst()

        if (rxInputs[src] == -1L && signal) {
          rxInputs[src] = push.toLong()
          if (rxInputs.values.none { it == -1L }) {
            return rxInputs.values.reduce(Long::times)
          }
        }

        val node = nodes.getOrDefault(dst, Node.Noop)

        queue += node
          .handle(src, signal)
          .map { (newDst, newSignal) -> Triple(dst, newDst, newSignal) }
      }
    }

    return -1
  }

  sealed interface Node {
    val output: List<String>
    fun handle(
      source: String,
      signal: Boolean
    ): List<Pair<String, Boolean>>

    data class Broadcaster(
      override val output: List<String>
    ) : Node {
      override fun handle(
        source: String,
        signal: Boolean
      ): List<Pair<String, Boolean>> {
        return output.map { it to signal }
      }
    }

    data class FlipFlop(
      override val output: List<String>,
      var state: Boolean = false
    ) : Node {
      override fun handle(
        source: String,
        signal: Boolean
      ): List<Pair<String, Boolean>> {
        if (!signal) {
          state = !state
          return output.map { it to state }
        } else {
          return emptyList()
        }
      }
    }

    data class Conjunction(
      override val output: List<String>,
      val inputs: MutableMap<String, Boolean>,
    ) : Node {
      override fun handle(
        source: String,
        signal: Boolean
      ): List<Pair<String, Boolean>> {
        inputs[source] = signal
        val send = !inputs.values.all { it }
        return output.map { it to send }
      }
    }

    data object Noop : Node {
      override val output: List<String> = emptyList()

      override fun handle(
        source: String,
        signal: Boolean
      ): List<Pair<String, Boolean>> = emptyList()
    }
  }
}

fun main() = SomeDay.mainify(Day20)
