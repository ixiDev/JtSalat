package com.ixidev.jtsalat.di

import com.ixidev.jtsalat.data.AppSettings
import com.ixidev.jtsalat.data.PrayersRepository
import com.ixidev.jtsalat.ui.AppViewModel
import com.ixidev.jtsalat.ui.screens.calender.CalenderViewModel
import com.ixidev.jtsalat.ui.screens.compass.CompassViewModel
import com.ixidev.jtsalat.ui.screens.home.HomeViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


expect fun platformModule(): Module

private val dataModule = module {
    single { AppSettings() }
    singleOf(::PrayersRepository)
}
private val viewModels = module {
    factoryOf(::AppViewModel)
    factoryOf(::HomeViewModel)
    factoryOf(::CompassViewModel)
    factoryOf(::CalenderViewModel)
//    factoryOf(::SettingsViewModel)
}

val koinModules = listOf(platformModule(), dataModule, viewModels)