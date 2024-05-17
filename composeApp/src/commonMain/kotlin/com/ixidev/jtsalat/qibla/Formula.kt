package com.ixidev.jtsalat.qibla

import com.ixidev.jtsalat.utils.toDegrees
import com.ixidev.jtsalat.utils.toRadians
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.atan
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

/**
 * Created by ABDELMAJID ID ALI on 10/11/2019.
 * Email : abdelmajid.idali@gmail.com
 * Github : https://github.com/ixiDev
 */
internal object Formula {
    const val ASR_RATIO_MAJORITY: Int = 1
    const val ASR_RATIO_HANAFI: Int = 2

    fun zuhr(lng: Double, tz: Double, et: Double): Double {
        return 12 + tz - lng / 15.0 - et
    }

    fun asr(tZuhr: Double, lat: Double, ds: Double, asrRatio: Double): Double {
        val alt = acotDeg(asrRatio + tanDeg(abs(ds - lat))) // altitude of the sun
        return tZuhr + hourAngle(lat, alt, ds) / 15.0
    }

    fun maghrib(tZuhr: Double, lat: Double, ds: Double, h: Double): Double {
        val alt = -0.8333 - 0.0347 * sqrt(h)
        return tZuhr + hourAngle(lat, alt, ds) / 15.0
    }

    fun isha(tZuhr: Double, lat: Double, ds: Double, ishaAngle: Double): Double {
        val alt = -ishaAngle
        return tZuhr + hourAngle(lat, alt, ds) / 15.0
    }

    fun fajr(tZuhr: Double, lat: Double, ds: Double, fajrAngle: Double): Double {
        val alt = -fajrAngle
        return tZuhr - hourAngle(lat, alt, ds) / 15.0
    }

    fun sunrise(tZuhr: Double, lat: Double, ds: Double, h: Double): Double {
        val alt = -0.8333 - 0.0347 * sqrt(h) // equals to maghrib
        return tZuhr - hourAngle(lat, alt, ds) / 15.0
    }

    /**
     * Return hour angle in degrees, return positive or negative infinity if calculation
     * cannot be performed.
     *
     *
     * Negative and positive infinity has special meaning.
     */
    fun hourAngle(lat: Double, alt: Double, ds: Double): Double {
        val cosHa = ((sinDeg(alt) - sinDeg(lat) * sinDeg(ds))
                / (cosDeg(lat) * cosDeg(ds)))
        return if (cosHa < -1) Double.NEGATIVE_INFINITY
        else if (cosHa > 1) Double.POSITIVE_INFINITY
        else acosDeg(cosHa)
    }

    /**
     * Return Equation of Time in hours.
     */
    fun eqTime(jd: Double): Double {
        val u = (jd - 2451545) / 36525.0
        val l0 = 280.46607 + 36000.7698 * u // average longitude of the sun in degrees
        return ((-(1789 + 237 * u) * sinDeg(l0)
                - (7146 - 62 * u) * cosDeg(l0)
                + (9934 - 14 * u) * sinDeg(2 * l0)
                - (29 + 5 * u) * cosDeg(2 * l0)
                ) + (74 + 10 * u) * sinDeg(3 * l0) + (320 - 4 * u) * cosDeg(
            3 * l0
        )
                - 212 * sinDeg(4 * l0)) / 60000.0
    }

    /**
     * Return declination of the Sun in degrees.
     */
    fun declSun(jd: Double): Double {
        val t: Double = 2 * PI * (jd - 2451545) / 365.25 // angle of date
        return (0.37877 + 23.264 * sinDeg(57.297 * t - 79.547) + 0.3812 * sinDeg(
            2 * 57.297 * t - 82.682
        ) + 0.17132 * sinDeg(3 * 57.297 * t - 59.722))
    }

    /**
     * Return Julian Day of a Gregorian or Julian date.
     *
     *
     * Negative Julian Day (i.e. y < -4712 or 4713 BC) is not supported.
     *
     * @param y year
     * @param m month [1..12]
     * @param d day [1..31]
     */
    fun gregorianToJd(y: Int, m: Int, d: Int): Double {
        var y = y
        var m = m
        if (y < -4712) error("year ($y) < -4712")

        if (m <= 2) {
            m += 12
            y -= 1
        }
        val b: Double
        if (y > 1582 || (y == 1582 && (m > 10 || (m == 10 && d >= 15)))) {
            // first gregorian is 15-oct-1582
            val a = floor(y / 100.0)
            b = 2 + floor(a / 4.0) - a
        } else { // invalid dates (5-14) are also considered as julian
            b = 0.0
        }
        val abs_jd = (1720994.5 + floor(365.25 * y) + floor(30.6001 * (m + 1)) + d + b)
        return abs_jd
    }

    /**
     * Return Gregorian or Julian date of Julian Day.
     *
     *
     * Negative Julian Day < -0.5 is not supported.
     *
     * @return {year, month [1..12], day [1..31]}
     */
    fun jdToGregorian(jd: Double): IntArray {
        if (jd < -0.5) error("Julian Day ($jd) < -0.5")

        val jd1 = jd + 0.5
        val z = floor(jd1)
        val f = jd1 - z
        val a: Double
        if (z < 2299161) {
            a = z
        } else {
            val aa = floor((z - 1867216.25) / 36524.25)
            a = z + 1 + aa - floor(aa / 4.0)
        }
        val b = a + 1524
        val c = floor((b - 122.1) / 365.25)
        val d = floor(365.25 * c)
        val e = floor((b - d) / 30.6001)
        val day = (b - d - floor(30.6001 * e) + f).toInt()
        val month = (if (e < 14) e - 1 else e - 13).toInt()
        val year = (if (month <= 2) c - 4715 else c - 4716).toInt()
        return intArrayOf(year, month, day)
    }

    /**
     * Return weekday of Julian Day
     *
     * @return weekday [1..7] where 1 is Ahad/Sunday
     */
    fun jdToWeekday(jd: Double): Int {
        return floor(jd + 1.5).toInt() % 7 + 1
    }

    /**
     * Return Julian Day with added hours.
     */
    fun adjustJdHour(jd: Double, hours: Double): Double {
        return jd + hours / 24.0
    }

    /**
     * Return qibla direction in degrees from the north (clock-wise).
     *
     * @param lat latitude in degrees
     * @param lng longitude in degrees
     * @return 0 means north, 90 means east, 270 means west, etc
     */
    fun qibla(lat: Double, lng: Double): Double {
        val lngA = 39.82616111
        val latA = 21.42250833
        val deg = atan2Deg(
            sinDeg(lngA - lng),
            cosDeg(lat) * tanDeg(latA)
                    - sinDeg(lat) * cosDeg(lngA - lng)
        )
        return if (deg >= 0) deg else deg + 360
    }

    private fun sinDeg(deg: Double): Double {
        return sin(toRadians(deg))
    }

    private fun cosDeg(deg: Double): Double {
        return cos(toRadians(deg))
    }

    private fun acosDeg(x_r: Double): Double {
        return toDegrees(acos(x_r))
    }

    private fun tanDeg(deg: Double): Double {
        return tan(toRadians(deg))
    }

    private fun acotDeg(x_y: Double): Double {
        return toDegrees(atan(1.0 / x_y))
    }

    private fun atan2Deg(y: Double, x: Double): Double {
        return toDegrees(atan2(y, x))
    }

    private fun toRadians(deg: Double): Double {
        return deg.toRadians()
    }

    private fun toDegrees(rad: Double): Double {
        return rad.toDegrees()
    }
}
