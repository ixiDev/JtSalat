package com.ixidev.jtsalat.data.models

import kotlinx.serialization.Serializable


@Serializable
data class LocationInfo(
    val latitude: Double,
    val longitude: Double,
    val city: String,
)