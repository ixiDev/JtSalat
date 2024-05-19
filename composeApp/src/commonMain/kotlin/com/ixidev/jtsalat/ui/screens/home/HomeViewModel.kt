package com.ixidev.jtsalat.ui.screens.home

import androidx.lifecycle.ViewModel
import com.batoulapps.adhan2.Coordinates
import com.batoulapps.adhan2.Prayer
import com.batoulapps.adhan2.PrayerTimes
import com.batoulapps.adhan2.data.DateComponents
import com.ixidev.jtsalat.data.AppSettings
import com.ixidev.jtsalat.data.models.SalatInfo
import com.ixidev.jtsalat.data.models.SalatType
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class HomeViewModel(
    appSettings: AppSettings,
) : ViewModel() {

    val homeStateFlow = appSettings.getUserLocation()
        .map { locationInfo ->
            val timeInstant = Clock.System.now()
            val localDateTime = timeInstant.toLocalDateTime(TimeZone.currentSystemDefault())
            val dateComponents = DateComponents(
                localDateTime.year,
                localDateTime.monthNumber,
                localDateTime.dayOfMonth
            )
            val coordinates = Coordinates(locationInfo.latitude, locationInfo.longitude)
            val preyTimes =
                PrayerTimes(coordinates, dateComponents, appSettings.calculationParameters)

            val nextSalat = preyTimes.nextPrayer(timeInstant).toSalatInfo(preyTimes)
            HomeState(
                currentLocation = locationInfo,
                nextSalat = nextSalat
            )
        }

}

private fun Prayer.toSalatInfo(preyTimes: PrayerTimes): SalatInfo {
    val salatType = when (this) {
        Prayer.FAJR -> SalatType.FAJR
        Prayer.SUNRISE -> SalatType.SUNRISE
        Prayer.DHUHR -> SalatType.DHUHR
        Prayer.ASR -> SalatType.ASR
        Prayer.MAGHRIB -> SalatType.MAGHRIB
        Prayer.ISHA -> SalatType.ISHA
        Prayer.NONE -> SalatType.FAJR
    }
    return SalatInfo(
        salatype = salatType,
        time = preyTimes.timeForPrayer(this) ?: Clock.System.now()
    )
}
