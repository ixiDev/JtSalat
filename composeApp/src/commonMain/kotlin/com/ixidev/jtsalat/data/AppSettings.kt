package com.ixidev.jtsalat.data

import com.batoulapps.adhan2.CalculationMethod
import com.batoulapps.adhan2.Madhab
import com.ixidev.jtsalat.data.models.LocationInfo
import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.json.Json

class AppSettings {
    private val settings = Settings().asObservableSettings()


    val calculationParameters = CalculationMethod.MUSLIM_WORLD_LEAGUE.parameters
        .copy(madhab = Madhab.SHAFI)

    fun getUserLocation(): StateFlow<LocationInfo> {
        val json = settings.getString("user_location", "")
        val default = decodeUserLocation(json)
        val flow = MutableStateFlow(default)
        settings.addStringListener(
            key = "user_location",
            "",
        ) {
            flow.tryEmit(decodeUserLocation(it))
        }
        return flow
    }

    private fun decodeUserLocation(json: String) = if (json.isNotEmpty()) {
        Json.decodeFromString(
            string = json,
            deserializer = LocationInfo.serializer()
        )
    } else {
        LocationInfo(0.0, 0.0, "Unknown")
    }

    fun updateUserLocation(location: LocationInfo) {
        settings.putString(
            key = "user_location",
            value = Json.Default.encodeToString(
                value = location,
                serializer = LocationInfo.serializer()
            )
        )
    }
}