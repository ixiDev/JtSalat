package com.ixidev.jtsalat.utils

import kotlinx.datetime.LocalTime
import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSISO8601DateFormatter
import platform.Foundation.NSLocale
import platform.Foundation.NSTimeZone
import platform.Foundation.autoupdatingCurrentLocale
import platform.Foundation.currentLocale
import platform.Foundation.localTimeZone

actual object DateTimeFormatter {
    actual fun getFormattedDate(
        iso8601Timestamp: String,
        format: String,
    ): String {
        val date = getDateFromIso8601Timestamp(iso8601Timestamp) ?: return ""

        val dateFormatter = NSDateFormatter()
        dateFormatter.timeZone = NSTimeZone.localTimeZone
        dateFormatter.locale = NSLocale.autoupdatingCurrentLocale
        dateFormatter.dateFormat = format
        return dateFormatter.stringFromDate(date)
    }

    private fun getDateFromIso8601Timestamp(string: String): NSDate? {
        return NSISO8601DateFormatter().dateFromString(string)
    }

    actual fun format(
        localTime: LocalTime,
        format: String
    ): String {
        val formatter = NSDateFormatter().apply {
            dateFormat = format
            locale = NSLocale.currentLocale
        }
        val nsDate = NSDate(localTime.toMillisecondOfDay() / 1000.0)
        return formatter.stringFromDate(nsDate)
    }

}