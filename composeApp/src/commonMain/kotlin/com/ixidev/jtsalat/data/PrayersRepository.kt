package com.ixidev.jtsalat.data

import com.batoulapps.adhan2.Coordinates
import com.batoulapps.adhan2.Prayer
import com.batoulapps.adhan2.PrayerTimes
import com.batoulapps.adhan2.data.DateComponents
import com.ixidev.jtsalat.data.models.LocationInfo
import com.ixidev.jtsalat.data.models.SalatInfo
import com.ixidev.jtsalat.data.models.SalatType
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class PrayersRepository(
    private val appSettings: AppSettings
) {
    fun getNextPrayer(locationInfo: LocationInfo, time: Instant): SalatInfo {
        val localDateTime = time.toLocalDateTime(TimeZone.currentSystemDefault())
        val dateComponents = DateComponents(
            localDateTime.year,
            localDateTime.monthNumber,
            if (localDateTime.hour < 23)
                localDateTime.dayOfMonth
            else localDateTime.dayOfMonth + 1 // next day
        )
        val coordinates = Coordinates(locationInfo.latitude, locationInfo.longitude)
        val preyTimes = PrayerTimes(coordinates, dateComponents, appSettings.calculationParameters)
        return preyTimes.nextPrayer(time).toSalatInfo(preyTimes)
    }


    fun getPreysTimes(locationInfo: LocationInfo): List<SalatInfo> {
        val dateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
        val dateComponents = DateComponents(
            dateTime.year,
            dateTime.monthNumber,
            dateTime.dayOfMonth
        )
        val coordinates = Coordinates(locationInfo.latitude, locationInfo.longitude)
        val preyTimes = PrayerTimes(coordinates, dateComponents, appSettings.calculationParameters)

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