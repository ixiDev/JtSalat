package com.ixidev.jtsalat.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.ixidev.jtsalat.data.SalatInfo
import com.ixidev.jtsalat.data.SalatType
import com.ixidev.jtsalat.ui.components.AnalogClock
import com.ixidev.jtsalat.ui.components.ClockStyle
import com.ixidev.jtsalat.ui.components.LocationInfoView
import com.ixidev.jtsalat.ui.components.NextSalatTimeView
import com.ixidev.jtsalat.utils.ClockFlow
import jtsalat.composeapp.generated.resources.Res
import jtsalat.composeapp.generated.resources.app_background
import jtsalat.composeapp.generated.resources.home_background
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

private val clockStyle = ClockStyle(
    minutesHandColor = Color.White,
    secondsHandColor = Color.Yellow,
    hoursHandColor = Color.White,
    clockCircleColor = Color.White,
    textClockColor = Color.White,
    centreDotColor = Color.White,
    textPadding = 0.75f
)

@OptIn(ExperimentalResourceApi::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    val viewModel = koinInject<HomeViewModel>()

    val clockFlow = remember { ClockFlow.now() }

    Box(modifier){
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(Res.drawable.home_background),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        Column(modifier = Modifier.fillMaxSize()) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                AnalogClock(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .aspectRatio(1f)
                        .align(Alignment.BottomCenter),
                    style = clockStyle,
                    clockFlow = clockFlow
                )
            }


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.9f)
                    .padding(top = 30.dp)
            ) {
                LocationInfoView(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    location = viewModel.currentLocation,
                    clockFlow = clockFlow
                )

                Spacer(modifier = Modifier.height(40.dp))
                val nexSalatInfo = remember {
                    SalatInfo(
                        salatype = SalatType.ISHA,
                        time = LocalDateTime(
                            year = 2024,
                            monthNumber = 5,
                            dayOfMonth = 11,
                            hour = 21,
                            minute = 50,
                            second = 0
                        ).time
                    )
                }
                NextSalatTimeView(
                    Modifier.fillMaxWidth(),
                    nexSalatInfo,
                    clockFlow
                )
            }
        }
    }

}


