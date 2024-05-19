package com.ixidev.jtsalat.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.offsetAt
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.asin
import kotlin.math.atan
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.tan

@Suppress("unused")
class PrayTimes {
    /**
     * caculation method
     */
    var calcMethod: Int = 0

    /**
     * Juristic method for Asr
     */
    var asrJuristic: Int = 0

    /**
     * minutes after mid-day for Dhuhr
     */
    var dhuhrMinutes: Int = 0

    /**
     * adjusting method for higher latitudes
     */
    var adjustHighLats: Int = 0
    var timeFormat: Int = 0

    /**
     * latitude
     */
    var lat: Double = 0.0

    /**
     * longitude
     */
    var lng: Double = 0.0
    var timeZone: Double = 0.0

    /**
     * Julian date
     */
    var jDate: Double = 0.0

    val timeNames: List<String> =
        mutableListOf("Fajr", "Sunrise", "Dhuhr", "Asr", "Sunset", "Maghrib", "Isha")
    var offsets: IntArray = IntArray(7)
    var invalidTime: String = "-------" // The string used for invalid times
    var numIterations: Int = 1 // number of iterations needed to compute times

    /**
     * this.methodParams[methodNum] = new Array(fa, ms, mv, is, iv);
     *
     *
     * fa : fajr angle ms : maghrib selector (0 = angle; 1 = minutes after
     * sunset) mv : maghrib parameter value (in angle or minutes) is : isha
     * selector (0 = angle; 1 = minutes after maghrib) iv : isha parameter value
     * (in angle or minutes)
     */
    private var methodParams: HashMap<Int, DoubleArray>

    lateinit var prayerTimesCurrent: DoubleArray

    init {
        offsets[0] = 0
        offsets[1] = 0
        offsets[2] = 0
        offsets[3] = 0
        offsets[4] = 0
        offsets[5] = 0
        offsets[6] = 0
        methodParams = HashMap()

        // Jafari
        val Jvalues = doubleArrayOf(16.0, 0.0, 4.0, 0.0, 14.0)
        methodParams[CalculMethod.JAFARI] = Jvalues

        // Karachi
        val Kvalues = doubleArrayOf(18.0, 1.0, 0.0, 0.0, 18.0)
        methodParams[CalculMethod.KARACHI] = Kvalues

        // ISNA
        val Ivalues = doubleArrayOf(15.0, 1.0, 0.0, 0.0, 15.0)
        methodParams[CalculMethod.ISNA] = Ivalues

        // MWL
        val MWvalues = doubleArrayOf(18.0, 1.0, 0.0, 0.0, 17.0)
        methodParams[CalculMethod.MWL] = MWvalues

        // Makkah
        val MKvalues = doubleArrayOf(18.5, 1.0, 0.0, 1.0, 90.0)
        methodParams[CalculMethod.MAKKA] = MKvalues

        // Egypt
        val Evalues = doubleArrayOf(19.5, 1.0, 0.0, 0.0, 17.5)
        methodParams[CalculMethod.EGYPT] = Evalues

        // Tehran
        val Tvalues = doubleArrayOf(17.7, 0.0, 4.5, 0.0, 14.0)
        methodParams[CalculMethod.TEHRAN] = Tvalues

        // Custom
        val Cvalues = doubleArrayOf(18.0, 1.0, 0.0, 0.0, 17.0)
        methodParams[CalculMethod.Custom] = Cvalues
    }

    object CalculMethod {
        /**
         * Ithna Ashari
         */
        const val JAFARI: Int = 0

        /**
         * University of Islamic Sciences, Karachi
         */
        const val KARACHI: Int = 1

        /**
         * Islamic Society of North America (ISNA)
         */
        const val ISNA: Int = 2

        /**
         * Muslim World League (MWL)
         */
        const val MWL: Int = 3

        /**
         * Umm al-Qura, Makkah
         */
        const val MAKKA: Int = 4

        /**
         * Egyptian General Authority of Survey
         */
        const val EGYPT: Int = 5

        /**
         * Institute of Geophysics, University of Tehran
         */
        const val TEHRAN: Int = 6

        const val Custom: Int = 7 // Custom Setting
    }

    object JuristicMethods {
        const val SHAFII: Int = 0
        const val HANAFI: Int = 1
    }

    object AdjustingMethods {
        /**
         * No adjustment
         */
        const val NONE: Int = 0

        /**
         * middle of night
         */
        const val MidNight: Int = 1

        /**
         * 1/7th of night
         */
        const val OneSeventh: Int = 2

        /**
         * angle/60th of night
         */
        const val AngleBased: Int = 3
    }

