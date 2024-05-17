package com.ixidev.jtsalat.ui.screens.compass

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ixidev.jtsalat.data.LocationInfo
import com.ixidev.jtsalat.qibla.Qibla
import com.ixidev.jtsalat.sensor.CompassSensor
import com.ixidev.jtsalat.sensor.GeocodeReverser
import com.ixidev.jtsalat.utils.isInQibla
import dev.icerock.moko.geo.LatLng
import dev.icerock.moko.geo.LocationTracker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CompassViewModel(
    private val locationTracker: LocationTracker,
    private val compassSensor: CompassSensor,
    private val geocodeReverser: GeocodeReverser
) : ViewModel() {


    private val locationStateFlow: Flow<LocationInfo> = locationTracker.getLocationsFlow()
        .onStart { emit(LatLng(0.0, 0.0)) }
        .map { geocodeReverser.reverseGeocode(it.latitude, it.longitude) }
        .distinctUntilChangedBy { it.city }

    val compassStateFlow: Flow<CompassState> = compassSensor.orientationFlow
        .combine(locationStateFlow) { orientation, location ->
            CompassState(
                degrees = orientation,
                isInQibla = isInQibla(
                    Qibla.findDirection(location.latitude, location.longitude).toInt(),
                    orientation
                ),
                location = location
            )
        }


    fun startLocationTracker() {
        compassSensor.start()
        viewModelScope.launch {
            locationTracker.startTracking()
        }
    }

    override fun onCleared() {
        compassSensor.stop()
        locationTracker.stopTracking()
    }
}