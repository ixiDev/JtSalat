package com.ixidev.jtsalat.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.waterfall
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ixidev.jtsalat.ui.components.AppBottomBar
import com.ixidev.jtsalat.ui.screens.calender.CalendarScreen
import com.ixidev.jtsalat.ui.screens.compass.CompassScreen
import com.ixidev.jtsalat.ui.screens.home.HomeScreen
import com.ixidev.jtsalat.ui.theme.JtSalatTheme
import com.ixidev.jtsalat.utils.BottomNavCurve
import dev.icerock.moko.geo.compose.BindLocationTrackerEffect
import dev.icerock.moko.geo.compose.LocationTrackerAccuracy
import dev.icerock.moko.geo.compose.rememberLocationTrackerFactory
import jtsalat.composeapp.generated.resources.Res
import jtsalat.composeapp.generated.resources.app_background
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
fun App() {
    val facotry = rememberLocationTrackerFactory(LocationTrackerAccuracy.LowPower)
    val locationTracker = remember { facotry.createLocationTracker() }
    BindLocationTrackerEffect(locationTracker)

    val viewModel = koinInject<AppViewModel>(
        parameters = { parametersOf(locationTracker) }
    )
    JtSalatTheme(darkTheme = false) {
        val navController = rememberNavController()

        Box(
            modifier = Modifier.fillMaxSize()
                .windowInsetsPadding(WindowInsets.waterfall)
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(Res.drawable.app_background),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
            Box(
                Modifier.fillMaxSize()
                    .background(MaterialTheme.colors.onBackground.copy(alpha = 0.2f))
            )

            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                AppHost(
                    modifier = Modifier.fillMaxWidth()
                        .weight(1f),
                    navController = navController
                )

                AppBottomBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(BottomNavCurve(radius = 80))
                        .height(100.dp),
                    navController = navController
                )

            }
        }

    }

    LaunchedEffect(locationTracker) {
        viewModel.startTracking()
    }
}

@Composable
fun AppHost(modifier: Modifier, navController: NavHostController) {


    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Destinations.DEFAULT,
//        enterTransition = {
//            slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Start, animationSpec =  tween(300))
//        },
//        exitTransition = {
//            slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Start, animationSpec = tween(300))
//        },
//        popEnterTransition = {
//            slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Start, animationSpec = tween(300))
//        },
//        popExitTransition = {
//            slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Start, animationSpec = tween(300))
//        }


    ) {
        composable(Destinations.HOME) {
            HomeScreen(modifier = Modifier.fillMaxSize())
        }
        composable(Destinations.COMPASS) {
            CompassScreen(modifier = Modifier.fillMaxSize())
        }
        composable(Destinations.CALENDAR) {
            CalendarScreen(modifier = Modifier.fillMaxSize())
        }
    }


}
