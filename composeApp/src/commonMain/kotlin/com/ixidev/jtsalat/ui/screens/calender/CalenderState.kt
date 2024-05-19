package com.ixidev.jtsalat.ui.screens.calender

import com.ixidev.jtsalat.data.models.LocationInfo
import com.ixidev.jtsalat.data.models.SalatInfo


data class CalenderState(
    val locationInfo: LocationInfo = LocationInfo(0.0, 0.0, "Unknown"),
    val prayTimes: List<SalatInfo> = emptyList(),
)