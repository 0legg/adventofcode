package net.olegg.aoc.year2022.day7

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.toPair
import net.olegg.aoc.year2022.DayOf2022
import net.olegg.aoc.year2022.day7.Day7.Node.Dir
import net.olegg.aoc.year2022.day7.Day7.Node.File

/**
 * See [Year 2022, Day 7](https://adventofcode.com/2022/day/7)
 */
object Day7 : DayOf2022(7) {

  override fun first(): Any? {
    val root = Dir(
      name = "/",
      parent = null,
      content = mutableListOf(),
    )
    var current = root
    val linesQueue = ArrayDeque(lines)
    while (linesQueue.isNotEmpty()) {
      val command = linesQueue.removeFirst()
      when {
        command == "$ cd /" -> {
          current = root
        }
        command == "$ cd .." -> {
          current = current.parent!!
        }
        command.startsWith("$ cd ") -> {
          val destination = command.replace("$ cd ", "")
          current = current.content.filterIsInstance<Dir>().first { it.name == destination }
        }
        command == "$ ls" -> {
          while (linesQueue.isNotEmpty() && !linesQueue.first().startsWith("$")) {
            val output = linesQueue.removeFirst().split(" ").toPair()
            current.content += when (output.first) {
              "dir" -> Dir(
                name = output.second,
                parent = current,
                content = mutableListOf(),
              )
              else -> File(
                name = output.second,
                parent = current,
                size = output.first.toLong(),
              )
            }
          }
        }
        else -> error("Unknown command $command")
      }
    }

    println("Total size ${root.size}")

    return root.sumSmallSizes()
  }

  private fun Dir.sumSmallSizes(): Long {
    return (if (size <= 100000L) size else 0) + content.filterIsInstance<Dir>().sumOf { it.sumSmallSizes() }
  }

  sealed interface Node {
    val name: String
    val size: Long
    val parent: Dir?

    data class Dir(
      override val name: String,
      override val parent: Dir?,
      val content: MutableList<Node>,
    ) : Node {
      override val size: Long by lazy { content.sumOf { it.size } }
    }

    data class File(
      override val name: String,
      override val parent: Dir?,
      override val size: Long,
    ) : Node
  }
}

fun main() = SomeDay.mainify(Day7)
