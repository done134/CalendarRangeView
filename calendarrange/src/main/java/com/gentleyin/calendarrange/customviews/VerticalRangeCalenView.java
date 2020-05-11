package com.gentleyin.calendarrange.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gentleyin.calendarrange.R;
import com.gentleyin.calendarrange.models.CalendarStyleAttrImpl;
import com.gentleyin.calendarrange.models.CalendarStyleAttributes;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by YinZhendong
 * on 2020/5/10.
 * 说明：垂直分布的日历选择控件
 */
public class VerticalRangeCalenView extends LinearLayout implements DateRangeCalendarViewApi {

    private final List<Calendar> monthDataList = new ArrayList<>();
    private VerticalCalendarAdapter calendarAdapter;
    private Locale locale;
    private RecyclerView rvCalendar;
    private CalendarStyleAttributes calendarStyleAttr;
    private CalendarListener mCalendarListener;

    private final static int TOTAL_ALLOWED_MONTHS = 30;

    public VerticalRangeCalenView(final Context context) {
        super(context);
        initViews(context, null);
    }

    public VerticalRangeCalenView(final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
        initViews(context, attrs);
    }

    public VerticalRangeCalenView(final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context, attrs);
    }

    private void initViews(final Context context, final AttributeSet attrs) {
        locale = context.getResources().getConfiguration().locale;
        calendarStyleAttr = new CalendarStyleAttrImpl(context, attrs);
        final LayoutInflater layoutInflater = LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.layout_calendar_container_vertical, this, true);
//        final RelativeLayout rlHeaderCalendar = findViewById(R.id.rlHeaderCalendar);
//        rlHeaderCalendar.setBackground(calendarStyleAttr.getHeaderBg());
//        tvYearTitle = findViewById(R.id.tvYearTitle);
//        tvYearTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, calendarStyleAttr.getTextSizeTitle());
        rvCalendar = findViewById(R.id.rv_calendar);

        monthDataList.clear();
        final Calendar today = (Calendar) Calendar.getInstance().clone();
        today.add(Calendar.MONTH, -TOTAL_ALLOWED_MONTHS);

        for (int i = 0; i < TOTAL_ALLOWED_MONTHS * 2; i++) {
            monthDataList.add((Calendar) today.clone());
            today.add(Calendar.MONTH, 1);
        }
        calendarAdapter = new VerticalCalendarAdapter(context, monthDataList, calendarStyleAttr);
        rvCalendar.setLayoutManager(new LinearLayoutManager(context));

        // 也可以直接写成：
//        rcvVertical.setLayoutManager(new LinearLayoutManager(this));

        rvCalendar.setHasFixedSize(true);
        rvCalendar.setAdapter(calendarAdapter);

        rvCalendar.setAdapter(calendarAdapter);
//        rvCalendar.setOffscreenPageLimit(0);
        rvCalendar.scrollToPosition(TOTAL_ALLOWED_MONTHS);
        getMonthTitle(TOTAL_ALLOWED_MONTHS);
