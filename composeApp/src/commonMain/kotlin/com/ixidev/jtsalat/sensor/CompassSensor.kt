package com.ixidev.jtsalat.sensor

import kotlinx.coroutines.flow.Flow


expect class CompassSensor{
    val orientationFlow: Flow<Int>
    fun start()
    fun stop()

}