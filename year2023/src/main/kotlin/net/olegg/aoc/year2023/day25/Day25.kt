package net.olegg.aoc.year2023.day25

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.UnionFind
import net.olegg.aoc.utils.toPair
import net.olegg.aoc.year2023.DayOf2023

/**
 * See [Year 2023, Day 25](https://adventofcode.com/2023/day/25)
 */
object Day25 : DayOf2023(25) {
  override fun first(): Any? {
    val edges = lines.flatMap { line ->
      line.substringAfter(": ").split(" ").map {
        listOf(line.substringBefore(": "), it).sorted().toPair()
      }
    }

    val nodeNames = buildSet {
      edges.forEach {
        add(it.first)
        add(it.second)
      }
    }

    val nodeIndex = nodeNames.withIndex().associate { it.value to it.index }

    var bestEdges = edges.size * edges.size
    var bestResult = -1

    while (bestEdges != 3) {
      val (currEdges, currResult) = minCut(edges, nodeIndex)

      if (currEdges < bestEdges) {
        bestEdges = currEdges
        bestResult = currResult
      }
    }

    return bestResult
  }

  private fun minCut(
    startEdges: List<Pair<String, String>>,
    startNodes: Map<String, Int>,
  ): Pair<Int, Int> {
    val adjList = mutableMapOf<Int, MutableList<Int>>()
    startEdges.forEach { (a, b) ->
      adjList.getOrPut(startNodes.getValue(a)) { mutableListOf() }
        .add(startNodes.getValue(b))
      adjList.getOrPut(startNodes.getValue(b)) { mutableListOf() }
        .add(startNodes.getValue(a))
    }
    val vertices = startNodes.values.sorted().toMutableList()

    val unionFind = UnionFind(vertices.size)

    while (vertices.size > 2) {
      val edges = buildEdges(adjList, vertices)
      val edge = edges.random()

      val (vertex1, vertex2) = edge
      unionFind.union(vertex1, vertex2)

      val valuesV1 = adjList[vertex1]!!
      val valuesV2 = adjList[vertex2]!!

      valuesV1.addAll(valuesV2)

      vertices.removeAt(vertices.indexOf(vertex2))
      adjList.remove(vertex2)

      vertices.forEach { vertex ->
        val values = adjList[vertex]!!
        values.forEachIndexed { index, value ->
          if (value == vertex2) {
            values[index] = vertex1
          }
        }
      }

      adjList[vertex1]!!.removeIf { it == vertex1 }
    }

    return adjList[vertices[0]]!!.size to startNodes.values
      .groupingBy { unionFind.root(it) }
      .eachCount()
      .values
      .reduce(Int::times)
  }

  private fun buildEdges(
    adjList: Map<Int, List<Int>>,
    vertices: List<Int>
  ): List<IntArray> {
    return vertices.flatMap { from ->
      val vector = adjList[from]!!
      vector.map { to ->
        intArrayOf(from, to)
      }
    }
  }
}

fun main() = SomeDay.mainify(Day25)
