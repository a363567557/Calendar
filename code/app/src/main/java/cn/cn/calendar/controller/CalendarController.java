package cn.cn.calendar.controller;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import cn.cn.calendar.adapter.FragmentAdapter;
import cn.cn.calendar.adapter.GridviewAdapter;

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

        onChangeMonth();
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


    /**
     * 获取当前显示月份的下一个月份Calendar
     *
     * @return Calendar
     */
    public Calendar getNextMonth() {
        return getNextCalendar();
    }


    /**
     * 获取当前显示月份的上一个月份Calendar
     *
     * @return Calendar
     */
    public Calendar getPreMonth() {
        return getPreCalendar();
    }


    /**
     * 获取当前显示月份Calendar
     *
     * @return Calendar
     */
    public Calendar onCurrentMonth() {
        return getCurrentSelectCalendar();
    }


    /**
     * 切换到上一个月
     *
     * @return
     */
    public Calendar onPreMonth() {
        getCurrentSelectCalendar().add(Calendar.MONTH, -1);
        onChangeMonth();
        return getCurrentSelectCalendar();
    }

    /**
     * 切换到下一个月
     *
     * @return
     */
    public Calendar onNextMonth() {
        getCurrentSelectCalendar().add(Calendar.MONTH, 1);
        onChangeMonth();
        return getCurrentSelectCalendar();
    }

    //切换月份
    private void onChangeMonth() {
        getPreCalendar().setTimeInMillis(getCurrentSelectCalendar().getTimeInMillis());
        getPreCalendar().add(Calendar.MONTH, -1);
        getNextCalendar().setTimeInMillis(getCurrentSelectCalendar().getTimeInMillis());
        getNextCalendar().add(Calendar.MONTH, 1);
    }

    //切换到今天
    public void onChangeToday() {
        currentSelectCalendar.setTimeInMillis(toadyCalendar.getTimeInMillis());
        currentSelectCalendar.set(Calendar.DAY_OF_MONTH, 1);
        onChangeMonth();
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
}
