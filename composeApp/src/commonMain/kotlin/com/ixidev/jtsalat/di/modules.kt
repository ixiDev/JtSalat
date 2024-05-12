package com.ixidev.jtsalat.di

import com.ixidev.jtsalat.ui.screens.home.HomeViewModel
import org.koin.dsl.module


private val viewModels = module {

    factory {
        HomeViewModel()
    }

}

val koinModules = listOf(viewModels)