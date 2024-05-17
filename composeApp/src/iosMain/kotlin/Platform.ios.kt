import platform.UIKit.UIDevice


class IOSPlatform : Platform {
    override val name: String =
        UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

//actual suspend fun reverseGeocode(lat: Double, lng: Double): LocationInfo? = suspendCoroutine { continuation ->
//        CLGeocoder().reverseGeocodeLocation(
//            CLLocation(lat, lng),
//            completionHandler = object : CLGeocodeCompletionHandler {
//                override fun invoke(placemarks: List<*>?, error: NSError?) {
//                    val placemark = placemarks?.get(0) as CLPlacemark
//                    if (error != null) {
//                        println(error)
//                        continuation.resume(
//                            LocationInfo(
//                                lng = lat,
//                                lat = lng,
//                                city = "Unknown"
//                            )
//                        )
//                    } else {
//                        println(placemark.locality)
//                        continuation.resume(
//                            LocationInfo(
//                                lng = lat,
//                                lat = lng,
//                                city = "${placemark.locality}, ${placemark.country}"
//                            )
//                        )
//                    }
//                }
//
//            }
//        )
//    }