    object TimeFormats {
        /**
         * 24-hour format
         */
        const val Time24: Int = 0

        /**
         * 12-hour format
         */
        const val Time12: Int = 1

        /**
         * 12-hour format with no suffix
         */
        const val Time12NS: Int = 2

        /**
         * floating point number
         */
        const val Floating: Int = 3
    }

    // ---------------------- Trigonometric Functions -----------------------
    // range reduce angle in degrees.
    private fun fixangle(a: Double): Double {
        var a = a
        a = a - (360 * (floor(a / 360.0)))

        a = if (a < 0) (a + 360) else a

        return a
    }

    // radian to degree
    private fun radiansToDegrees(alpha: Double): Double {
        return ((alpha * 180.0) / PI)
    }

    // deree to radian
    private fun DegreesToRadians(alpha: Double): Double {
        return ((alpha * PI) / 180.0)
    }

    // degree sin
    private fun dsin(d: Double): Double {
        return (sin(DegreesToRadians(d)))
    }

    // degree cos
    private fun dcos(d: Double): Double {
        return (cos(DegreesToRadians(d)))
    }

    // degree tan
    private fun dtan(d: Double): Double {
        return (tan(DegreesToRadians(d)))
    }

    // degree arcsin
    private fun darcsin(x: Double): Double {
        val `val` = asin(x)
        return radiansToDegrees(`val`)
    }

    // degree arccos
    private fun darccos(x: Double): Double {
        val `val` = acos(x)
        return radiansToDegrees(`val`)
    }

    // degree arctan
    private fun darctan(x: Double): Double {
        val `val` = atan(x)
        return radiansToDegrees(`val`)
    }

    // degree arctan2
    private fun darctan2(y: Double, x: Double): Double {
        val `val` = atan2(y, x)
        return radiansToDegrees(`val`)
    }

    // degree arccot
    private fun darccot(x: Double): Double {
        val `val` = atan2(1.0, x)
        return radiansToDegrees(`val`)
    }

    // detect daylight saving in a given date
//    private fun detectDaylightSaving(): Double {
//        val timez = TimeZone.currentSystemDefault()
//        return timez.getDSTSavings().toDouble()
//    }

    // ---------------------- Julian Date Functions -----------------------
    // calculate julian date from a calendar date
    private fun julianDate(year: Int, month: Int, day: Int): Double {
        var year = year
        var month = month
        if (month <= 2) {
            year -= 1
            month += 12
        }
        val A = floor(year / 100.0)

        val B = 2 - A + floor(A / 4.0)

        val JD = floor(365.25 * (year + 4716)) + floor(30.6001 * (month + 1)) + day + B - 1524.5

        return JD
    }

    // convert a calendar date to julian date (second method)
    private fun calcJD(year: Int, month: Int, day: Int): Double {
        val J1970 = 2440588.0
        val date = LocalDateTime(year, month - 1, day, 1, 1, 1)

        val ms: Double =
            date.time.toMillisecondOfDay().toDouble() // # of milliseconds since midnight Jan 1,
        // 1970
        val days = floor(ms / (1000.0 * 60.0 * 60.0 * 24.0))
        return J1970 + days - 0.5
    }

    // ---------------------- Calculation Functions -----------------------
    // References:
    // http://www.ummah.net/astronomy/saltime
    // http://aa.usno.navy.mil/faq/docs/SunApprox.html
    // compute declination angle of sun and equation of time
    private fun sunPosition(jd: Double): DoubleArray {
        val D = jd - 2451545
        val g = fixangle(357.529 + 0.98560028 * D)
        val q = fixangle(280.459 + 0.98564736 * D)
        val L = fixangle(q + (1.915 * dsin(g)) + (0.020 * dsin(2 * g)))

        // double R = 1.00014 - 0.01671 * [self dcos:g] - 0.00014 * [self dcos:
        // (2*g)];
        val e = 23.439 - (0.00000036 * D)
        val d = darcsin(dsin(e) * dsin(L))
        var RA = (darctan2((dcos(e) * dsin(L)), (dcos(L)))) / 15.0
        RA = fixhour(RA)
        val EqT = q / 15.0 - RA
        val sPosition = DoubleArray(2)
        sPosition[0] = d
        sPosition[1] = EqT

        return sPosition
    }

    // compute equation of time
    private fun equationOfTime(jd: Double): Double {
        val eq = sunPosition(jd)[1]
        return eq
    }

    // compute declination angle of sun
    private fun sunDeclination(jd: Double): Double {
        val d = sunPosition(jd)[0]
        return d
    }

