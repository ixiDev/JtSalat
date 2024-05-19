package com.ixidev.jtsalat.sensor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import platform.CoreMotion.CMDeviceMotion
import platform.CoreMotion.CMDeviceMotionHandler
import platform.CoreMotion.CMMotionManager
import platform.Foundation.NSError
import platform.Foundation.NSOperationQueue
import kotlin.math.atan2

actual class CompassSensor {

    private var motionManager = CMMotionManager()

    // private var orientationSubject = PassthroughSubject<Float, AccessibilitySyncOptions.Never>()
    //private var orientation: Float = 0.0
    private val _orientationFlow = MutableStateFlow(0)
    actual val orientationFlow: Flow<Int>
        get() = _orientationFlow.asStateFlow()

    actual fun start() {
        if (motionManager.deviceMotionAvailable.not()) {
            println("Device motion not available")
            return
        }
        motionManager.deviceMotionUpdateInterval = 0.1
        motionManager.startDeviceMotionUpdatesToQueue(
            NSOperationQueue.mainQueue,
            object : OrientationListener {
                override fun onOrientation(orientation: Double) {
                    _orientationFlow.tryEmit(orientation.toInt())
                }
            }
        )
        // motionManager.startDeviceMotionUpdates()
    }

    actual fun stop() {
        motionManager.stopDeviceMotionUpdates()
    }

    private interface OrientationListener : CMDeviceMotionHandler {
        fun onOrientation(orientation: Double)
        override fun invoke(motion: CMDeviceMotion?, error: NSError?) {
            val attitude = motion?.attitude
            attitude?.let {
                val orientation = atan2(attitude.roll, attitude.pitch)
                onOrientation(orientation)
            }
        }

    }
}