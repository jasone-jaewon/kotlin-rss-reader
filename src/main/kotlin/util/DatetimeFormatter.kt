package util

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

private val GMT_FORMATTER = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z")
private val ZONE_FORMATTER = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z")

fun parse(input: String): ZonedDateTime {
    return try {
        ZonedDateTime.parse(input, GMT_FORMATTER)
    } catch (e: Exception) {
        ZonedDateTime.parse(input, ZONE_FORMATTER)
    }
}