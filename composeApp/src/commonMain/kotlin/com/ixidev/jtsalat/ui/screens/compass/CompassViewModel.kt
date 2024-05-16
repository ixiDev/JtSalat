package com.ixidev.jtsalat.ui.screens.compass

import androidx.lifecycle.ViewModel
import com.ixidev.jtsalat.sensor.CompassSensor
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

class CompassViewModel(
    private val compassSensor: CompassSensor
) : ViewModel() {

    val orientationFLow  = compassSensor.orientationFlow


    init {
        compassSensor.start()
    }

    override fun onCleared() {
        compassSensor.stop()
    }
}