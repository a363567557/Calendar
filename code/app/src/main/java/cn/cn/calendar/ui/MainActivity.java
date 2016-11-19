package cn.cn.calendar.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import java.util.Calendar;
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
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), calendarController);
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
                if (null != o) {
                    adapter.setData(((long) o) + "----------");
                } else {
                    adapter.setData(null);
                }
            }
        });
        findViewById(R.id.pre).setOnClickListener(this);
        findViewById(R.id.next).setOnClickListener(this);
        findViewById(R.id.today).setOnClickListener(this);
    }

    //更新标题数据
    private void updateCalendarTitle() {
        text.setText(calendarController.getCurrentSelectCalendar().get(Calendar.YEAR) + "--" +
                (calendarController.getCurrentSelectCalendar().get(Calendar.MONTH) + 1));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.pre == id) {
            viewpager.setCurrentItem(fragmentAdapter.currentIndext() - 1);
        } else if (R.id.next == id) {
            viewpager.setCurrentItem(fragmentAdapter.currentIndext() + 1);
        } else if (R.id.today == id) {
            calendarController.onChangeToday();
            fragmentAdapter.notifyDataSetChanged();
            fragmentAdapter.update();
            updateCalendarTitle();
        }
    }
}
