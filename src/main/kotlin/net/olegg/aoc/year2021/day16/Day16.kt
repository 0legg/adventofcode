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
  override fun first(data: String): Any? {
    val binary = data.trim()
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
          number = value.toBigInteger(2)
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
            (0 until packets).map { readPacket(buffer) }
          }
          else -> emptyList()
        }
        Body.Nested(
          lengthType = lengthType,
          packets = packets,
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
  )

  sealed class Body {
    data class Literal(
      val number: BigInteger,
    ) : Body() {
      companion object {
        const val TYPE = 4
      }
    }

    data class Nested(
      val lengthType: Int,
      val packets: List<Packet>,
    ) : Body()
  }

  private fun Buffer.advance(by: Int) = position(position() + by)

  private fun CharBuffer.consume(size: Int): String {
    val returnValue = take(size).toString()
    advance(size)
    return returnValue
  }
}

fun main() = SomeDay.mainify(Day16)
