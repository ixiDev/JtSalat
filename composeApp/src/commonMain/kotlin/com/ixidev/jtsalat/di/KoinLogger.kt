package com.ixidev.jtsalat.di

import org.koin.core.logger.Level
import org.koin.core.logger.Logger

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object KoinLogger {
    fun newLogger(level: Level): Logger
}