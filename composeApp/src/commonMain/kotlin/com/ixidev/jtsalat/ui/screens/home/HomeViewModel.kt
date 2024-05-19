package com.ixidev.jtsalat.ui.screens.home

import androidx.lifecycle.ViewModel
import com.ixidev.jtsalat.data.AppSettings

class HomeViewModel(appSettings: AppSettings) : ViewModel() {

    val currentLocation = appSettings.getUserLocation()

    init {
        println("HomeViewModel init")
    }

}