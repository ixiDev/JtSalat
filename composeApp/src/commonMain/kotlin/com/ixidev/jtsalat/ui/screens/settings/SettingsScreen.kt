package com.ixidev.jtsalat.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import jtsalat.composeapp.generated.resources.Res
import jtsalat.composeapp.generated.resources.settings
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject


@OptIn(ExperimentalResourceApi::class)
@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    val viewModel = koinInject<SettingsViewModel>()
    Column(modifier = modifier) {
        Text(
            text = stringResource(Res.string.settings),
            style = MaterialTheme.typography.h5,
        )

    }
}