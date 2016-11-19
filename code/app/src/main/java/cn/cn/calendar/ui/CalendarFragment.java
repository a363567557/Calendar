package cn.cn.calendar.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import cn.cn.calendar.R;
import cn.cn.calendar.adapter.GridviewAdapter;
import cn.cn.calendar.controller.CalendarController;
import cn.cn.calendar.view.CalendarTextView;

/**
 * author weiwei on 2016/11/17 0017 16:20.
 */
public class CalendarFragment extends Fragment {
    private CalendarController mCalendarController;
    private GridView gridview;
    private GridviewAdapter gridviewAdapter;

    public CalendarFragment(CalendarController calendarController) {
        mCalendarController = calendarController;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmet_main, null);
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
        initListener();
    }

    private void initView() {
        gridview = (GridView) getView().findViewById(R.id.gridview);
        gridviewAdapter = new GridviewAdapter(getActivity(), mCalendarController);
        gridview.setAdapter(gridviewAdapter);
    }

    private void initListener() {
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CalendarTextView calendarTextView = (CalendarTextView) view.findViewById(R.id.date);
                if (calendarTextView.isCurrentMonth()) {
                    //非空为本月日期
                    int childCount = gridview.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        //设置本月日期背景颜色
                        View childView = gridview.getChildAt(i);
                        CalendarTextView gridviewItem = (CalendarTextView) childView.findViewById(R.id.date);
//                        if (gridviewItem.isCurrentMonth()) {
//                            if (gridviewItem.isToday()) {
//                                gridviewItem.setBackgroundColor(mCalendarController.getTodayNotSelectedBackground());
//                            } else {
//                                gridviewItem.setBackgroundColor(mCalendarController.getNormolBackground());
//                            }
                        gridviewItem.setSelected(false);
//                        }
                    }
//                    calendarTextView.setBackgroundColor(mCalendarController.getSelectedlBackground());
                    calendarTextView.setSelected(true);
                    if (calendarTextView.getDate() > 0) {
                        //大于0表示点击日期有数据
                        mCalendarController.onGridviewAdapterOnItemClickListenerCallBack(calendarTextView.getDate());
                    } else {
                        mCalendarController.onGridviewAdapterOnItemClickListenerCallBack(null);
                    }
                }
            }
        });
    }

    /**
     * 获取下一个月份日历数据
     */
    public void getNextMonth() {
        if (gridviewAdapter != null) {
            gridviewAdapter.setDate(mCalendarController.getNextMonth());
        }

    }

    /**
     * 获取上一个月份日历数据
     */
    public void getPreMonth() {
        if (gridviewAdapter != null) {
            gridviewAdapter.setDate(mCalendarController.getPreMonth());
        }
    }

    /**
     * 获取当前月份日历数据
     */
    public void onCurrentMonth() {
        if (gridviewAdapter != null) {
            gridviewAdapter.setDate(mCalendarController.onCurrentMonth());
        }
    }

    /**
     * 切换到上一个月份
     */
    public void onPreMonth() {
        if (gridviewAdapter != null) {
            gridviewAdapter.setDate(mCalendarController.onPreMonth());
        }
    }

    /**
     * 切换到下一个月份
     */
    public void onNextMonth() {
        if (gridviewAdapter != null) {
            gridviewAdapter.setDate(mCalendarController.onNextMonth());
        }
    }
}
