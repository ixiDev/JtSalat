import android.os.Build


class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

fun tets() {
//    java.lang.Double.isNaN()
    val dd = 0.00
    dd.isNaN()
}

