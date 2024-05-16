package com.ixidev.jtsalat.sensor

import kotlinx.coroutines.flow.Flow

actual class CompassSensor {
    actual val orientationFlow: Flow<Int>
        get() = TODO("Not yet implemented")

    actual fun start() {
    }

    actual fun stop() {
    }
}