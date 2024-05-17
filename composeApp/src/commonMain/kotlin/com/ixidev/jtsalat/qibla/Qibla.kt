package com.ixidev.jtsalat.qibla

import com.ixidev.jtsalat.qibla.Formula.qibla

/**
 * Created by ABDELMAJID ID ALI on 10/11/2019.
 * Email : abdelmajid.idali@gmail.com
 * Github : https://github.com/ixiDev
 */
object Qibla {
    /**
     * Return qibla direction in degrees from the north (clock-wise).
     *
     * @param lat latitude in degrees
     * @param lng longitude in degrees
     * @return 0 means north, 90 means east, 270 means west, etc
     */
    fun findDirection(lat: Double, lng: Double): Double {
        return qibla(lat, lng)
    }

    /**
     * Calculate qibla direction as 3-tuple of degree-minute-second.
     *
     * @param lat
     * @param lng
     * @return
     */
    fun findDirectionDms(lat: Double, lng: Double): Dms {
        return Dms(qibla(lat, lng))
    }
}
