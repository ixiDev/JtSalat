package com.ixidev.jtsalat.data.models

import kotlinx.datetime.Instant


data class SalatInfo(
    val salatype: SalatType,
    val time: Instant
)

enum class SalatType {
    FAJR,
    SUNRISE,
    DHUHR,
    ASR,
    MAGHRIB,
    ISHA
}