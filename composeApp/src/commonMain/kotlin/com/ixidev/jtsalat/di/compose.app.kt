package com.ixidev.jtsalat.di

import androidx.compose.runtime.Composable
import org.koin.compose.KoinApplication
import org.koin.core.logger.Level


@Composable
fun KoinComposeApp(content: @Composable () -> Unit) {
    KoinApplication(
        application = {
            logger(KoinLogger.newLogger(Level.DEBUG))
            // your preview config here
            modules(koinModules)
        },
        content = content
    )
}