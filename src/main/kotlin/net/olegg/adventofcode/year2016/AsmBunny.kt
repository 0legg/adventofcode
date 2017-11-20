package net.olegg.adventofcode.year2016

object AsmBunny {
    fun eval(program: List<String>, registers: IntArray): IntArray {
        val code = program.toMutableList()
        val output = registers.clone()
        var position = 0
        while (position in code.indices) {
            val parsed = code[position].split("\\s+".toRegex())

            position += if (validate(parsed)) when (parsed[0]) {
                "cpy" -> { output[register(parsed[2])] = value(parsed[1], output); 1 }
                "inc" -> { output[register(parsed[1])] += 1; 1 }
                "dec" -> { output[register(parsed[1])] -= 1; 1 }
                "jnz" -> { if (value(parsed[1], output) == 0) 1 else value(parsed[2], output) }
                "tgl" -> {
                    val shift = value(parsed[1], output)
                    if (position + shift in code.indices) {
                        val toggle = code[position + shift].split("\\s+".toRegex()).toMutableList()
                        toggle[0] = when (toggle.size) {
                            2 -> if (toggle[0] == "inc") "dec" else "inc"
                            3 -> if (toggle[0] == "jnz") "cpy" else "jnz"
                            else -> toggle[0]
                        }
                        code[position + shift] = toggle.joinToString(separator = " ")
                    }
                    1
                }
                else -> throw RuntimeException("unimplemented")
            } else {
                1
            }
        }
        return output
    }

    fun value(index: String, registers: IntArray) = when (index[0]) {
        in 'a'..'d' -> registers[register(index)]
        else -> index.toInt()
    }

    fun register(register: String) = "abcd".indexOf(register)

    fun validate(parsed: List<String>) = when (parsed[0]) {
        "cpy" -> parsed.size == 3 && register(parsed[2]) != -1
        "inc" -> parsed.size == 2 && register(parsed[1]) != -1
        "dec" -> parsed.size == 2 && register(parsed[1]) != -1
        "jnz" -> parsed.size == 3
        "tgl" -> parsed.size == 2
        else -> false
    }
}