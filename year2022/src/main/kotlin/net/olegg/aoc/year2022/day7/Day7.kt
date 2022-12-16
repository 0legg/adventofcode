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

  /*override val localData: String?
    get() = """
      $ cd /
      $ ls
      dir a
      14848514 b.txt
      8504156 c.dat
      dir d
      $ cd a
      $ ls
      dir e
      29116 f
      2557 g
      62596 h.lst
      $ cd e
      $ ls
      584 i
      $ cd ..
      $ cd ..
      $ cd d
      $ ls
      4060174 j
      8033020 d.log
      5626152 d.ext
      7214296 k
    """.trimIndent()*/

  override fun first(): Any? {
    return solve {
      this.map { it.size }
        .filter { it <= 100000L }
        .sum()
    }
  }

  override fun second(): Any? {
    return solve {
      val rootSize = maxOf { it.size }
      val unused = 70000000L - rootSize
      val toDelete = 30000000L - unused

      this.map { it.size }
        .filter { it >= toDelete }
        .min()
    }
  }

  private fun solve(action: List<Dir>.() -> Long): Long {
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

    return root.flattenDirs().action()
  }

  private fun Dir.flattenDirs(): List<Dir> {
    return listOf(this) + content.filterIsInstance<Dir>().flatMap { it.flattenDirs() }
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
