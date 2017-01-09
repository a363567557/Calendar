package cn.cn.calendar.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import cn.cn.calendar.R;
import cn.cn.calendar.adapter.FragmentAdapter;
import cn.cn.calendar.adapter.GridviewAdapter;
import cn.cn.calendar.adapter.ListviewAdapter;
import cn.cn.calendar.controller.CalendarController;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private FragmentAdapter fragmentAdapter;
    private ViewPager viewpager;
    private ListView listView;
    private CalendarController calendarController;
    private TextView text;
    private ListviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calendarController = new CalendarController();
        setContentView(R.layout.activity_main);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        text = (TextView) findViewById(R.id.text);
        listView = (ListView) findViewById(R.id.list);
        initData();
        initListener();
        updateCalendarTitle();
    }

    private void initData() {
        adapter = new ListviewAdapter(this);
        listView.setAdapter(adapter);
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), calendarController,viewpager);
        calendarController.setSelectDay(calendarController.getToadyCalendar().get(Calendar.DAY_OF_MONTH));
        calendarController.onChangeToday();
        calendarController.setFragmentAdapter(fragmentAdapter);
        viewpager.setAdapter(fragmentAdapter);
        viewpager.setCurrentItem(Short.MAX_VALUE / 2);
    }

    private void initListener() {
        calendarController.setFragmentPageChangeCallBack(new FragmentAdapter.FragmentPageChangeCallBack() {
            @Override
            public void onFragmentPageChangeCallBack() {
                updateCalendarTitle();
            }
        });
        calendarController.setGridviewAdapterOnItemClickListenerCallBack(new GridviewAdapter.GridviewAdapterOnItemClickListenerCallBack() {
            @Override
            public void setGridviewAdapterOnItemClickListenerCallBack(Object o) {
//                if (null != o) {
                    adapter.setData(((long) o) + "----------");
//                } else {
//                    adapter.setData(null);
//                }
            }
        });
        findViewById(R.id.pre).setOnClickListener(this);
        findViewById(R.id.next).setOnClickListener(this);
        findViewById(R.id.today).setOnClickListener(this);

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                updateCalendarTitle();
                GridView gridview = calendarController.getGridview();
                BaseAdapter adapter = (BaseAdapter) gridview.getAdapter();
                adapter.notifyDataSetChanged();
//                ((BaseAdapter)calendarController.getGridview().getAdapter()).notifyDataSetChanged();

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    //更新标题数据
    private void updateCalendarTitle() {
        int month = getMonth(viewpager.getCurrentItem()).get(Calendar.MONTH) + 1;
        if (month < 10) {
            text.setText(calendarController.getCurrentSelectCalendar().get(Calendar.YEAR) + ".0" + month);
        } else {
            text.setText(calendarController.getCurrentSelectCalendar().get(Calendar.YEAR) + "." + month);
        }
    }
    private int aaa = Short.MAX_VALUE / 2;
    public Calendar getMonth(int index) {
        int i = index - aaa;
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MONTH, i);
        return calendar;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.pre == id) {
            viewpager.setCurrentItem(viewpager.getCurrentItem() - 1);
        } else if (R.id.next == id) {
            viewpager.setCurrentItem(viewpager.getCurrentItem() + 1);
        } else if (R.id.today == id) {
            calendarController.onChangeToday();
            updateCalendarTitle();
            calendarController.setSelectDay(calendarController.getToadyCalendar().get(Calendar.DAY_OF_MONTH));
            viewpager.setCurrentItem(Short.MAX_VALUE / 2, false);
            ((BaseAdapter)calendarController.getGridview().getAdapter()).notifyDataSetChanged();
            calendarController.getDetailList(calendarController.getToadyCalendar().getTime().getTime() / 1000);
        }
    }
}