    // compute mid-day (Dhuhr, Zawal) time
    private fun computeMidDay(t: Double): Double {
        val T = equationOfTime(this.jDate + t)
        val Z = fixhour(12 - T)
        return Z
    }

    // compute time for a given angle G
    private fun computeTime(G: Double, t: Double): Double {
        val D = sunDeclination(this.jDate + t)
        val Z = computeMidDay(t)
        val Beg = -dsin(G) - dsin(D) * dsin(this.lat)
        val Mid = dcos(D) * dcos(this.lat)
        val V = darccos(Beg / Mid) / 15.0

        return Z + (if (G > 90) -V else V)
    }

    // compute the time of Asr
    // Shafii: step=1, Hanafi: step=2
    private fun computeAsr(step: Double, t: Double): Double {
        val D = sunDeclination(this.jDate + t)
        val G = -darccot(step + dtan(abs(this.lat - D)))
        return computeTime(G, t)
    }

    // ---------------------- Misc Functions -----------------------
    // compute the difference between two times
    private fun timeDiff(time1: Double, time2: Double): Double {
        return fixhour(time2 - time1)
    }

    // -------------------- Interface Functions --------------------
    // return prayer times for a given date
    private fun getDatePrayerTimes(
        year: Int, month: Int, day: Int,
        latitude: Double, longitude: Double, tZone: Double
    ): ArrayList<String> {
        this.lat = latitude
        this.lng = longitude
        this.timeZone = tZone
        this.jDate = julianDate(year, month, day)
        val lonDiff = longitude / (15.0 * 24.0)
        this.jDate -= lonDiff
        return computeDayTimes()
    }

    // return prayer times for a given date
    fun getPrayerTimes(
        date: LocalDate,
        latitude: Double,
        longitude: Double,
        tZone: Double
    ): ArrayList<String> {
        val year: Int = date.year
        val month: Int = date.monthNumber
        val day: Int = date.dayOfMonth

        return getDatePrayerTimes(year, month + 1, day, latitude, longitude, tZone)
    }

    fun getPrayerTimesDouble(
        date: LocalDate,
        latitude: Double,
        longitude: Double,
        tZone: Double
    ): DoubleArray {
        val year: Int = date.year
        val month: Int = date.monthNumber
        val day: Int = date.dayOfMonth

        return getDatePrayerTimesDouble(year, month + 1, day, latitude, longitude, tZone)
    }

    private fun getDatePrayerTimesDouble(
        year: Int,
        month: Int,
        day: Int,
        latitude: Double,
        longitude: Double,
        tZone: Double
    ): DoubleArray {
        this.lat = latitude
        this.lng = longitude
        this.timeZone = tZone
        this.jDate = julianDate(year, month, day)
        val lonDiff = longitude / (15.0 * 24.0)
        this.jDate -= lonDiff
        return computeDoubleDayTimes()
    }

    // set custom values for calculation parameters
    private fun setCustomParams(params: DoubleArray) {
        for (i in 0..4) {
            if (params[i] == -1.0) {
                params[i] = methodParams[this.calcMethod]?.get(i) ?: 0.0
                methodParams[CalculMethod.Custom] = params
            } else {
                methodParams[CalculMethod.Custom]?.set(i, params[i])
            }
        }
        this.calcMethod = CalculMethod.Custom
    }

    // set the angle for calculating Fajr
    fun setFajrAngle(angle: Double) {
        val params = doubleArrayOf(angle, -1.0, -1.0, -1.0, -1.0)
        setCustomParams(params)
    }

    /**
     * set the angle for calculating Maghrib
     */
    fun setMaghribAngle(angle: Double) {
        val params = doubleArrayOf(-1.0, 0.0, angle, -1.0, -1.0)
        setCustomParams(params)
    }

    // set the angle for calculating Isha
    fun setIshaAngle(angle: Double) {
        val params = doubleArrayOf(-1.0, -1.0, -1.0, 0.0, angle)
        setCustomParams(params)
    }

    // set the minutes after Sunset for calculating Maghrib
    fun setMaghribMinutes(minutes: Double) {
        val params = doubleArrayOf(-1.0, 1.0, minutes, -1.0, -1.0)
        setCustomParams(params)
    }

    // set the minutes after Maghrib for calculating Isha
    fun setIshaMinutes(minutes: Double) {
        val params = doubleArrayOf(-1.0, -1.0, -1.0, 1.0, minutes)
        setCustomParams(params)
    }

    // convert double hours to 12h format with no suffix
    fun floatToTime12NS(time: Double): String {
        return floatToTime12(time, true)
    }

