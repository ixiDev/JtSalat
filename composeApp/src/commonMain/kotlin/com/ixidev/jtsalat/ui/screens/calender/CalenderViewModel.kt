package com.ixidev.jtsalat.ui.screens.calender

import androidx.lifecycle.ViewModel
import com.ixidev.jtsalat.data.AppSettings
import com.ixidev.jtsalat.data.PrayersRepository
import kotlinx.coroutines.flow.map

class CalenderViewModel(
    appSettings: AppSettings,
    private val prayersRepository: PrayersRepository
) : ViewModel() {


    val calenderStateFlow = appSettings.getUserLocation()
        .map { locationInfo ->
            CalenderState(
                locationInfo = locationInfo,
                prayTimes = prayersRepository.getPreysTimes(locationInfo)
            )
        }


}