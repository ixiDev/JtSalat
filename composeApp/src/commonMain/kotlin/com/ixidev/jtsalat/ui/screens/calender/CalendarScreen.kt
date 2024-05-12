package com.ixidev.jtsalat.ui.screens.calender

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import io.wojciechosak.calendar.config.rememberCalendarState
import io.wojciechosak.calendar.view.CalendarView
import io.wojciechosak.calendar.view.HorizontalCalendarView
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalendarScreen(modifier: Modifier = Modifier) {

    val startDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    Column(modifier = modifier) {

        HorizontalCalendarView(
            modifier = Modifier.fillMaxWidth(),
            startDate = startDate.date,

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
    }

}