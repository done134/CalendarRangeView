package com.gentleyin.calendarrange.models;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Archit Shah on 8/13/2017.
 */

public class DayContainer {

    public RelativeLayout rootView;
    public TextView tvDate,tvDaySub;

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());

    public DayContainer(final RelativeLayout rootView) {
        this.rootView = rootView;
        tvDate = (TextView) rootView.getChildAt(0);
        tvDaySub = (TextView) rootView.getChildAt(1);
    }

    public static int GetContainerKey(final Calendar cal) {

        final String str = simpleDateFormat.format(cal.getTime());
        return Integer.valueOf(str);
    }
}
