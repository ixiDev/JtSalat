package com.ixidev.jtsalat.sensor

import android.content.Context
import android.location.Geocoder
import com.ixidev.jtsalat.data.models.LocationInfo
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

actual class GeocodeReverser(context: Context) {
    private val geocoder = Geocoder(context, Locale.getDefault())
    actual suspend fun reverseGeocode(
        lat: Double,
        lng: Double
    ): LocationInfo = suspendCoroutine { continuation ->
        geocoder.getFromLocation(lat, lng, 1) {
            var cityName = "Unknown"
            var countryName = "Unknown"
            if (it.isNotEmpty()) {
                cityName = it[0].locality
                countryName = it[0].countryName
            }
            continuation.resume(
                LocationInfo(
                    latitude = lat,
                    longitude = lng,
                    city = "$cityName, $countryName",
                )
            )
        }
    }

}