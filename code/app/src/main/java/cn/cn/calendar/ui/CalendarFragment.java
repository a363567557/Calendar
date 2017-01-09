package cn.cn.calendar.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.Calendar;

import cn.cn.calendar.R;
import cn.cn.calendar.adapter.GridviewAdapter;
import cn.cn.calendar.controller.CalendarController;
import cn.cn.calendar.view.CalendarTextView;

/**
 * author weiwei on 2016/11/17 0017 16:20.
 */
public class CalendarFragment extends Fragment {
    private View mRootView;
    private CalendarController mCalendarController;
    private GridView gridview;
    private GridviewAdapter gridviewAdapter;
    private Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


    public void setCalendarController(CalendarController calendarController) {
        mCalendarController = calendarController;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = View.inflate(context, R.layout.fragmet_main, null);
        initView();
        initListener();
        return mRootView;
    }

    private void initView() {
        gridview = (GridView) mRootView.findViewById(R.id.gridview);
        if (null == gridviewAdapter) {
            gridviewAdapter = new GridviewAdapter(this, mCalendarController);
            gridview.setAdapter(gridviewAdapter);
        }
        gridviewAdapter.setDate(mCalendarController.getMonth(index));
    }

    private void initListener() {
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCalendarController.isToday = false;
                CalendarTextView calendarTextView = (CalendarTextView) view.findViewById(R.id.date);
                if (calendarTextView.isCurrentMonth()) {
                    //非空为本月日期
                    int childCount = gridview.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        //设置本月日期背景颜色
                        View childView = gridview.getChildAt(i);
                        CalendarTextView gridviewItem = (CalendarTextView) childView.findViewById(R.id.date);
                        gridviewItem.setSelected(false);
                    }
                    if (calendarTextView.isToday()) {
                        mCalendarController.isToday = true;
                    }
                    calendarTextView.setSelected(true);
                    mCalendarController.setSelectDay(Integer.valueOf(calendarTextView.getText().toString()));
                    mCalendarController.onGridviewAdapterOnItemClickListenerCallBack(calendarTextView.getDate());
                }
            }
        });
    }

    public void cleanSelect() {
        if (null == gridview) {
            return;
        }
        //非空为本月日期
        int childCount = gridview.getChildCount();
        for (int i = 0; i < childCount; i++) {
            //设置本月日期背景颜色
            View childView = gridview.getChildAt(i);
            CalendarTextView gridviewItem = (CalendarTextView) childView.findViewById(R.id.date);
            gridviewItem.setSelected(false);
        }
    }

    public GridView getGridview() {
        return gridview;
    }

    /**
     * 获取下一个月份日历数据
     */
    private Calendar calendar;
    private int index;

    public void getMonth(int index) {
        this.index = index;
        calendar = mCalendarController.getMonth(index);
        if (gridviewAdapter != null) {
            gridviewAdapter.setDate(calendar);
            cleanSelect();
        }
    }

    public Calendar getCalendar() {
        if (calendar == null) {
            return mCalendarController.getToadyCalendar();
        }
        return calendar;
    }
}
