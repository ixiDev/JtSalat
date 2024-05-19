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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.ixidev.jtsalat.ui.components.AnalogClock
import com.ixidev.jtsalat.ui.components.ClockStyle
import com.ixidev.jtsalat.ui.components.LocationInfoView
import com.ixidev.jtsalat.ui.components.NextSalatTimeView
import com.ixidev.jtsalat.utils.ClockFlow
import jtsalat.composeapp.generated.resources.Res
import jtsalat.composeapp.generated.resources.home_background
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

    val homeState by viewModel.homeStateFlow.collectAsState(HomeState.EMPTY)
    val clockFlow = remember { ClockFlow.now() }

    Box(modifier) {
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
                    location = homeState.currentLocation,
                    clockFlow = clockFlow
                )

                Spacer(modifier = Modifier.height(40.dp))

                NextSalatTimeView(
                    Modifier.fillMaxWidth(),
                    homeState.nextSalat,
                    clockFlow
                )
            }
        }
    }

}