    // ---------------------- Compute Prayer Times -----------------------
    // compute prayer times at given julian date
    private fun computeTimes(times: DoubleArray): DoubleArray {
        val t = dayPortion(times)

        val fajr = this.computeTime(180 - (methodParams[this.calcMethod]?.get(0) ?: 0.0), t[0])

        val sunrise = this.computeTime(180 - 0.833, t[1])

        val dhuhr = this.computeMidDay(t[2])
        val asr = this.computeAsr((1 + this.asrJuristic).toDouble(), t[3])
        val sunset = this.computeTime(0.833, t[4])

        val maghrib = this.computeTime(
            methodParams[this.calcMethod]?.get(2) ?: 0.0, t[5]
        )
        val isha = this.computeTime(
            methodParams.get(this.calcMethod)?.get(4) ?: 0.0, t[6]
        )

        val ctimes = doubleArrayOf(fajr, sunrise, dhuhr, asr, sunset, maghrib, isha)

        return ctimes
    }

    // compute prayer times at given julian date
    private fun computeDayTimes(): ArrayList<String> {
        var times = doubleArrayOf(5.0, 6.0, 12.0, 13.0, 18.0, 18.0, 18.0) // default times

        for (i in 1..this.numIterations) {
            times = computeTimes(times)
        }

        times = adjustTimes(times)
        times = tuneTimes(times)

        return adjustTimesFormat(times)
    }

    private fun computeDoubleDayTimes(): DoubleArray {
        var times = doubleArrayOf(5.0, 6.0, 12.0, 13.0, 18.0, 18.0, 18.0) // default times

        for (i in 1..this.numIterations) {
            times = computeTimes(times)
        }

        times = adjustTimes(times)
        times = tuneTimes(times)

        return times
    }

    // adjust times in a prayer time array
    private fun adjustTimes(times: DoubleArray): DoubleArray {
        var times = times
        for (i in times.indices) {
            times[i] += this.timeZone - this.lng / 15
        }

        times[2] += (this.dhuhrMinutes / 60).toDouble() // Dhuhr
        if (methodParams.get(this.calcMethod)?.get(1) == 1.0) // Maghrib
        {
            times[5] = times[4] + (methodParams.get(this.calcMethod)?.get(2) ?: 0.0) / 60
        }
        if (methodParams.get(this.calcMethod)?.get(3) == 1.0) // Isha
        {
            times[6] = times[5] + (methodParams.get(this.calcMethod)?.get(4) ?: 0.0) / 60
        }

        if (this.adjustHighLats != AdjustingMethods.NONE) {
            times = adjustHighLatTimes(times)
        }

        return times
    }

    // convert times array to given time format
    private fun adjustTimesFormat(times: DoubleArray): ArrayList<String> {
        val result: ArrayList<String> = ArrayList()

        if (this.timeFormat == TimeFormats.Floating) {
            for (time in times) {
                result.add(time.toString())
            }
            return result
        }

        for (i in 0..6) {
            if (this.timeFormat == TimeFormats.Time12) {
                result.add(floatToTime12(times[i], false))
            } else if (this.timeFormat == TimeFormats.Time12NS) {
                result.add(floatToTime12(times[i], true))
            } else {
                result.add(floatToTime24(times[i]))
            }
        }
        return result
    }

    // adjust Fajr, Isha and Maghrib for locations in higher latitudes
    private fun adjustHighLatTimes(times: DoubleArray): DoubleArray {
        val nightTime = timeDiff(times[4], times[1]) // sunset to sunrise

        // Adjust Fajr
        val FajrDiff = nightPortion(methodParams.get(this.calcMethod)!![0]) * nightTime

        if (times[0].isNaN() || timeDiff(times[0], times[1]) > FajrDiff) {
            times[0] = times[1] - FajrDiff
        }

        // Adjust Isha
        val IshaAngle = if ((methodParams.get(this.calcMethod)!!.get(3) == 0.0))
            methodParams.get(calcMethod)!!.get(4)
        else 18.0
        val IshaDiff = this.nightPortion(IshaAngle) * nightTime
        if (times[6].isNaN() || this.timeDiff(times[4], times[6]) > IshaDiff) {
            times[6] = times[4] + IshaDiff
        }

        // Adjust Maghrib
        val MaghribAngle = if ((methodParams.get(this.calcMethod)!!.get(1) == 0.0))
            methodParams.get(calcMethod)!!.get(2)
        else 4.0

        val MaghribDiff = nightPortion(MaghribAngle) * nightTime
        if (times[5].isNaN() || this.timeDiff(times[4], times[5]) > MaghribDiff) {
            times[5] = times[4] + MaghribDiff
        }

        return times
    }

