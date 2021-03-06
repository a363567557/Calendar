package cn.cn.calendar.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import cn.cn.calendar.R;
import cn.cn.calendar.controller.CalendarController;
import cn.cn.calendar.view.CalendarTextView;

/**
 * author weiwei on 2016/11/17 0017 16:48.
 */
public class GridviewAdapter extends BaseAdapter {
    private Fragment mFragment;
    private Calendar monthFristDayCalendar;
    private Calendar preMonthDayCalendar;
    private int dayOfWeek, lastDay, preLastDay;
    private long oneDayTime = 24L * 60 * 60;
    private long monthFristDayTime = 0;
    private long[] times = {1483891200,1484064000};
    private CalendarController mCalendarController;
    private final int CALENDAR_LENTH = 42;

    public GridviewAdapter(Fragment context, CalendarController calendarController) {
        mFragment = context;
        mCalendarController = calendarController;
    }

    public void setDate(Calendar calendar) {
        monthFristDayCalendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        monthFristDayCalendar.setTimeInMillis(calendar.getTimeInMillis());
        //获取1号0:0:0
        monthFristDayCalendar.set(Calendar.HOUR_OF_DAY, 0);
        monthFristDayCalendar.set(Calendar.MINUTE, 0);
        monthFristDayCalendar.set(Calendar.SECOND, 0);
        monthFristDayCalendar.set(Calendar.MILLISECOND, 0);
        monthFristDayTime = monthFristDayCalendar.getTimeInMillis() / 1000;

        preMonthDayCalendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        preMonthDayCalendar.setTimeInMillis(calendar.getTimeInMillis());
        preMonthDayCalendar.add(Calendar.MONTH, -1);
        dayOfWeek = monthFristDayCalendar.get(Calendar.DAY_OF_WEEK);
        dayOfWeek -= 1;
        if (dayOfWeek == 0) {
            dayOfWeek += 7;
        }
        lastDay = monthFristDayCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        preLastDay = preMonthDayCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        if (mCalendarController.isToday
                && monthFristDayCalendar.get(Calendar.MONTH) == mCalendarController.getToadyCalendar().get(Calendar.MONTH)
                && monthFristDayCalendar.get(Calendar.YEAR) == mCalendarController.getToadyCalendar().get(Calendar.YEAR)) {
            mCalendarController.getDetailList(mCalendarController.getToadyCalendar().getTime().getTime() / 1000);
        }
        notifyDataSetChanged();
        getCalander(monthFristDayCalendar);
    }

    public void getCalander(Calendar calendar) {
        calendar.setTime(calendar.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    @Override
    public int getCount() {
        return CALENDAR_LENTH;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder;
        if (convertView == null) {
            convertView = View.inflate(mFragment.getContext(), R.layout.gridview_item, null);
            viewholder = new ViewHolder(convertView);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }

        if (lastDay > 0) {
            if (position < dayOfWeek - 1) {
                viewholder.setDate("" + (preLastDay - (dayOfWeek - position - 2)));
                viewholder.dateTv.setBackgroundColor(ContextCompat.getColor(mFragment.getContext(), R.color.not_current_month));
                viewholder.dateTv.setTextColor(ContextCompat.getColor(mFragment.getContext(), R.color.not_current_month_textcolor));
                viewholder.dateTv.setCurrentMonth(false);
                viewholder.pointTv.setVisibility(View.INVISIBLE);
            } else if (position >= lastDay + dayOfWeek - 1) {
                viewholder.setDate("" + (position - lastDay - dayOfWeek + 2));
                viewholder.dateTv.setBackgroundColor(ContextCompat.getColor(mFragment.getContext(), R.color.not_current_month));
                viewholder.dateTv.setTextColor(ContextCompat.getColor(mFragment.getContext(), R.color.not_current_month_textcolor));
                viewholder.dateTv.setCurrentMonth(false);
                viewholder.pointTv.setVisibility(View.INVISIBLE);
            } else {
                viewholder.setDate("" + (position - dayOfWeek + 2));
                viewholder.dateTv.setBackgroundColor(ContextCompat.getColor(mFragment.getContext(), R.color.white));
                viewholder.dateTv.setTextColor(ContextCompat.getColor(mFragment.getContext(), R.color.current_month_textcolor));
                viewholder.dateTv.setCurrentMonth(true);
                int day = position - dayOfWeek + 1;
                if (checkContainDay(day, viewholder.dateTv)) {
                    viewholder.pointTv.setVisibility(View.VISIBLE);
                } else {
                    viewholder.pointTv.setVisibility(View.INVISIBLE);
                }

                if (checkToday(day)) {
                    viewholder.dateTv.setToday(true);
                } else {
                    viewholder.dateTv.setToday(false);
                }

                if (mCalendarController.isToday) {
                    if (mCalendarController.getSelectDay() == position - dayOfWeek + 2 && monthFristDayCalendar.get(Calendar.MONTH) == mCalendarController.getToadyCalendar().get(Calendar.MONTH) && monthFristDayCalendar.get(Calendar.YEAR) == mCalendarController.getToadyCalendar().get(Calendar.YEAR)) {
                        viewholder.dateTv.setSelected(true);
                    } else {
                        viewholder.dateTv.setSelected(false);
                    }
                }
            }
        }
        convertView.setTag(viewholder);
        return convertView;
    }

    //判断times中是否在当月某天中
    private boolean checkContainDay(int day, CalendarTextView view) {
        view.setDate((long) 0);
        if (times != null) {
            for (long time : times) {
                if (time >= (monthFristDayTime + day * oneDayTime) && time < (monthFristDayTime + (day + 1) * oneDayTime)) {
                    view.setDate(time);
                    return true;
                }
            }
        }
        return false;
    }

    //判断是否今天日期
    private boolean checkToday(int day) {
        return (mCalendarController.todayTime() >= monthFristDayTime + day * oneDayTime && mCalendarController.todayTime() < monthFristDayTime + (day + 1) * oneDayTime);
    }

    class ViewHolder {
        public CalendarTextView dateTv;
        public TextView pointTv;

        public ViewHolder(View view) {
            dateTv = (CalendarTextView) view.findViewById(R.id.date);
            pointTv = (TextView) view.findViewById(R.id.point);
        }

        public void setDate(String date) {
            dateTv.setText(date);
        }
    }

    public interface GridviewAdapterOnItemClickListenerCallBack {
        void setGridviewAdapterOnItemClickListenerCallBack(Object o);
    }

    public interface GridviewAdapterTimeCallBack {
        void onGridviewAdapterTimeCallBack(Object o);
    }
}
