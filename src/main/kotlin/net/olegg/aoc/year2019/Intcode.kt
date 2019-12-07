package net.olegg.aoc.year2019

import java.nio.CharBuffer
import java.nio.IntBuffer
import java.util.ArrayDeque
import java.util.Deque

object Intcode {
  fun eval(program: IntArray, inputs: List<Int> = emptyList()): List<Int> {
    val memory = IntBuffer.wrap(program)
    val input = ArrayDeque(inputs)
    val output = ArrayDeque<Int>()
    while (memory.hasRemaining()) {
      val rawOp = memory.get()
      parseOp(rawOp)?.let { (op, modes) ->
        op.eval(op, memory, modes, input, output)
      } ?: when (rawOp) {
        99 -> return output.toList()
        else -> throw UnsupportedOperationException("Opcode $rawOp is not supported")
      }
    }

    throw IndexOutOfBoundsException("Trying to reach position out of memory bounds")
  }

  data class Op(
      val length: Int,
      val eval: Op.(memory: IntBuffer, modes: CharBuffer, input: Deque<Int>, output: Deque<Int>) -> Unit
  ) {
    fun parseArgs(memory: IntBuffer, modes: CharBuffer): List<Int> = modes.indices
        .map { when (val mode = modes.get()) {
          '0' -> memory[memory.get()]
          '1' -> memory.get()
          else -> throw IllegalArgumentException("Mode $mode is not supported")
        } }
  }

  fun parseOp(rawOp: Int): Pair<Op, CharBuffer>? {
    val opcode = rawOp % 100
    val op = OPS[opcode] ?: return null
    val rawModes = rawOp / 100
    val modes = CharBuffer.wrap("$rawModes".padStart(op.length - 1, '0').reversed().toCharArray())
    return op to modes
  }

  val OPS = mapOf(
      1 to Op(4) { memory, modes, _, _ ->
        modes[2] = '1'
        val (arg1, arg2, arg3) = parseArgs(memory, modes)
        memory[arg3] = arg1 + arg2
      },
      2 to Op(4) { memory, modes, _, _ ->
        modes[2] = '1'
        val (arg1, arg2, arg3) = parseArgs(memory, modes)
        memory[arg3] = arg1 * arg2
      },
      3 to Op(2) { memory, modes, input, _ ->
        modes[0] = '1'
        val (arg1) = parseArgs(memory, modes)
        memory[arg1] = input.pop()
      },
      4 to Op(2) { memory, modes, _, output ->
        val (arg1) = parseArgs(memory, modes)
        output.offer(arg1)
      },
      5 to Op(3) { memory, modes, _, _ ->
        val (arg1, arg2) = parseArgs(memory, modes)
        if (arg1 != 0) {
          memory.position(arg2)
        }
      },
      6 to Op(3) { memory, modes, _, _ ->
        val (arg1, arg2) = parseArgs(memory, modes)
        if (arg1 == 0) {
          memory.position(arg2)
        }
      },
      7 to Op(4) { memory, modes, _, _ ->
        modes[2] = '1'
        val (arg1, arg2, arg3) = parseArgs(memory, modes)
        memory[arg3] = if (arg1 < arg2) 1 else 0
      },
      8 to Op(4) { memory, modes, _, _ ->
        modes[2] = '1'
        val (arg1, arg2, arg3) = parseArgs(memory, modes)
        memory[arg3] = if (arg1 == arg2) 1 else 0
      }
  )

  private operator fun IntBuffer.set(index: Int, value: Int) = put(index, value)
  private operator fun CharBuffer.set(index: Int, value: Char) = put(index, value)
}
