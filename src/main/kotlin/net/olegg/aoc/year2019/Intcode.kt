package net.olegg.aoc.year2019

import java.util.ArrayDeque
import java.util.Deque

object Intcode {
  fun eval(program: IntArray, inputs: List<Int> = emptyList()): List<Int> {
    val input = ArrayDeque(inputs)
    val output = ArrayDeque<Int>()
    var position = 0
    while (position in program.indices) {
      val rawOp = program[position]
      parseOp(rawOp)?.let { (op, modes) ->
        op.eval(program, position, modes, input, output)
        position += op.length
      } ?: when (rawOp) {
        99 -> return output.toList()
        else -> throw UnsupportedOperationException("Opcode $rawOp is not supported")
      }
    }

    throw IndexOutOfBoundsException("Trying to reach position out of memory bounds")
  }

  data class Op(
      val length: Int,
      val eval: (program: IntArray, position: Int, modes: List<Int>, input: Deque<Int>, output: Deque<Int>) -> Unit
  )

  fun parseOp(rawOp: Int): Pair<Op, List<Int>>? {
    val opcode = rawOp % 100
    val op = OPS[opcode] ?: return null
    val rawModes = rawOp / 100
    val modes = mutableListOf<Int>()
    (1 until op.length).fold(rawModes) { curr, _ ->
      modes += curr % 10
      return@fold curr / 10
    }
    return op to modes
  }

  fun getValue(program: IntArray, value: Int, mode: Int) = when(mode) {
    0 -> program[program[value]]
    1 -> program[value]
    else -> throw UnsupportedOperationException("Mode $mode is not supported")
  }

  val OPS = mapOf(
      1 to Op(4) { program, position, modes, _, _ ->
        program[program[position + 3]] = getValue(program, position + 1, modes[0]) + getValue(program, position + 2, modes[1])
      },
      2 to Op(4) { program, position, modes, _, _ ->
        program[program[position + 3]] = getValue(program, position + 1, modes[0]) * getValue(program, position + 2, modes[1])
      },
      3 to Op(2) { program, position, _, input, _ ->
        program[program[position + 1]] = input.pop()
      },
      4 to Op(2) { program, position, modes, _, output ->
        output.offer(getValue(program, position + 1, modes[0]))
      }
  )
}
