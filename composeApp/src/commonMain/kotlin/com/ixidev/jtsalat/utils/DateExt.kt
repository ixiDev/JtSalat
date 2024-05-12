package com.ixidev.jtsalat.utils

import kotlinx.datetime.Clock
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