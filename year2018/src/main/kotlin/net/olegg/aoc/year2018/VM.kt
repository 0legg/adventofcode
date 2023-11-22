package net.olegg.aoc.year2018

enum class Ops {
  ADDR {
    override fun apply(
      regs: LongArray,
      a: Int,
      b: Int
    ) = regs[a] + regs[b]
    override fun stringify(
      a: Int,
      b: Int,
      c: Int
    ) = "regs[$c] = regs[$a] + regs[$b]"
  },
  ADDI {
    override fun apply(
      regs: LongArray,
      a: Int,
      b: Int
    ) = regs[a] + b
    override fun stringify(
      a: Int,
      b: Int,
      c: Int
    ) = "regs[$c] = regs[$a] + $b"
  },
  MULR {
    override fun apply(
      regs: LongArray,
      a: Int,
      b: Int
    ) = regs[a] * regs[b]
    override fun stringify(
      a: Int,
      b: Int,
      c: Int
    ) = "regs[$c] = regs[$a] * regs[$b]"
  },
  MULI {
    override fun apply(
      regs: LongArray,
      a: Int,
      b: Int
    ) = regs[a] * b
    override fun stringify(
      a: Int,
      b: Int,
      c: Int
    ) = "regs[$c] = regs[$a] * $b"
  },
  BANR {
    override fun apply(
      regs: LongArray,
      a: Int,
      b: Int
    ) = regs[a] and regs[b]
    override fun stringify(
      a: Int,
      b: Int,
      c: Int
    ) = "regs[$c] = regs[$a] & regs[$b]"
  },
  BANI {
    override fun apply(
      regs: LongArray,
      a: Int,
      b: Int
    ) = regs[a] and b.toLong()
    override fun stringify(
      a: Int,
      b: Int,
      c: Int
    ) = "regs[$c] = regs[$a] & $b"
  },
  BORR {
    override fun apply(
      regs: LongArray,
      a: Int,
      b: Int
    ) = regs[a] or regs[b]
    override fun stringify(
      a: Int,
      b: Int,
      c: Int
    ) = "regs[$c] = regs[$a] | regs[$b]"
  },
  BORI {
    override fun apply(
      regs: LongArray,
      a: Int,
      b: Int
    ) = regs[a] or b.toLong()
    override fun stringify(
      a: Int,
      b: Int,
      c: Int
    ) = "regs[$c] = regs[$a] | $b"
  },
  SETR {
    override fun apply(
      regs: LongArray,
      a: Int,
      b: Int
    ) = regs[a]
    override fun stringify(
      a: Int,
      b: Int,
      c: Int
    ) = "regs[$c] = regs[$a]"
  },
  SETI {
    override fun apply(
      regs: LongArray,
      a: Int,
      b: Int
    ) = a.toLong()
    override fun stringify(
      a: Int,
      b: Int,
      c: Int
    ) = "regs[$c] = $a"
  },
  GTIR {
    override fun apply(
      regs: LongArray,
      a: Int,
      b: Int
    ) = if (a > regs[b]) 1L else 0L
    override fun stringify(
      a: Int,
      b: Int,
      c: Int
    ) = "regs[$c] = $a > regs[$b]"
  },
  GTRI {
    override fun apply(
      regs: LongArray,
      a: Int,
      b: Int
    ) = if (regs[a] > b) 1L else 0L
    override fun stringify(
      a: Int,
      b: Int,
      c: Int
    ) = "regs[$c] = regs[$a] > $b"
  },
  GTRR {
    override fun apply(
      regs: LongArray,
      a: Int,
      b: Int
    ) = if (regs[a] > regs[b]) 1L else 0L
    override fun stringify(
      a: Int,
      b: Int,
      c: Int
    ) = "regs[$c] = regs[$a] > regs[$b]"
  },
  EQIR {
    override fun apply(
      regs: LongArray,
      a: Int,
      b: Int
    ) = if (a.toLong() == regs[b]) 1L else 0L
    override fun stringify(
      a: Int,
      b: Int,
      c: Int
    ) = "regs[$c] = $a == regs[$b]"
  },
  EQRI {
    override fun apply(
      regs: LongArray,
      a: Int,
      b: Int
    ) = if (regs[a] == b.toLong()) 1L else 0L
    override fun stringify(
      a: Int,
      b: Int,
      c: Int
    ) = "regs[$c] = regs[$a] == $b"
  },
  EQRR {
    override fun apply(
      regs: LongArray,
      a: Int,
      b: Int
    ) = if (regs[a] == regs[b]) 1L else 0L
    override fun stringify(
      a: Int,
      b: Int,
      c: Int
    ) = "regs[$c] = regs[$a] == regs[$b]"
  };

  abstract fun apply(
    regs: LongArray,
    a: Int,
    b: Int
  ): Long

  abstract fun stringify(
    a: Int,
    b: Int,
    c: Int
  ): String
}

data class Command(val op: Ops, val a: Int, val b: Int, val c: Int) {
  fun apply(regs: LongArray) {
    regs[c] = op.apply(regs, a, b)
  }

  override fun toString(): String {
    return op.stringify(a, b, c)
  }
}
