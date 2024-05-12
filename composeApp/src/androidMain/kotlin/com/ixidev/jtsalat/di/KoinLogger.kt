package com.ixidev.jtsalat.di

import org.koin.android.logger.AndroidLogger
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.PrintLogger

actual object KoinLogger {
    actual fun newLogger(level: Level): Logger {
        return AndroidLogger(level)
    }
}