import kotlin.math.PI

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

