package com.ixidev.jtsalat.ui.screens.compass

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ixidev.jtsalat.ui.theme.QiblaTintColor
import com.ixidev.jtsalat.utils.isInQibla
import jtsalat.composeapp.generated.resources.Res
import jtsalat.composeapp.generated.resources.compase_view_no_arrow
import jtsalat.composeapp.generated.resources.compass_qibla_direction
import jtsalat.composeapp.generated.resources.ic_qibla_needle
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject


@OptIn(ExperimentalResourceApi::class)
@Composable
fun CompassScreen(modifier: Modifier = Modifier) {

    val viewModel = koinInject<CompassViewModel>()
    val orientation by viewModel.orientationFLow.collectAsState(0)

    Column(
        modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        val isInQibla = isInQibla(93, orientation)
        Icon(
            painter = painterResource(Res.drawable.ic_qibla_needle),
            contentDescription = null,
            modifier = Modifier
                .size(30.dp),
            tint = if (isInQibla) QiblaTintColor else MaterialTheme.colors.primary
        )
        Box(
            modifier = Modifier.fillMaxWidth()
                .aspectRatio(1f)
//                .align(Alignment.CenterHorizontally)
                .padding(30.dp)
                .background(color = Color.White.copy(alpha = 0.5f), shape = CircleShape)

        ) {
            Image(
                modifier = Modifier.fillMaxSize()
                    .align(Alignment.Center)
                    .padding(15.dp)
                    .rotate(-orientation.toFloat()),
                painter = painterResource(Res.drawable.compase_view_no_arrow),
                contentDescription = null
            )
            Image(
                modifier = Modifier.fillMaxSize()
                    .align(Alignment.Center)
                    .padding(5.dp),
                painter = painterResource(Res.drawable.compass_qibla_direction),
                contentDescription = null,
                colorFilter = if (isInQibla) ColorFilter.tint(QiblaTintColor) else null
            )

            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "$orientation",
                style = MaterialTheme.typography.h2,
                color = if (isInQibla) QiblaTintColor else Color(0xffc4772b),
                textAlign = TextAlign.Center
            )

        }

    }
}