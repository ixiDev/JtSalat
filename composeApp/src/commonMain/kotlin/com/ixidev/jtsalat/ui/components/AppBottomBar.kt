package com.ixidev.jtsalat.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ixidev.jtsalat.ui.Destinations
import jtsalat.composeapp.generated.resources.Res
import jtsalat.composeapp.generated.resources.ic_calender
import jtsalat.composeapp.generated.resources.ic_home
import jtsalat.composeapp.generated.resources.ic_qibla
import jtsalat.composeapp.generated.resources.ic_settings
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AppBottomBar(modifier: Modifier, navController: NavHostController) {
    BottomNavigation(
        modifier = modifier,
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.7f),
        contentColor = MaterialTheme.colors.secondary //Color(0xff222222)
    ) {

        BottomNavigationItem(
            modifier = Modifier.padding(top = 40.dp),
            alwaysShowLabel = true,
            selected = false,
            onClick = { navController.navigate(Destinations.HOME) },
            icon = {
                Icon(
                    modifier = Modifier
                        .size(30.dp),
                    painter = painterResource(Res.drawable.ic_home),
                    contentDescription = null,
                )
            }
        )
        val infiniteTransition = rememberInfiniteTransition()

        BottomNavigationItem(
            modifier = Modifier.padding(top = 10.dp),
            selected = false,
            onClick = { navController.navigate(Destinations.COMPASS) },
            icon = {
                val animate = infiniteTransition.animateFloat(
                    initialValue = -30f,
                    targetValue = 30f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 2000),
                        repeatMode = RepeatMode.Reverse
                    )
                )

                Icon(
                    modifier = Modifier
                        .rotate(animate.value)
                        .size(50.dp),
                    painter = painterResource(Res.drawable.ic_qibla),
                    contentDescription = null,
                )

            }
        )

        BottomNavigationItem(
            modifier = Modifier.padding(top = 40.dp),
            selected = false,
            onClick = { navController.navigate(Destinations.CALENDAR) },
            icon = {
                Icon(
                    modifier = Modifier
                        .size(30.dp),
                    painter = painterResource(Res.drawable.ic_calender),
                    contentDescription = null,
                )
            }
        )

    }
}