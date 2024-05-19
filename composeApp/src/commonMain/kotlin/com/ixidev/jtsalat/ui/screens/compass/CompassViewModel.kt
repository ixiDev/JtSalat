package com.ixidev.jtsalat.ui.screens.compass

import androidx.lifecycle.ViewModel
import com.ixidev.jtsalat.data.AppSettings
import com.ixidev.jtsalat.qibla.Qibla
import com.ixidev.jtsalat.sensor.CompassSensor
import com.ixidev.jtsalat.utils.isInQibla
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class CompassViewModel(
    private val compassSensor: CompassSensor,
    settings: AppSettings
) : ViewModel() {


    val compassStateFlow: Flow<CompassState> = compassSensor.orientationFlow
        .combine(settings.getUserLocation()) { orientation, location ->
            val qiblaDegree = Qibla.findDirection(location.latitude, location.longitude).toInt()
            CompassState(
                degrees = orientation,
                qiblaDegrees = qiblaDegree,
                isInQibla = isInQibla(qiblaDegree, orientation),
                location = location
            )
        }


    init {
        compassSensor.start()
    }

    override fun onCleared() {
        compassSensor.stop()
    }
}