package com.ixidev.jtsalat.sensor

import com.ixidev.jtsalat.data.LocationInfo

actual class GeocodeReverser {
    actual suspend fun reverseGeocode(
        lat: Double,
        lng: Double
    ): LocationInfo {
        TODO("Not yet implemented")
    }
}