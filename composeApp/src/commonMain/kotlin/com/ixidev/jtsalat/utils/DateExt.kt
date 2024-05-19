package com.ixidev.jtsalat.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


fun LocalDateTime.now() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())


fun calculateTimeDifference(start: LocalTime, end: LocalTime): String {

    val difference = if (end > start) {
        end.toSecondOfDay() - start.toSecondOfDay()
    } else {
        start.toSecondOfDay() - end.toSecondOfDay()
    }
    val hours = difference / 3600
    val minutes = (difference % 3600) / 60
    val seconds = difference % 60

    return if (end > start) {
        "- 0${hours} :${minutes} :${seconds}"
    } else {
        "+ 0${hours} :${minutes} :${seconds}"
    }
}


fun getFormattedDate(
    iso8601Timestamp: String,
): String {
    val localDateTime = iso8601TimestampToLocalDateTime(iso8601Timestamp)
    val date = localDateTime.date
    val day = date.dayOfMonth
    val month = date.monthNumber
    val year = date.year

    // This format should be generated based on an argument.
    // For now, we're hardcoding this to the 'dd.MM.yyyy' format.
    return "${day.zeroPrefixed(2)}.${month.zeroPrefixed(2)}.${year}"
}

private fun iso8601TimestampToLocalDateTime(timestamp: String): LocalDateTime {
    return Instant.parse(timestamp).toLocalDateTime(TimeZone.currentSystemDefault())
}

private fun Int.zeroPrefixed(
    maxLength: Int,
): String {
    if (this < 0 || maxLength < 1) return ""

    val string = this.toString()
    val currentStringLength = string.length
    return if (maxLength <= currentStringLength) {
        string
    } else {
        val diff = maxLength - currentStringLength
        var prefixedZeros = ""
        repeat(diff) {
            prefixedZeros += "0"
        }
        "$prefixedZeros$string"
    }
}