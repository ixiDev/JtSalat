package com.ixidev.jtsalat.qibla

/**
 * Created by ABDELMAJID ID ALI on 10/11/2019.
 * Email : abdelmajid.idali@gmail.com
 * Github : https://github.com/ixiDev
 */
class Dms(degree: Double) {
    val degree: Int
    val minute: Int
    val second: Double

    init {
        val seconds = (degree * 3600)
        this.degree = (seconds / 3600).toInt()
        this.minute = (seconds % 3600 / 60).toInt()
        this.second = seconds % 60
    }

    fun toString(prec: Int): String {
        val d = degree
        val m = minute
        val s = second
//        return if (d < 0 || m < 0 || s < 0)
//            String.format("-%d° %d′ %." + prec + "f″", -d, -m, -s)
//        else String.format("%d° %d′ %." + prec + "f″", d, m, s)
        return "-%${-d}° %${-m}′ %.$prec f″"
    }

    override fun toString(): String {
        return toString(0)
    }


}