package com.ixidev.jtsalat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ixidev.jtsalat.data.AppSettings
import com.ixidev.jtsalat.sensor.GeocodeReverser
import dev.icerock.moko.geo.LocationTracker
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.core.logger.Logger

class AppViewModel(
    private val locationTracker: LocationTracker,
    private val geocodeReverser: GeocodeReverser,
    private val appSettings: AppSettings,
    logger: Logger
) : ViewModel() {


    init {
        locationTracker.getLocationsFlow()
            .map {
                logger.info("Location: $it")
                geocodeReverser.reverseGeocode(it.latitude, it.longitude)
            }
            .distinctUntilChangedBy { it.city }
            .onEach {
                appSettings.updateUserLocation(it)
            }.launchIn(viewModelScope)

    }


    override fun onCleared() {
        super.onCleared()
        locationTracker.stopTracking()
    }

    fun startTracking() {
        viewModelScope.launch {
            locationTracker.startTracking()
        }
    }
}