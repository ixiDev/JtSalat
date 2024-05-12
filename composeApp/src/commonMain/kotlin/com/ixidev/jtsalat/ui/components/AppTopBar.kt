package com.ixidev.jtsalat.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ixidev.jtsalat.data.LocationInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.resources.ExperimentalResourceApi


@OptIn(ExperimentalResourceApi::class)
@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    clockFlow: Flow<LocalDateTime>,
    location: LocationInfo
) {

    val clockStyle = remember {
        ClockStyle(
            minutesHandColor = Color.White,
            secondsHandColor = Color.Yellow,
            hoursHandColor = Color.White,
            clockCircleColor = Color.White,
            textClockColor = Color.White,
            centreDotColor = Color.White,
            textPadding = 0.75f
        )
    }

    Box(modifier = modifier) {
//        Image(
//            modifier = Modifier.fillMaxWidth(),
//            painter = painterResource(Res.drawable.mosq),
//            contentDescription = "mosq",
//            contentScale = ContentScale.FillWidth
//        )
//        Spacer(
//            Modifier.fillMaxSize()
//                .background(Color(0xab35736a)) // TODO: move color to theme
//        )

        Row(Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            AnalogClock(
                modifier = Modifier
                    .fillMaxHeight(0.7f)
                    .aspectRatio(1f)
                    .padding(start = 30.dp, bottom = 2.dp),
                style = clockStyle,
                clockFlow = clockFlow
            )

            LocationInfoView(
                modifier = Modifier.weight(1f),
                location = location,
                clockFlow = clockFlow
            )
        }

    }
}

