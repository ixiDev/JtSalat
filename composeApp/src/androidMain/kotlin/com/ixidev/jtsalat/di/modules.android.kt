package com.ixidev.jtsalat.di

import com.ixidev.jtsalat.sensor.CompassSensor
import com.ixidev.jtsalat.sensor.GeocodeReverser
import org.koin.android.logger.AndroidLogger
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single<Logger> {
        AndroidLogger(level = Level.DEBUG)
    }
    single { GeocodeReverser(get()) }
    single {
        CompassSensor(get())
    }
}