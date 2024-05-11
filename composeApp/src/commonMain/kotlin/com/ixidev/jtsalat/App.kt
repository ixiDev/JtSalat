package com.ixidev.jtsalat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.ixidev.jtsalat.ui.components.AnalogClock
import com.ixidev.jtsalat.ui.components.ClockStyle
import jtsalat.composeapp.generated.resources.Res
import jtsalat.composeapp.generated.resources.mosq
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
fun App() {
    MaterialTheme {

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
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier.fillMaxWidth()
                    .height(200.dp)
            ) {

                Image(
                    modifier = Modifier.fillMaxWidth(),
                    painter = painterResource(Res.drawable.mosq),
                    contentDescription = "Compose Multiplatform",
                    contentScale = ContentScale.FillWidth
                )
                Spacer(
                    Modifier.fillMaxSize()
                        .background(Color(0xab35736a))
                )
                AnalogClock(
                    modifier = Modifier
                        .fillMaxHeight(0.6f)
                        .align(Alignment.BottomStart)
                        .aspectRatio(1f)
                        .padding(start = 30.dp, bottom = 10.dp),
                    style = clockStyle
                )

            }
//            Row(
//                Modifier.fillMaxWidth()
//                    .height(200.dp)
//                    .background(Color(0xff35736a)),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//
//            }
        }

//        var showContent by remember { mutableStateOf(false) }
//        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

//            Button(onClick = { showContent = !showContent }) {
//                Text("Click me!")
//            }
//            AnimatedVisibility(showContent) {
//                val greeting = remember { Greeting().greet() }
//                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
//                    Image(painterResource(Res.drawable.compose_multiplatform), null)
//                    Text("Compose: $greeting")
//                }
//            }
//        }
    }
}