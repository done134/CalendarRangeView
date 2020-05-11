package com.gentleyin.calendarrange.customviews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.Calendar;

final class CalendarRangeUtils {
    /**
     * Resets date time to HH:mm:ss SSS = 00:00:00 000
     *
     * @param date {@link Calendar object}
     */
    static void resetTime(@NonNull final Calendar date, @DateRangeCalendarManager.CalendarRangeType final int rangeType) {
        if (rangeType == DateRangeCalendarManager.CalendarRangeType.START_DATE) {
            date.set(Calendar.HOUR_OF_DAY, 0);
            date.set(Calendar.MINUTE, 0);
            date.set(Calendar.SECOND, 0);
            date.set(Calendar.MILLISECOND, 0);
        } else if (rangeType == DateRangeCalendarManager.CalendarRangeType.LAST_DATE) {
            date.set(Calendar.HOUR_OF_DAY, 23);
            date.set(Calendar.MINUTE, 59);
            date.set(Calendar.SECOND, 59);
            date.set(Calendar.MILLISECOND, 999);
        } else {
            date.set(Calendar.HOUR_OF_DAY, 0);
            date.set(Calendar.MINUTE, 0);
            date.set(Calendar.SECOND, 0);
            date.set(Calendar.MILLISECOND, 0);
        }
    }

    /**
     * To print calendar date.
     *
     * @param calendar date
     * @return Date string
     */
    static String printDate(@Nullable final Calendar calendar) {
        return calendar != null ? calendar.getTime().toString() : "null";
    }
}