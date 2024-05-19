package com.ixidev.jtsalat.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ixidev.jtsalat.data.models.LocationInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

@Composable
fun LocationInfoView(
    modifier: Modifier = Modifier,
    location: LocationInfo,
    clockFlow: Flow<LocalDateTime>
) {
    val clock by clockFlow.collectAsState(null)
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {

        LocationNameRow(
            modifier = Modifier.fillMaxWidth(),
            locationInfo = location
        )

        Spacer(Modifier.padding(2.dp))

        Text(
            text = "${clock?.dayOfWeek},  ${clock?.month}, ${clock?.year}",
            color = MaterialTheme.colors.background,
            fontSize = 12.sp
        )
        Spacer(Modifier.padding(2.dp))

        Text(
            text = "${clock?.hour}:${clock?.minute}:${clock?.second}",
            color = Color.Yellow,
            style = MaterialTheme.typography.h6
        )
    }
}