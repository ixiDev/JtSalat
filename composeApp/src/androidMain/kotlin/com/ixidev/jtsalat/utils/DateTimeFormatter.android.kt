package com.ixidev.jtsalat.utils

import kotlinx.datetime.LocalTime
import kotlinx.datetime.toJavaLocalTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

actual object DateTimeFormatter {
    actual fun getFormattedDate(
        iso8601Timestamp: String,
        format: String,
    ): String {
        val date = getDateFromIso8601Timestamp(iso8601Timestamp)
        val formatter = DateTimeFormatter.ofPattern(format)
        return date.format(formatter)
    }

    private fun getDateFromIso8601Timestamp(string: String): ZonedDateTime {
        return ZonedDateTime.parse(string)
    }

    actual fun format(
        localTime: LocalTime,
        format: String
    ): String {
        val formatter = DateTimeFormatter.ofPattern(format)
        return formatter.format(localTime.toJavaLocalTime())
    }

}