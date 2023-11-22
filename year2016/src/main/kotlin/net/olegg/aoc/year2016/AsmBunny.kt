@file:Suppress("NOTHING_TO_INLINE")

package net.olegg.aoc.year2016

object AsmBunny {
  fun eval(
    program: List<String>,
    registers: IntArray
  ): Sequence<Int> {
    return sequence {
      val code = program.map { it.split("\\s+".toRegex()).toMutableList() }.toMutableList()
      var position = 0
      while (position in code.indices) {
        val parsed = code[position]

        position += if (validate(parsed)) {
          when (parsed[0]) {
            "cpy" -> {
              registers[register(parsed[2])] = value(parsed[1], registers)
              1
            }
            "inc" -> {
              registers[register(parsed[1])] += 1
              1
            }
            "dec" -> {
              registers[register(parsed[1])] -= 1
              1
            }
            "jnz" -> {
              if (value(parsed[1], registers) == 0) 1 else value(parsed[2], registers)
            }
            "tgl" -> {
              val shift = value(parsed[1], registers)
              if (position + shift in code.indices) {
                val toggle = code[position + shift]
                toggle[0] = when (toggle.size) {
                  2 -> if (toggle[0] == "inc") "dec" else "inc"
                  3 -> if (toggle[0] == "jnz") "cpy" else "jnz"
                  else -> toggle[0]
                }
              }
              1
            }
            "out" -> {
              yield(value(parsed[1], registers))
              1
            }
            else -> {
              1
            }
          }
        } else {
          1
        }
      }
      yieldAll(registers.toList())
    }
  }

  inline fun value(
    index: String,
    registers: IntArray
  ) = when (index[0]) {
    in 'a'..'d' -> registers[register(index)]
    else -> index.toInt()
  }

  inline fun register(register: String) = "abcd".indexOf(register)

  inline fun validate(parsed: List<String>) = when (parsed[0]) {
    "cpy" -> parsed.size == 3 && register(parsed[2]) != -1
    "inc" -> parsed.size == 2 && register(parsed[1]) != -1
    "dec" -> parsed.size == 2 && register(parsed[1]) != -1
    "jnz" -> parsed.size == 3
    "tgl" -> parsed.size == 2
    "out" -> parsed.size == 2
    else -> false
  }
}
