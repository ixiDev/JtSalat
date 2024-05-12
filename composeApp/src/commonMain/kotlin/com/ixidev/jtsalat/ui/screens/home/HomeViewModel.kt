package com.ixidev.jtsalat.ui.screens.home

import androidx.lifecycle.ViewModel
import com.ixidev.jtsalat.data.LocationInfo

class HomeViewModel : ViewModel() {

    val currentLocation: LocationInfo = LocationInfo(
        lat = 30.0,
        lng = 30.0,
        city = "Casablanca, Morocco"
    )

    init {
        println("HomeViewModel init")
    }

}