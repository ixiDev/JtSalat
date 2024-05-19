package com.ixidev.jtsalat.di

import com.ixidev.jtsalat.sensor.CompassSensor
import com.ixidev.jtsalat.sensor.GeocodeReverser
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.PrintLogger
import org.koin.dsl.module

actual fun platformModule() = module {

    single<Logger> {
        PrintLogger(level = Level.DEBUG)
    }

    single {
        CompassSensor()
    }
    single {
        GeocodeReverser()
    }
}