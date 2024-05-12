package com.ixidev.jtsalat.data

import kotlinx.datetime.LocalTime


data class SalatInfo(
    val salatype: SalatType,
    val time: LocalTime
)

enum class SalatType {
    FAJR,
    DHUHR,
    ASR,
    MAGHRIB,
    ISHA
}