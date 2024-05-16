package com.ixidev.jtsalat.utils

import kotlin.math.PI
import kotlin.math.max
import kotlin.math.min

//fun toRadians(degree: Double): Double = degree * PI / 180
fun Double.toRadians(): Double = this * PI / 180


fun isInQibla(qiblaDegree: Int, currentDegree: Int): Boolean {
    val max = max(qiblaDegree, currentDegree)
    val min = min(qiblaDegree, currentDegree)
    return (max - min) <= 3
}


