package net.olegg.aoc.year2021.day16

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2021.DayOf2021
import java.math.BigInteger
import java.nio.Buffer
import java.nio.CharBuffer

/**
 * See [Year 2021, Day 16](https://adventofcode.com/2021/day/16)
 */
object Day16 : DayOf2021(16) {
  override fun first(): Any? {
    val binary = data
      .map { it.digitToInt(16) }
      .joinToString("") { it.toString(2).padStart(4, '0') }

    val buffer = CharBuffer.wrap(binary)
    val root = readPacket(buffer)

    fun Packet.sumVersions(): Int = version + when (body) {
      is Body.Literal -> 0
      is Body.Nested -> body.packets.sumOf { it.sumVersions() }
    }

    return root.sumVersions()
  }

  override fun second(): Any? {
    val binary = data
      .map { it.digitToInt(16) }
      .joinToString("") { it.toString(2).padStart(4, '0') }

    val buffer = CharBuffer.wrap(binary)
    val root = readPacket(buffer)

    return root.value()
  }

  private fun readPacket(buffer: CharBuffer): Packet {
    val version = buffer.consume(3).toInt(2)
    val type = buffer.consume(3).toInt(2)

    val body = when (type) {
      Body.Literal.TYPE -> {
        val value = buildString {
          do {
            val chunk = buffer.consume(5)
            append(chunk.drop(1))
          } while (chunk.first() != '0')
        }
        Body.Literal(
          number = value.toBigInteger(2),
        )
      }
      else -> {
        val lengthType = buffer.consume(1).toInt(2)
        val packets: List<Packet> = when (lengthType) {
          0 -> {
            val toRead = buffer.consume(15).toInt(2)
            val targetPosition = buffer.position() + toRead
            buildList {
              while (buffer.position() < targetPosition) {
                add(readPacket(buffer))
              }
            }
          }
          1 -> {
            val packets = buffer.consume(11).toInt(2)
            (0..<packets).map { readPacket(buffer) }
          }
          else -> emptyList()
        }
        Body.Nested(
          lengthType = lengthType,
          packets = packets,
          operation = Body.Nested.Operation.MAPPINGS.getValue(type),
        )
      }
    }

    return Packet(
      version = version,
      type = type,
      body = body,
    )
  }

  data class Packet(
    val version: Int,
    val type: Int,
    val body: Body,
  ) {
    fun value(): BigInteger = body.value()
  }

  sealed class Body {
    abstract fun value(): BigInteger

    data class Literal(
      val number: BigInteger,
    ) : Body() {
      override fun value(): BigInteger = number

      companion object {
        const val TYPE = 4
      }
    }

    data class Nested(
      val lengthType: Int,
      val packets: List<Packet>,
      val operation: Operation,
    ) : Body() {
      override fun value(): BigInteger = operation(packets)

      sealed interface Operation {
        operator fun invoke(packets: List<Packet>): BigInteger

        object Sum : Operation {
          override fun invoke(packets: List<Packet>): BigInteger {
            return packets.sumOf { it.value() }
          }
          const val TYPE = 0
        }

        object Product : Operation {
          override fun invoke(packets: List<Packet>): BigInteger {
            return packets.map { it.value() }.reduce(BigInteger::multiply)
          }

          const val TYPE = 1
        }

        object Minimum : Operation {
          override fun invoke(packets: List<Packet>): BigInteger {
            return packets.minOf { it.value() }
          }
          const val TYPE = 2
        }

        object Maximum : Operation {
          override fun invoke(packets: List<Packet>): BigInteger {
            return packets.maxOf { it.value() }
          }
          const val TYPE = 3
        }

        object GreaterThan : Operation {
          override fun invoke(packets: List<Packet>): BigInteger {
            return if (packets[0].value() > packets[1].value()) BigInteger.ONE else BigInteger.ZERO
          }
          const val TYPE = 5
        }

        object LessThan : Operation {
          override fun invoke(packets: List<Packet>): BigInteger {
            return if (packets[0].value() < packets[1].value()) BigInteger.ONE else BigInteger.ZERO
          }
          const val TYPE = 6
        }

        object Equal : Operation {
          override fun invoke(packets: List<Packet>): BigInteger {
            return if (packets[0].value() == packets[1].value()) BigInteger.ONE else BigInteger.ZERO
          }
          const val TYPE = 7
        }

        companion object {
          val MAPPINGS = mapOf(
            Sum.TYPE to Sum,
            Product.TYPE to Product,
            Minimum.TYPE to Minimum,
            Maximum.TYPE to Maximum,
            GreaterThan.TYPE to GreaterThan,
            LessThan.TYPE to LessThan,
            Equal.TYPE to Equal,
          )
        }
      }
    }
  }

  private fun Buffer.advance(by: Int) = position(position() + by)

  private fun CharBuffer.consume(size: Int): String {
    val returnValue = take(size).toString()
    advance(size)
    return returnValue
  }
}

fun main() = SomeDay.mainify(Day16)
