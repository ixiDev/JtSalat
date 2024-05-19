package com.ixidev.jtsalat.ui.screens.home

import com.ixidev.jtsalat.data.models.LocationInfo
import com.ixidev.jtsalat.data.models.SalatInfo
import com.ixidev.jtsalat.data.models.SalatType
import kotlinx.datetime.Clock


data class HomeState(
    val currentLocation: LocationInfo,
    val nextSalat: SalatInfo
) {
    companion object {
        val EMPTY = HomeState(
            currentLocation = LocationInfo(0.0, 0.0, "Unknown"),
            nextSalat = SalatInfo(SalatType.FAJR, Clock.System.now())
        )
    }
}