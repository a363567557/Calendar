package cn.cn.calendar.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import cn.cn.calendar.controller.CalendarController;
import cn.cn.calendar.ui.CalendarFragment;


/**
 * author weiwei on 2016/11/17 0017 16:24.
 */
public class FragmentAdapter extends FragmentStatePagerAdapter {
    private CalendarFragment[] fragments;
    private CalendarController mCalendarController;
    private static int mDefaultFragmetCount = 5;
    private ViewPager mViewPager;

    public FragmentAdapter(FragmentManager fm, CalendarController calendarController, ViewPager viewPager) {
        this(fm, calendarController, viewPager, mDefaultFragmetCount);
    }

    public FragmentAdapter(FragmentManager fm, CalendarController calendarController, ViewPager viewPager, int fragmentCount) {
        super(fm);
        mCalendarController = calendarController;
        mViewPager = viewPager;
        mDefaultFragmetCount = fragmentCount;
        init();
    }

    private void init() {
        fragments = new CalendarFragment[mDefaultFragmetCount];
        for (int i = 0; i < mDefaultFragmetCount; i++) {
            fragments[i] = new CalendarFragment();
            fragments[i].setCalendarController(mCalendarController);
        }
    }

    @Override
    public int getCount() {
        return Short.MAX_VALUE;
    }

    //得到每个item
    @Override
    public Fragment getItem(int position) {
        return fragments[position % mDefaultFragmetCount];
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //处理position。让position落在[0,fragmentList.size)中
        fragments[position % fragments.length].getMonth(position);
        for (int i = 0; i < fragments.length; i++) {
            fragments[i].cleanSelect();
        }

        //调用原来的方法
        return super.instantiateItem(container, position % fragments.length);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        int fragmentsPosition = position % fragments.length;
        if (fragmentsPosition >= 0) {
//            currentFragment = fragments[fragmentsPosition];
            mCalendarController.setCurrentSelectCalendar(fragments[fragmentsPosition].getCalendar());
//            mCalendarController.onFragmentPageChangeCallBack();
        }
        super.setPrimaryItem(container, position % mDefaultFragmetCount, object);
    }

    public interface FragmentPageChangeCallBack {
        void onFragmentPageChangeCallBack();
    }

    public CalendarFragment getCurrentFragment() {
//        return currentFragment;
        return fragments[mViewPager.getCurrentItem() % mDefaultFragmetCount];
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
//        super.destroyItem(container, position % mDefaultFragmetCount, object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position % mDefaultFragmetCount, object);
    }
}