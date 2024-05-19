package com.ixidev.jtsalat.ui

import com.ixidev.jtsalat.data.models.SalatType
import jtsalat.composeapp.generated.resources.Res
import jtsalat.composeapp.generated.resources.asr_salat_name
import jtsalat.composeapp.generated.resources.dhuhr_salat_name
import jtsalat.composeapp.generated.resources.fajr_salat_name
import jtsalat.composeapp.generated.resources.isha_salat_name
import jtsalat.composeapp.generated.resources.maghrib_salat_name
import jtsalat.composeapp.generated.resources.sunrise_salat_name
import org.jetbrains.compose.resources.ExperimentalResourceApi


@OptIn(ExperimentalResourceApi::class)
fun getSalatName(type: SalatType) = when (type) {
    SalatType.FAJR -> Res.string.fajr_salat_name
    SalatType.SUNRISE -> Res.string.sunrise_salat_name
    SalatType.DHUHR -> Res.string.dhuhr_salat_name
    SalatType.ASR -> Res.string.asr_salat_name
    SalatType.MAGHRIB -> Res.string.maghrib_salat_name
    SalatType.ISHA -> Res.string.isha_salat_name
}
