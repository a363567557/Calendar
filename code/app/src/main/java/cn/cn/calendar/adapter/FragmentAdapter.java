package cn.cn.calendar.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;
import cn.cn.calendar.controller.CalendarController;
import cn.cn.calendar.ui.CalendarFragment;

/**
 * author weiwei on 2016/11/17 0017 16:24.
 */
public class FragmentAdapter extends FragmentPagerAdapter {
    private CalendarFragment[] fragments;
    private int mCurrentIndex = 0;
    private CalendarController mCalendarController;
    private int mDefaultFragmetCount = 5;

    public FragmentAdapter(FragmentManager fm, CalendarController calendarController) {
        super(fm);
        mCalendarController = calendarController;
        init();
    }

    public FragmentAdapter(FragmentManager fm, CalendarController calendarController, int fragmentCount) {
        super(fm);
        mCalendarController = calendarController;
        mDefaultFragmetCount = fragmentCount;
        init();
    }

    private void init() {
        fragments = new CalendarFragment[mDefaultFragmetCount];
        for (int i = 0; i < mDefaultFragmetCount; i++) {
            fragments[i] = new CalendarFragment(mCalendarController);
        }
    }

    @Override
    public int getCount() {
        return Short.MAX_VALUE;
    }

    //得到每个item
    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //处理position。让position落在[0,fragmentList.size)中
        position = position % fragments.length;
        //调用原来的方法
        return super.instantiateItem(container, position);
    }

    private int lastPosition = -1;//fragment 是否初始化标记

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        mCalendarController.onFragmentPageChangeCallBack();
        mCurrentIndex = position;
        int fragmentsPosition = mCurrentIndex % fragments.length;
        CalendarFragment preFragment;
        CalendarFragment nextFragment;
        if (fragmentsPosition == 0) {
            preFragment = fragments[mDefaultFragmetCount - 1];
            nextFragment = fragments[fragmentsPosition + 1];
        } else if (fragmentsPosition == mDefaultFragmetCount - 1) {
            preFragment = fragments[fragmentsPosition - 1];
            nextFragment = fragments[0];
        } else {
            preFragment = fragments[fragmentsPosition - 1];
            nextFragment = fragments[fragmentsPosition + 1];
        }
        CalendarFragment currentFragment = fragments[fragmentsPosition];

        if (lastPosition != -1) {
            if (lastPosition < position) {
                currentFragment.onNextMonth();
            } else if (lastPosition > position) {
                currentFragment.onPreMonth();
            } else {
                currentFragment.onCurrentMonth();
            }
        } else {
            currentFragment.onCurrentMonth();
        }
        lastPosition = position;
        preFragment.getPreMonth();
        nextFragment.getNextMonth();
        super.setPrimaryItem(container, position, object);
    }

    public int currentIndext() {
        return mCurrentIndex;
    }

    public void update() {
        CalendarFragment calendarFragment = fragments[mCurrentIndex % fragments.length];
        calendarFragment.onCurrentMonth();
    }

    public interface FragmentPageChangeCallBack {
        void onFragmentPageChangeCallBack();
    }

}