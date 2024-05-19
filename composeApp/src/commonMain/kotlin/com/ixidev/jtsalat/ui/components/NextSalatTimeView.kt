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
import com.ixidev.jtsalat.data.models.SalatInfo
import com.ixidev.jtsalat.ui.getSalatName
import com.ixidev.jtsalat.utils.calculateTimeDifference
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun NextSalatTimeView(modifier: Modifier, salatInfo: SalatInfo, clockFlow: Flow<LocalDateTime>) {

    val salatName = remember { getSalatName(salatInfo.salatype) }
    val salatTime = remember {
        salatInfo.time.toLocalDateTime(TimeZone.currentSystemDefault())
    }
    val currentTime by clockFlow.collectAsState(null)


    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            text = stringResource(salatName),
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.background,
        )

        Text(
            text = "${salatTime.hour}:${salatTime.minute}",
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.primary
        )

        Spacer(modifier = Modifier.height(10.dp))
        // time remaining
        Text(
            text = calculateTimeDifference(currentTime?.time ?: salatTime.time, salatTime.time),
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.primary
        )

    }


}