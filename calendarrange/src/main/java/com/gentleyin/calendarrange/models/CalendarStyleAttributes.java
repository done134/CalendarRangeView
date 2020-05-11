package com.gentleyin.calendarrange.models;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

public interface CalendarStyleAttributes {

    Typeface getFonts();

    void setFonts(@NonNull Typeface fonts);

    int getTitleColor();

    @NonNull
    Drawable getHeaderBg();

    int getWeekColor();

    int getRangeStripColor();

    int getSelectedDateCircleColor();

    int getSelectedDateColor();

    int getDefaultDateColor();

    int getDisableDateColor();

    int getRangeDateColor();

    float getTextSizeTitle();

    float getTextSizeWeek();

    float getTextSizeDate();

    boolean isShouldEnabledTime();

    void setWeekOffset(int weekOffset);

    int getWeekOffset();

    boolean isEditable();

    void setEditable(boolean editable);
}
