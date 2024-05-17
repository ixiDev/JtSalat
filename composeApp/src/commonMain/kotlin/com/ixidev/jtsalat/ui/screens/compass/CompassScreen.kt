package com.ixidev.jtsalat.ui.screens.compass

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ixidev.jtsalat.ui.theme.QiblaTintColor
import dev.icerock.moko.geo.compose.BindLocationTrackerEffect
import dev.icerock.moko.geo.compose.LocationTrackerAccuracy
import dev.icerock.moko.geo.compose.rememberLocationTrackerFactory
import jtsalat.composeapp.generated.resources.Res
import jtsalat.composeapp.generated.resources.approx_qibla_direction
import jtsalat.composeapp.generated.resources.compase_view_no_arrow
import jtsalat.composeapp.generated.resources.compass_qibla_direction
import jtsalat.composeapp.generated.resources.ic_qibla_needle
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf


@OptIn(ExperimentalResourceApi::class)
@Composable
fun CompassScreen(modifier: Modifier = Modifier) {

    val facotry = rememberLocationTrackerFactory(LocationTrackerAccuracy.Best)
    val locationTracker = remember { facotry.createLocationTracker() }
    BindLocationTrackerEffect(locationTracker)

    val viewModel = koinInject<CompassViewModel>(
        parameters = { parametersOf(locationTracker) }
    )
    val compassState by viewModel.compassStateFlow.collectAsState(null)

    Column(
        modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Icon(
            painter = painterResource(Res.drawable.ic_qibla_needle),
            contentDescription = null,
            modifier = Modifier
                .size(30.dp),
            tint = if (compassState?.isInQibla == true) QiblaTintColor else MaterialTheme.colors.primary
        )
        Box(
            modifier = Modifier.fillMaxWidth()
                .aspectRatio(1f)
                .padding(30.dp)
                .background(color = Color.White.copy(alpha = 0.5f), shape = CircleShape)

        ) {
            Image(
                modifier = Modifier.fillMaxSize()
                    .align(Alignment.Center)
                    .padding(15.dp)
                    .rotate(-(compassState?.degrees?.toFloat() ?: 0f)),
                painter = painterResource(Res.drawable.compase_view_no_arrow),
                contentDescription = null
            )
            Image(
                modifier = Modifier.fillMaxSize()
                    .align(Alignment.Center)
                    .padding(5.dp),
                painter = painterResource(Res.drawable.compass_qibla_direction),
                contentDescription = null,
                colorFilter = if (compassState?.isInQibla == true) ColorFilter.tint(QiblaTintColor) else null
            )

            if (compassState == null) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    strokeWidth = 3.dp
                )
            } else {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "${compassState?.degrees}",
                    style = MaterialTheme.typography.h2,
                    color = if (compassState?.isInQibla == true) QiblaTintColor else Color(
                        0xffc4772b
                    ),
                    textAlign = TextAlign.Center
                )
            }

        }

        Spacer(modifier = Modifier.size(20.dp))

        compassState?.let { state ->
            Text(
                text = stringResource(Res.string.approx_qibla_direction),
                color = Color.Gray,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = MaterialTheme.typography.h6.fontSize
            )

            Spacer(modifier = Modifier.size(5.dp))

            Text(
                text = " ${state.location.city}  ${state.qiblaDegrees}°",
                color = Color.Gray,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = MaterialTheme.typography.h6.fontSize
            )
        }
    }

    LaunchedEffect(Unit) {
        viewModel.startLocationTracker()
    }

}