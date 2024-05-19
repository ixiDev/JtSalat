package com.ixidev.jtsalat.ui.screens.calender

import androidx.lifecycle.ViewModel
import com.batoulapps.adhan2.CalculationMethod
import com.batoulapps.adhan2.Coordinates
import com.batoulapps.adhan2.Madhab
import com.batoulapps.adhan2.PrayerTimes
import com.batoulapps.adhan2.data.DateComponents
import com.ixidev.jtsalat.data.AppSettings
import com.ixidev.jtsalat.data.models.LocationInfo
import com.ixidev.jtsalat.data.models.SalatInfo
import com.ixidev.jtsalat.data.models.SalatType
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class CalenderViewModel(
    appSettings: AppSettings
) : ViewModel() {


    private val params = CalculationMethod.MUSLIM_WORLD_LEAGUE.parameters
        .copy(
            madhab = Madhab.SHAFI,
        )

    val calenderStateFlow = appSettings.getUserLocation()
        .map { locationInfo ->
            CalenderState(locationInfo, getPreysTimes(locationInfo))
        }

    private fun getPreysTimes(locationInfo: LocationInfo): List<SalatInfo> {
        val dateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
        val dateComponents = DateComponents(
            dateTime.year,
            dateTime.monthNumber,
            dateTime.dayOfMonth
        )
        val preyTimes = try {
            val coordinates = Coordinates(locationInfo.latitude, locationInfo.longitude)
            PrayerTimes(coordinates, dateComponents, params)
        } catch (e: Exception) {
            return emptyList()
        }

        return listOf(
            SalatInfo(
                salatype = SalatType.FAJR,
                time = preyTimes.fajr
            ),
            SalatInfo(
                salatype = SalatType.SUNRISE,
                time = preyTimes.sunrise
            ),
            SalatInfo(
                salatype = SalatType.DHUHR,
                time = preyTimes.dhuhr
            ),
            SalatInfo(
                salatype = SalatType.ASR,
                time = preyTimes.asr
            ),
            SalatInfo(
                salatype = SalatType.MAGHRIB,
                time = preyTimes.maghrib
            ),
            SalatInfo(
                salatype = SalatType.ISHA,
                time = preyTimes.isha
            ),
        )
    }


}