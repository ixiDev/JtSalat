package com.ixidev.jtsalat.utils

import kotlinx.datetime.LocalTime

expect object DateTimeFormatter {

    fun getFormattedDate(
        iso8601Timestamp: String,
        format: String,
    ): String

    fun format(localTime: LocalTime, format: String): String
}