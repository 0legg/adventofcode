package net.olegg.aoc.utils

import java.math.BigInteger
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

/**
 * Extension functions and utility methods for strings.
 */

fun String.md5() =
    "%032x".format(BigInteger(1, MessageDigest.getInstance("MD5").digest(this.toByteArray(StandardCharsets.UTF_8))))
