package com.ixidev.jtsalat.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.roundToInt

actual class CompassSensor(context: Context) {
    private val sensorManager: SensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION)

    private val _orientationFlow = MutableStateFlow(0)
    actual val orientationFlow: Flow<Int>
        get() = _orientationFlow
            .asStateFlow()



    private val sensorEventListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

        override fun onSensorChanged(event: SensorEvent?) {
            event?.let {
                val orientation = event.values[0]
                _orientationFlow.value = orientation.roundToInt()
            }
        }
    }

    actual fun start() {
        sensorManager.registerListener(
            sensorEventListener,
            sensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    actual fun stop() {
        sensorManager.unregisterListener(sensorEventListener)
    }


}