package com.ixidev.jtsalat.ui.screens.home

import androidx.lifecycle.ViewModel
import com.ixidev.jtsalat.data.LocationInfo

class HomeViewModel : ViewModel() {

    val currentLocation: LocationInfo = LocationInfo(
        latitude = 30.0,
        longitude = 30.0,
        city = "Casablanca, Morocco"
    )

    init {
        println("HomeViewModel init")
    }

}