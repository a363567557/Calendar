package cn.cn.calendar.controller;

import android.view.View;
import android.widget.GridView;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import cn.cn.calendar.adapter.FragmentAdapter;
import cn.cn.calendar.adapter.GridviewAdapter;
import cn.cn.calendar.ui.CalendarFragment;

/**
 * author weiwei on 2016/11/17 0017 21:48.
 */
public class CalendarController {
    private Calendar toadyCalendar;
    private Calendar monthFristDayCalendar;
    private Calendar currentSelectCalendar;
    private Calendar preSelectCalendar;
    private Calendar nextSelectCalendar;
    private GridviewAdapter.GridviewAdapterOnItemClickListenerCallBack mGridviewAdapterOnItemClickListenerCallBack;
    private FragmentAdapter.FragmentPageChangeCallBack mFragmentPageChangeCallBack;
    private GridviewAdapter.GridviewAdapterTimeCallBack mGridviewAdapterTimeCallBack;
    private FragmentAdapter fragmentAdapter;
    private int selectDay = -1;
    private View gotoToday;

    public CalendarController() {
        initCalendar();
    }

    //初始化当前日历数据
    private void initCalendar() {
        toadyCalendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        monthFristDayCalendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        currentSelectCalendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        preSelectCalendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        nextSelectCalendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());

        //当天日期的0:0:0
        toadyCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toadyCalendar.set(Calendar.MINUTE, 0);
        toadyCalendar.set(Calendar.SECOND, 0);
        toadyCalendar.set(Calendar.MILLISECOND, 0);


        currentSelectCalendar.setTimeInMillis(toadyCalendar.getTimeInMillis());
        currentSelectCalendar.set(Calendar.DAY_OF_MONTH, 1);
        monthFristDayCalendar.setTimeInMillis(toadyCalendar.getTimeInMillis());
        monthFristDayCalendar.set(Calendar.DAY_OF_MONTH, 1);

    }

    public void getDetailList(final long dateClicked) {
        if (dateClicked > 0) {
            // TODO: 2017/1/9 0009 根据日期对应的时间获取数据
        }
    }

    /**
     * 获取当前显示月份的秒数
     *
     * @return Calendar
     */
    public long todayTime() {
        return toadyCalendar.getTimeInMillis() / 1000;
    }

    /**
     * 获取当前显示月份的Calendar
     *
     * @return Calendar
     */
    public Calendar getCurrentSelectCalendar() {
        return currentSelectCalendar;
    }

    private Calendar getPreCalendar() {
        return preSelectCalendar;
    }

    private Calendar getNextCalendar() {
        return nextSelectCalendar;
    }

    public void setGotoTodayView(View gotoToday){
        this.gotoToday = gotoToday;
    }

    public boolean isToday = false;

    //切换到今天
    public void onChangeToday() {
        isToday = true;
        currentSelectCalendar.setTimeInMillis(toadyCalendar.getTimeInMillis());
        currentSelectCalendar.set(Calendar.DAY_OF_MONTH, 1);
    }

    public void setSelectDay(int day) {
        selectDay = day;
    }

    public int getSelectDay() {
        return selectDay;
    }

    public void setFragmentAdapter(FragmentAdapter fragmentAdapter) {
        this.fragmentAdapter = fragmentAdapter;
    }


    public CalendarFragment getCurrentFragment() {
        if(null == fragmentAdapter){
            return null;
        }
        return fragmentAdapter.getCurrentFragment();
    }

    public GridView getGridview() {
        if(null != getCurrentFragment()){
            return getCurrentFragment().getGridview();
        }
        return null;
    }

    public boolean checkTodayBtn() {
        if (isToday) {
            this.gotoToday.setEnabled(false);
            return false;
        } else {
            this.gotoToday.setEnabled(true);
            return true;
        }
    }

    public Calendar getToadyCalendar(){
        return toadyCalendar;
    }

    public void setGridviewAdapterOnItemClickListenerCallBack(GridviewAdapter.GridviewAdapterOnItemClickListenerCallBack callBack) {
        mGridviewAdapterOnItemClickListenerCallBack = callBack;
    }

    public void onGridviewAdapterOnItemClickListenerCallBack(Object o) {
        if (null != mGridviewAdapterOnItemClickListenerCallBack) {
            mGridviewAdapterOnItemClickListenerCallBack.setGridviewAdapterOnItemClickListenerCallBack(o);
        }
    }

    public void setFragmentPageChangeCallBack(FragmentAdapter.FragmentPageChangeCallBack callBack) {
        mFragmentPageChangeCallBack = callBack;
    }

    public void onFragmentPageChangeCallBack() {
        if (null != mFragmentPageChangeCallBack) {
            mFragmentPageChangeCallBack.onFragmentPageChangeCallBack();
        }
    }

    public void setGridviewAdapterTimeCallBack(GridviewAdapter.GridviewAdapterTimeCallBack callBack) {
        mGridviewAdapterTimeCallBack = callBack;
    }

    public void onGridviewAdapterTimeCallBack(Object o) {
        if (null != mGridviewAdapterTimeCallBack) {
            mGridviewAdapterTimeCallBack.onGridviewAdapterTimeCallBack(o);
        }
    }

    private int aaa = Short.MAX_VALUE/2;
    public Calendar getMonth(int index) {
        int i = index - aaa;
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MONTH,i);
        return calendar;
    }

    public void setCurrentSelectCalendar(Calendar calendar){
        currentSelectCalendar = calendar;
    }
}
