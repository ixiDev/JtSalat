package com.ixidev.jtsalat.di

import com.ixidev.jtsalat.ui.screens.compass.CompassViewModel
import com.ixidev.jtsalat.ui.screens.home.HomeViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module


expect fun platformModule(): Module

private val viewModels = module {

    factory {
        HomeViewModel()
    }
    factoryOf(::CompassViewModel)

}

val koinModules = listOf(platformModule(), viewModels)