package com.ixidev.jtsalat.ui.screens.calender

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ixidev.jtsalat.ui.components.LocationNameRow
import com.ixidev.jtsalat.ui.components.SalatItemView
import io.wojciechosak.calendar.config.rememberCalendarState
import io.wojciechosak.calendar.view.CalendarView
import io.wojciechosak.calendar.view.HorizontalCalendarView
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.compose.koinInject


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalendarScreen(modifier: Modifier = Modifier) {

    val viewModel = koinInject<CalenderViewModel>()
    val startDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    Column(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalCalendarView(
            modifier = Modifier.fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colors.primary)
                .padding(10.dp),
            startDate = startDate.date
        ) { monthOffset ->
            CalendarView(
                config = rememberCalendarState(
                    startDate = startDate.date,
                    monthOffset = monthOffset
                ),
                day = { dayState ->
                    // define your day composable here!
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "${dayState.date.dayOfMonth}",
                        color = if (dayState.isActiveDay) Color.Black else Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        val calenderState by viewModel.calenderStateFlow.collectAsState(CalenderState())


        LocationNameRow(
            modifier = Modifier.fillMaxWidth(),
            calenderState.locationInfo
        )
        Spacer(modifier = Modifier.height(16.dp))

        calenderState.prayTimes.forEach {
            SalatItemView(
                modifier = Modifier.fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colors.primary)
                    .padding(8.dp),
                salat = it
            )
            Spacer(modifier = Modifier.height(8.dp))
        }


    }

}