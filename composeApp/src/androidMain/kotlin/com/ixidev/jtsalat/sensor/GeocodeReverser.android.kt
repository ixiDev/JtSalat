package com.ixidev.jtsalat.sensor

import android.content.Context
import android.location.Geocoder
import android.os.Build
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
        var cityName = "Unknown"
        var countryName = "Unknown"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            geocoder.getFromLocation(lat, lng, 1) {
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
        } else {
            try {
                val addresses = geocoder.getFromLocation(lat, lng, 1) ?: emptyList()
                if (addresses.isNotEmpty()) {
                    cityName = addresses[0].locality
                    countryName = addresses[0].countryName
                }
            } catch (e: Exception) {
                e.printStackTrace()
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