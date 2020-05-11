package com.gentleyin.calendarrange.customviews;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gentleyin.calendarrange.R;
import com.gentleyin.calendarrange.models.CalendarStyleAttributes;

import java.util.Calendar;
import java.util.List;

/**
 * Created by YinZhendong
 * on 2020/5/10.
 * 说明：
 */
public class VerticalCalendarAdapter extends RecyclerView.Adapter<VerticalCalendarAdapter.VerticalViewHolder> {

    private final Context mContext;
    private final List<Calendar> dataList;
//    private final String monthTitle;
    private final CalendarStyleAttributes calendarStyleAttr;
    private CalendarListener calendarListener;
    private final DateRangeCalendarManager dateRangeCalendarManager;
    private final Handler mHandler;
    private String[] monthTitle = {"1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"};

    public VerticalCalendarAdapter(Context mContext, List<Calendar> dataList, CalendarStyleAttributes calendarStyleAttr) {
        this.mContext = mContext;
        this.dataList = dataList;
        this.calendarStyleAttr = calendarStyleAttr;

//        this.monthTitle = monthTitle;
        // Get start month and set start date of that month
        final Calendar startSelectableDate = (Calendar) dataList.get(0).clone();
        startSelectableDate.set(Calendar.DAY_OF_MONTH, 1);
        // Get end month and set end date of that month
        final Calendar endSelectableDate = (Calendar) dataList.get(dataList.size() - 1).clone();
        endSelectableDate.set(Calendar.DAY_OF_MONTH, endSelectableDate.getActualMaximum(Calendar.DAY_OF_MONTH));
        dateRangeCalendarManager = new DateRangeCalendarManager(startSelectableDate, endSelectableDate);
        mHandler = new Handler();
    }

    @NonNull
    @Override
    public VerticalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_vertical_pager_month, parent, false);
        return new VerticalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VerticalViewHolder holder, int position) {
        final Calendar modelObject = dataList.get(position);
        holder.dateRangeMonthView.drawCalendarForMonth(calendarStyleAttr, getCurrentMonth(modelObject), dateRangeCalendarManager);
        holder.dateRangeMonthView.setCalendarListener(calendarAdapterListener);
        holder.tvMonthTitle.setText(getMonthTitle(modelObject));

    }

    /**
     * To clone calendar obj and get current month calendar starting from 1st date.
     *
     * @param calendar - Calendar
     * @return - Modified calendar obj of month of 1st date.
     */
    private Calendar getCurrentMonth(final @NonNull Calendar calendar) {
        final Calendar current = (Calendar) calendar.clone();
        current.set(Calendar.DAY_OF_MONTH, 1);
        return current;
    }

    /**
     * To set calendar year title
     */
    private String getMonthTitle(final Calendar currentCalendarMonth) {
//        final Calendar currentCalendarMonth = monthDataList.get(position);
        String dateText = monthTitle[currentCalendarMonth.get(Calendar.MONTH)];
        dateText = dateText.substring(0, 1).toUpperCase() + dateText.subSequence(1, dateText.length());

        return currentCalendarMonth.get(Calendar.YEAR)+"年"+dateText;

    }

    private final CalendarListener calendarAdapterListener = new CalendarListener() {
        @Override
        public void onFirstDateSelected(@NonNull final Calendar startDate) {

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    notifyDataSetChanged();
                }
            }, 50);

            if (calendarListener != null) {
                calendarListener.onFirstDateSelected(startDate);
            }
        }

        @Override
        public void onDateRangeSelected(@NonNull final Calendar startDate, @NonNull final Calendar endDate) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    notifyDataSetChanged();
                }
            }, 50);
            if (calendarListener != null) {
                calendarListener.onDateRangeSelected(startDate, endDate);
            }
        }
    };

    void setCalendarListener(final CalendarListener calendarListener) {
        this.calendarListener = calendarListener;
    }

    /**
     * To redraw calendar.
     */
    void invalidateCalendar() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        }, 50);
    }

    /**
     * To remove all selection and redraw current calendar
     */
    void resetAllSelectedViews() {
        dateRangeCalendarManager.setMinSelectedDate(null);
        dateRangeCalendarManager.setMaxSelectedDate(null);
        notifyDataSetChanged();
    }

    void setSelectedDate(final Calendar minSelectedDate, final Calendar maxSelectedDate) {
        dateRangeCalendarManager.setMinSelectedDate(minSelectedDate);
        dateRangeCalendarManager.setMaxSelectedDate(maxSelectedDate);
        notifyDataSetChanged();
    }

    Calendar getMinSelectedDate() {
        return dateRangeCalendarManager.getMinSelectedDate();
    }

    Calendar getMaxSelectedDate() {
        return dateRangeCalendarManager.getMaxSelectedDate();
    }

    /**
     * To set editable mode. Set true if you want user to select date range else false. Default value will be true.
     */
    void setEditable(final boolean isEditable) {
        calendarStyleAttr.setEditable(isEditable);
        notifyDataSetChanged();
    }

    /**
     * To get editable mode.
     */
    boolean isEditable() {
        return calendarStyleAttr.isEditable();
    }

    void setSelectableDateRange(@NonNull final Calendar startDate, @NonNull final Calendar endDate) {
        dateRangeCalendarManager.setSelectableDateRange(startDate, endDate);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    class VerticalViewHolder extends RecyclerView.ViewHolder {

        VerticalRangeMonthView dateRangeMonthView;
        TextView tvMonthTitle;

        VerticalViewHolder(View itemView) {
            super(itemView);
            dateRangeMonthView = itemView.findViewById(R.id.cvEventCalendarView);
            tvMonthTitle = itemView.findViewById(R.id.tv_month_title);
        }
    }
}
