package com.ixidev.jtsalat.ui.screens.compass

import com.ixidev.jtsalat.data.models.LocationInfo


data class CompassState(
    val degrees: Int = 0,
    val qiblaDegrees: Int = 93,
    val isInQibla: Boolean = false,
    val location: LocationInfo = LocationInfo(
        latitude = 33.58,
        longitude = -7.60,
        city = "Unknown, Unknown"
    )
)