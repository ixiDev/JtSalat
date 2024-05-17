interface Platform {
    val name: String
}

expect fun getPlatform(): Platform


//expect suspend fun reverseGeocode(lat: Double, lng: Double):LocationInfo?

