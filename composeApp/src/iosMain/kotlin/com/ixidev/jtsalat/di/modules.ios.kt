package com.ixidev.jtsalat.di

import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.PrintLogger
import org.koin.dsl.module

actual fun platformModule() = module {

    single<Logger> {
        PrintLogger(level = Level.DEBUG)
    }
}