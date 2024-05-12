import android.graphics.PointF
import android.os.Build
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

//actual object JMath {
//    actual fun toRadians(angle: Double): Double {
//        return Math.toRadians(angle)
//    }
//}
