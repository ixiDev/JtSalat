package com.ixidev.jtsalat.di

import com.ixidev.jtsalat.ui.screens.compass.CompassViewModel
import com.ixidev.jtsalat.ui.screens.home.HomeViewModel
import org.koin.core.module.Module
import org.koin.dsl.module


expect fun platformModule(): Module

private val viewModels = module {

    factory {
        HomeViewModel()
    }
    factory {
        CompassViewModel(get())
    }

}

val koinModules = listOf(platformModule(), viewModels)