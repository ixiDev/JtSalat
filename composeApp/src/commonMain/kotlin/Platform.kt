import kotlin.math.PI

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

fun toRadian(degree: Double): Double  = degree * PI / 180