package com.ixidev.jtsalat.sensor

import com.ixidev.jtsalat.data.LocationInfo
import platform.CoreLocation.CLGeocodeCompletionHandler
import platform.CoreLocation.CLGeocoder
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLPlacemark
import platform.Foundation.NSError
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

actual class GeocodeReverser {
    private val geocoder = CLGeocoder()
    actual suspend fun reverseGeocode(
        lat: Double,
        lng: Double
    ): LocationInfo = suspendCoroutine { continuation ->
        geocoder.reverseGeocodeLocation(
            CLLocation(lat, lng),
            completionHandler = object : CLGeocodeCompletionHandler {
                override fun invoke(placemarks: List<*>?, error: NSError?) {
                    val placemark = placemarks?.get(0) as CLPlacemark
                    if (error != null) {
                        println(error)
                        continuation.resume(
                            LocationInfo(
                                longitude = lat,
                                latitude = lng,
                                city = "Unknown"
                            )
                        )
                    } else {
                        println(placemark.locality)
                        continuation.resume(
                            LocationInfo(
                                longitude = lat,
                                latitude = lng,
                                city = "${placemark.locality}, ${placemark.country}"
                            )
                        )
                    }
                }

            }
        )
    }
}