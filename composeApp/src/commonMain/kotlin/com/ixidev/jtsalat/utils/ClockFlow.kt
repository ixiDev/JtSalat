package com.ixidev.jtsalat.utils

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


object ClockFlow {


    fun now(): Flow<LocalDateTime> = flow {
        while (true) {
            // TODO: get timezone from settings
            emit(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()))
            delay(1000)
        }
    }


}