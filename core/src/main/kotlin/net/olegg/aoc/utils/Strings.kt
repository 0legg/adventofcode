package net.olegg.aoc.utils

import java.math.BigInteger
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

/**
 * Extension functions and utility methods for strings.
 */

fun String.md5(): String =
  "%032x".format(BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray(StandardCharsets.UTF_8))))

fun String?.parseInts(
  vararg delimiters: String,
  radix: Int = 10
): List<Int> = this?.split(*delimiters)?.mapNotNull { it.toIntOrNull(radix) }.orEmpty()

fun String?.parseLongs(
  vararg delimiters: String,
  radix: Int = 10
): List<Long> = this?.split(*delimiters)?.mapNotNull { it.toLongOrNull(radix) }.orEmpty()

/**
 * Finds all occurrences of [this] in [input] starting from [startIndex], including overlapping ones.
 * So, while `"aa".toRegex().findAll("aaa").count()` returns 1,
 * `"aa".toRegex().findAllOverlapping("aaa").count()` will return 2, both for first two and last two characters.
 */
fun Regex.findAllOverlapping(
  input: CharSequence,
  startIndex: Int = 0,
): Sequence<MatchResult> {
  require(startIndex in input.indices)
  return generateSequence({ find(input, startIndex) }) { previous ->
    find(input, previous.range.first + 1)
  }
}
