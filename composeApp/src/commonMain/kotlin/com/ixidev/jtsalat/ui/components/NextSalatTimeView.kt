package com.ixidev.jtsalat.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ixidev.jtsalat.data.SalatInfo
import com.ixidev.jtsalat.data.SalatType
import com.ixidev.jtsalat.utils.calculateTimeDifference
import jtsalat.composeapp.generated.resources.Res
import jtsalat.composeapp.generated.resources.asr_salat_name
import jtsalat.composeapp.generated.resources.dhuhr_salat_name
import jtsalat.composeapp.generated.resources.fajr_salat_name
import jtsalat.composeapp.generated.resources.isha_salat_name
import jtsalat.composeapp.generated.resources.maghrib_salat_name
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun NextSalatTimeView(modifier: Modifier, salatInfo: SalatInfo, clockFlow: Flow<LocalDateTime>) {

    val salatName = remember {
        when (salatInfo.salatype) {
            SalatType.FAJR -> Res.string.fajr_salat_name
            SalatType.DHUHR -> Res.string.dhuhr_salat_name
            SalatType.ASR -> Res.string.asr_salat_name
            SalatType.MAGHRIB -> Res.string.maghrib_salat_name
            SalatType.ISHA -> Res.string.isha_salat_name
        }
    }
    val currentTime by clockFlow.collectAsState(null)


    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            text = stringResource(salatName),
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.background,
        )

        Text(
            text = "${salatInfo.time.hour}:${salatInfo.time.minute}",
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.primary
        )

        Spacer(modifier = Modifier.height(10.dp))
        // time remaining
        Text(
            text = calculateTimeDifference(currentTime?.time ?: salatInfo.time, salatInfo.time),
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.primary
        )

    }


}