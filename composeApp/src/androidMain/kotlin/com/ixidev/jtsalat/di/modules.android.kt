package com.ixidev.jtsalat.di

import com.ixidev.jtsalat.sensor.CompassSensor
import org.koin.core.module.Module
import org.koin.dsl.module

actual  fun platformModule(): Module = module {
    single {
        CompassSensor(get())
    }
}