    // the night portion used for adjusting times in higher latitudes
    private fun nightPortion(angle: Double): Double {
        var calc = 0.0

        if (adjustHighLats == AdjustingMethods.AngleBased) calc = (angle) / 60.0
        else if (adjustHighLats == AdjustingMethods.MidNight) calc = 0.5
        else if (adjustHighLats == AdjustingMethods.OneSeventh) calc = 0.14286

        return calc
    }

    // convert hours to day portions
    private fun dayPortion(times: DoubleArray): DoubleArray {
        for (i in 0..6) {
            times[i] /= 24.0
        }
        return times
    }

    // Tune timings for adjustments
    // Set time offsets
    fun tune(offsetTimes: IntArray) {
        // offsetTimes length
        // should be 7 in order
        // of Fajr, Sunrise,
        // Dhuhr, Asr, Sunset,
        // Maghrib, Isha

//        java.lang.System.arraycopy(offsetTimes, 0, this.offsets, 0, offsetTimes.size)
    }

    private fun tuneTimes(times: DoubleArray): DoubleArray {
        for (i in times.indices) {
            times[i] = times[i] + offsets[i] / 60.0
        }

        return times
    }

    fun getMethodParams(): HashMap<Int, DoubleArray> {
        return methodParams
    }

    fun setMethodParams(methodParams: HashMap<Int, DoubleArray>) {
        this.methodParams = methodParams
    }

    companion object {
        // range reduce hours to 0..23
        private fun fixhour(a: Double): Double {
            var a = a
            a = a - 24.0 * floor(a / 24.0)
            a = if (a < 0) (a + 24) else a
            return a
        }

        // ---------------------- Time-Zone Functions -----------------------
        // compute local time-zone for a specific date
//        fun getTimeZoneFromDate(date: LocalDateTime): Double {
//            val tz = TimeZone.currentSystemDefault()
//
//            return tz.getOffset(date.getTime()) / 3600000.0
//        }

        val baseTimeZone: Double
            // compute base time-zone of the system
            get() {
                val timez = TimeZone.currentSystemDefault()
                return (timez.offsetAt(Clock.System.now()).totalSeconds / 1000.0) / 3600
            }

        // convert double hours to 24h format
        fun floatToTime24(time: Double): String {
            var time = time
            val result: String

            if (time.isNaN()) {
                return ""
            }
            time = fixhour(time + 0.5 / 60.0) // add 0.5 minutes to round
            val hours = floor(time).toInt()
            val minutes = floor((time - hours) * 60.0)

            result =
                if ((hours in 0..9) && (minutes in 0.0..9.0)) {
                    "0" + hours + ":0" + minutes.roundToInt()
                } else if ((hours in 0..9)) {
                    "0" + hours + ":" + minutes.roundToInt()
                } else if ((minutes in 0.0..9.0)) {
                    hours.toString() + ":0" + minutes.roundToInt()
                } else {
                    hours.toString() + ":" + minutes.roundToInt()
                }
            return result
        }

        // convert double hours to 12h format
        fun floatToTime12(time: Double, noSuffix: Boolean): String {
            var time = time
            if (time.isNaN()) {
                return "-----"
            }

            time = fixhour(time + 0.5 / 60) // add 0.5 minutes to round
            var hours = floor(time).toInt()
            val minutes = floor((time - hours) * 60)
            val result: String
            val suffix = if (hours >= 12) {
                "pm"
            } else {
                "am"
            }
            hours = ((((hours + 12) - 1) % (12)) + 1)
            /*hours = (hours + 12) - 1;
        int hrs = (int) hours % 12;
        hrs += 1;*/
            result = if (!noSuffix) {
                if ((hours in 0..9) && (minutes in 0.0..9.0)) {
                    ("0" + hours + ":0" + minutes.roundToInt() + " "
                            + suffix)
                } else if ((hours in 0..9)) {
                    "0" + hours + ":" + minutes.roundToInt() + " " + suffix
                } else if ((minutes in 0.0..9.0)) {
                    hours.toString() + ":0" + minutes.roundToInt() + " " + suffix
                } else {
                    hours.toString() + ":" + minutes.roundToInt() + " " + suffix
                }
            } else {
                if ((hours in 0..9) && (minutes in 0.0..9.0)) {
                    "0" + hours + ":0" + minutes.roundToInt()
                } else if ((hours in 0..9)) {
                    "0" + hours + ":" + minutes.roundToInt()
                } else if ((minutes in 0.0..9.0)) {
                    hours.toString() + ":0" + minutes.roundToInt()
                } else {
                    hours.toString() + ":" + minutes.roundToInt()
                }
            }
            return result
        }
    }
}
