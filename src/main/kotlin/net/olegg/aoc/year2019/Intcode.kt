package net.olegg.aoc.year2019

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import net.olegg.aoc.year2019.Intcode.Arg.Address
import net.olegg.aoc.year2019.Intcode.Arg.Value
import java.nio.CharBuffer
import java.nio.LongBuffer

class Intcode(program: LongArray) {

  private var offset = 0L
  private var memory = LongBuffer.wrap(program)

  suspend fun eval(input: ReceiveChannel<Long> = Channel(), output: SendChannel<Long> = Channel()) {
    while (memory.hasRemaining()) {
      val rawOp = memory.get()
      parseOp(rawOp)?.let { (op, modes) ->
        op.eval(op, this, modes, input, output)
      } ?: when (rawOp) {
        99L -> {
          return
        }
        else -> throw UnsupportedOperationException("Opcode $rawOp is not supported")
      }
    }

    throw IndexOutOfBoundsException("Trying to reach position out of memory bounds")
  }

  fun get(): Long {
    resizeIfNecessary(memory.position().toLong())
    return memory.get()
  }

  operator fun get(index: Long): Long {
    resizeIfNecessary(index)
    return memory.get(index.toInt())
  }

  operator fun get(arg: Arg): Long {
    return when (arg) {
      is Address -> get(arg.index)
      is Value -> arg.value
    }
  }

  operator fun set(index: Long, value: Long) {
    resizeIfNecessary(index)
    memory.put(index.toInt(), value)
  }

  operator fun set(arg: Arg, value: Long) {
    when (arg) {
      is Address -> set(arg.index, value)
      is Value -> set(arg.value, value)
    }
  }

  fun position(index: Long) {
    resizeIfNecessary(index)
    memory.position(index.toInt())
  }

  private fun resizeIfNecessary(newIndex: Long) {
    val intdex = newIndex.toInt()
    if (intdex >= memory.capacity()) {
      val position = memory.position()
      memory.rewind()
      memory = LongBuffer.allocate(intdex + 16).put(memory)
      memory.position(position)
    }
  }

  data class Op(
      val length: Int,
      val eval: suspend Op.(
          program: Intcode,
          modes: CharBuffer,
          input: ReceiveChannel<Long>,
          output: SendChannel<Long>
      ) -> Unit
  ) {
    fun parseArgs(program: Intcode, modes: CharBuffer): List<Arg> = modes.indices
        .map {
          when (val mode = modes.get()) {
            '0' -> Address(program.get())
            '1' -> Value(program.get())
            '2' -> Address(program.offset + program.get())
            else -> throw IllegalArgumentException("Mode $mode is not supported")
          }
        }
  }

  private fun parseOp(rawOp: Long): Pair<Op, CharBuffer>? {
    val opcode = rawOp % 100
    val op = OPS[opcode.toInt()] ?: return null
    val rawModes = rawOp / 100
    val modes = CharBuffer.wrap("$rawModes".padStart(op.length - 1, '0').reversed().toCharArray())
    return op to modes
  }

  companion object {
    private val OPS = mapOf(
        1 to Op(4) { program, modes, _, _ ->
          val (arg1, arg2, arg3) = parseArgs(program, modes)
          program[arg3] = program[arg1] + program[arg2]
        },
        2 to Op(4) { program, modes, _, _ ->
          val (arg1, arg2, arg3) = parseArgs(program, modes)
          program[arg3] = program[arg1] * program[arg2]
        },
        3 to Op(2) { program, modes, input, _ ->
          val (arg1) = parseArgs(program, modes)
          program[arg1] = input.receive()
        },
        4 to Op(2) { program, modes, _, output ->
          val (arg1) = parseArgs(program, modes)
          output.send(program[arg1])
        },
        5 to Op(3) { program, modes, _, _ ->
          val (arg1, arg2) = parseArgs(program, modes)
          if (program[arg1] != 0L) {
            program.position(program[arg2])
          }
        },
        6 to Op(3) { program, modes, _, _ ->
          val (arg1, arg2) = parseArgs(program, modes)
          if (program[arg1] == 0L) {
            program.position(program[arg2])
          }
        },
        7 to Op(4) { program, modes, _, _ ->
          val (arg1, arg2, arg3) = parseArgs(program, modes)
          program[arg3] = if (program[arg1] < program[arg2]) 1L else 0L
        },
        8 to Op(4) { program, modes, _, _ ->
          val (arg1, arg2, arg3) = parseArgs(program, modes)
          program[arg3] = if (program[arg1] == program[arg2]) 1L else 0L
        },
        9 to Op(2) { program, modes, _, _ ->
          val (arg1) = parseArgs(program, modes)
          program.offset += program[arg1]
        }
    )
  }

  sealed class Arg {
    data class Address(val index: Long) : Arg()
    data class Value(val value: Long) : Arg()
  }

  private operator fun CharBuffer.set(index: Int, value: Char) = put(index, value)
}