//        setListeners();
    }


    /**
     * To set calendar year title
     *
     * @param position data list position for getting date
     */
    private String getMonthTitle(final int position) {
        final Calendar currentCalendarMonth = monthDataList.get(position);
        String dateText = new DateFormatSymbols(locale).getMonths()[currentCalendarMonth.get(Calendar.MONTH)];
        dateText = dateText.substring(0, 1).toUpperCase() + dateText.subSequence(1, dateText.length());

        return currentCalendarMonth.get(Calendar.YEAR)+dateText;

    }

    /**
     * To set calendar listener
     *
     * @param calendarListener Listener
     */
    @Override
    public void setCalendarListener(@NonNull final CalendarListener calendarListener) {
        mCalendarListener = calendarListener;
        calendarAdapter.setCalendarListener(mCalendarListener);
    }

    /**
     * To apply custom fonts to all the text views
     *
     * @param fonts - Typeface that you want to apply
     */
    @Override
    public void setFonts(@NonNull final Typeface fonts) {
//        tvYearTitle.setTypeface(fonts);
//        calendarStyleAttr.setFonts(fonts);
//        calendarAdapter.invalidateCalendar();
    }

    /**
     * To remove all selection and redraw current calendar
     */
    @Override
    public void resetAllSelectedViews() {
        calendarAdapter.resetAllSelectedViews();
    }

    /**
     * To set week offset. To start week from any of the day. Default is 0 (Sunday).
     *
     * @param offset 0-Sun, 1-Mon, 2-Tue, 3-Wed, 4-Thu, 5-Fri, 6-Sat
     */
    @Override
    public void setWeekOffset(final int offset) {
        calendarStyleAttr.setWeekOffset(offset);
        calendarAdapter.invalidateCalendar();
    }

    /**
     * To set left navigation ImageView drawable
     */
    @Override
    public void setNavLeftImage(@NonNull final Drawable leftDrawable) {
//        imgVNavLeft.setImageDrawable(leftDrawable);
    }

    /**
     * To set right navigation ImageView drawable
     */
    @Override
    public void setNavRightImage(@NonNull final Drawable rightDrawable) {
//        imgVNavRight.setImageDrawable(rightDrawable);
    }

    /**
     * Sets start and end date.<br>
     * <B>Note:</B><br>
     * You can not set null start date with valid end date.<br>
     * You can not set end date before start date.<br>
     * If you are setting custom month range than do not call this before calling (@method setVisibleMonthRange).<br>
     *
     * @param startDate Start date
     * @param endDate   End date
     */
    @Override
    public void setSelectedDateRange(@Nullable final Calendar startDate, @Nullable final Calendar endDate) {
        if (startDate == null && endDate != null) {
            throw new IllegalArgumentException("Start date can not be null if you are setting end date.");
        } else if (endDate != null && endDate.before(startDate)) {
            throw new IllegalArgumentException("Start date can not be after end date.");
        }
        calendarAdapter.setSelectedDate(startDate, endDate);
    }

    /**
     * To get start date.
     */
    @NonNull
    @Override
    public Calendar getStartDate() {
        return calendarAdapter.getMinSelectedDate();
    }

    /**
     * To get end date.
     */
    @NonNull
    @Override
    public Calendar getEndDate() {
        return calendarAdapter.getMaxSelectedDate();
    }

    /**
     * To set editable mode. Default value will be true.
     *
     * @param isEditable true if you want user to select date range else false
     */
    @Override
    public void setEditable(final boolean isEditable) {
        calendarAdapter.setEditable(isEditable);
    }

    /**
     * To get editable mode.
     */
    @Override
    public boolean isEditable() {
        return calendarAdapter.isEditable();
    }

    /**
     * To provide month range to be shown to user. If start month is greater than end month than it will give {@link IllegalArgumentException}.<br>
     * By default it will also make selectable date range as per visible month's dates. If you want to customize the selectable date range then
     * use {@link #setSelectableDateRange(Calendar, Calendar)}.<br><br>
     * <b>Note:</b> Do not call this method after calling date selection method {@link #setSelectableDateRange(Calendar, Calendar)}
     * / {@link #setSelectedDateRange(Calendar, Calendar)} as it will reset date selection.
     *
     * @param startMonth Start month of the calendar
     * @param endMonth   End month of the calendar
     */
    @Override
    public void setVisibleMonthRange(@NonNull final Calendar startMonth, @NonNull final Calendar endMonth) {

        final Calendar startMonthDate = (Calendar) startMonth.clone();
        final Calendar endMonthDate = (Calendar) endMonth.clone();

        startMonthDate.set(Calendar.DATE, 1);
        CalendarRangeUtils.resetTime(startMonthDate, DateRangeCalendarManager.CalendarRangeType.START_DATE);

        endMonthDate.set(Calendar.DATE, 1);
        CalendarRangeUtils.resetTime(endMonthDate, DateRangeCalendarManager.CalendarRangeType.LAST_DATE);

        if (startMonthDate.after(endMonthDate)) {
            throw new IllegalArgumentException("Start month(" + startMonthDate.getTime().toString() + ") can not be later than end month(" + endMonth.getTime().toString() + ").");
        }
        monthDataList.clear();

        while (!isDateSame(startMonthDate, endMonthDate)) {
            monthDataList.add((Calendar) startMonthDate.clone());
            startMonthDate.add(Calendar.MONTH, 1);
        }
        monthDataList.add((Calendar) startMonthDate.clone());

        calendarAdapter = new VerticalCalendarAdapter(getContext(), monthDataList, calendarStyleAttr);
        rvCalendar.setAdapter(calendarAdapter);
//        rvCalendar.setOffscreenPageLimit(0);
//        rvCalendar.setCurrentItem(0);
//        setCalendarYearTitle(0);
//        setNavigationHeader(0);
        calendarAdapter.setCalendarListener(mCalendarListener);
    }

    /**
     * To set current visible month.
     *
     * @param calendar Month to be set as current
     */
    @Override
    public void setCurrentMonth(@NonNull final Calendar calendar) {
        for (int i = 0; i < monthDataList.size(); i++) {
            final Calendar month = monthDataList.get(i);
            if (month.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)) {
                if (month.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)) {
                    rvCalendar.scrollToPosition(i);
                    break;
                }
            }
        }
    }

    @Override
    public void setSelectableDateRange(@NonNull final Calendar startDate, @NonNull final Calendar endDate) {
        if (endDate.before(startDate)) {
            throw new IllegalArgumentException("Start date(" + startDate.getTime().toString() + ") can not be after end date(" + endDate.getTime().toString() + ").");
        }
        calendarAdapter.setSelectableDateRange(startDate, endDate);
    }

    private boolean isDateSame(@NonNull final Calendar one, @NonNull final Calendar second) {
        return one.get(Calendar.YEAR) == second.get(Calendar.YEAR)
                && one.get(Calendar.MONTH) == second.get(Calendar.MONTH)
                && one.get(Calendar.DATE) == second.get(Calendar.DATE);
    }
}
