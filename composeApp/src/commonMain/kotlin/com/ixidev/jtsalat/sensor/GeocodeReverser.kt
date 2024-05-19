package com.ixidev.jtsalat.sensor

import com.ixidev.jtsalat.data.models.LocationInfo

expect class GeocodeReverser {
    suspend fun reverseGeocode(lat: Double, lng: Double): LocationInfo
}