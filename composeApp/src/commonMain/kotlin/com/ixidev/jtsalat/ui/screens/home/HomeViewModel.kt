package com.ixidev.jtsalat.ui.screens.home

import androidx.lifecycle.ViewModel
import com.ixidev.jtsalat.data.AppSettings
import com.ixidev.jtsalat.data.PrayersRepository
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class HomeViewModel(
    appSettings: AppSettings,
    prayersRepository: PrayersRepository
) : ViewModel() {

    val homeStateFlow = appSettings.getUserLocation()
        .map { locationInfo ->
            HomeState(
                currentLocation = locationInfo,
                nextSalat = prayersRepository.getNextPrayer(locationInfo, Clock.System.now())
            )
        }

}


