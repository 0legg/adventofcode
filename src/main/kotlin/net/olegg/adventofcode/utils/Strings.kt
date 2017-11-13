package net.olegg.adventofcode.utils

import java.math.BigInteger
import java.security.MessageDigest

/**
 * Created by olegg on 12/4/16.
 */

fun String.md5() = "%032x".format(BigInteger(1, MessageDigest.getInstance("MD5").digest(this.toByteArray(java.nio.charset.StandardCharsets.UTF_8))))