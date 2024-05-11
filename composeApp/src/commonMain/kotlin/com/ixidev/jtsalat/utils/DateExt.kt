package com.ixidev.jtsalat.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


fun LocalDateTime.now() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())