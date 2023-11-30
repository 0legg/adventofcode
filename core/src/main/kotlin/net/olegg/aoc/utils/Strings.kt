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
