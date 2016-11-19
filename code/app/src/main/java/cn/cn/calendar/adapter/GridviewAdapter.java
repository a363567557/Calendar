package cn.cn.calendar.adapter;

import android.content.Context;
import android.graphics.Color;
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
    private Context mContext;
    private Calendar monthFristDayCalendar;
    private Calendar preMonthDayCalendar;
    private Calendar drawDayCalendar;
    int dayOfWeek, lastDay, preLastDay;
    long oneDayTime = 24L * 60 * 60;
    long monthFristDayTime = 0;
    private long[] times = new long[]{1479398400, 1479484800};
    private CalendarController mCalendarController;

    public GridviewAdapter(Context context, CalendarController calendarController) {
        mContext = context;
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
        lastDay = monthFristDayCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        preLastDay = preMonthDayCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return 42;
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
            convertView = View.inflate(mContext, R.layout.gridview_item, null);
            viewholder = new ViewHolder(convertView);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        if (position < dayOfWeek - 1) {
            viewholder.setDate("" + (preLastDay - (dayOfWeek - position - 2)));
            viewholder.dateTv.setBackgroundColor(Color.parseColor("#DBDBDB"));
            viewholder.dateTv.setTextColor(Color.parseColor("#888888"));
            viewholder.dateTv.setCurrentMonth(false);
            viewholder.pointTv.setVisibility(View.INVISIBLE);
        } else if (position >= lastDay + dayOfWeek - 1) {
            viewholder.setDate("" + (position - lastDay - dayOfWeek + 2));
            viewholder.dateTv.setBackgroundColor(Color.parseColor("#DBDBDB"));
            viewholder.dateTv.setTextColor(Color.parseColor("#888888"));
            viewholder.dateTv.setCurrentMonth(false);
            viewholder.pointTv.setVisibility(View.INVISIBLE);
        } else {
            viewholder.setDate("" + (position - dayOfWeek + 2));
            viewholder.dateTv.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
//            viewholder.dateTv.setBackground(mCalendarController.getNormolDrawable());
//            viewholder.dateTv.setBackgroundResource(mCalendarController.getNormolDrawable());
            viewholder.dateTv.setSelected(false);
            viewholder.dateTv.setTextColor(Color.parseColor("#333333"));
            viewholder.dateTv.setCurrentMonth(true);
            int day = position - dayOfWeek + 1;
//            viewholder.dateTv.setOnClickListener(calendarOnClickListener);
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

            if (viewholder.dateTv.isToday()) {
//                viewholder.dateTv.setBackgroundColor(mCalendarController.getSelectedlBackground());
                viewholder.dateTv.setSelected(true);
//                viewholder.dateTv.setBackgroundResource(mCalendarController.getTodayDrawable());
            }

        }
        convertView.setTag(viewholder);
        return convertView;
    }

    //判断times中是否在当月某天中
    private boolean checkContainDay(int day, CalendarTextView view) {
        for (long time : times) {
            if (time >= monthFristDayTime + day * oneDayTime && time < monthFristDayTime + (day + 1) * oneDayTime) {
                System.out.println(monthFristDayTime + day * oneDayTime + "-----" + (monthFristDayTime + (day + 1) * oneDayTime));
                view.setDate(time);
                return true;
            }
        }
        view.setTag((long) 0);
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
}
