package cn.cn.calendar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.TextView;

import cn.cn.calendar.R;

/**
 * author weiwei on 2016/11/18 0018 14:43.
 */
public class CalendarTextView extends TextView {
    private long date;
    private boolean isToday;
    private boolean isCurrentMonth;
    private float calendar_stroke_width = 0;
    private float calendar_text_size = 0;
    private float calendar_circle_indicator_width = 0;
    private int circleX = 0, circleY = 0;

    public CalendarTextView(Context context) {
        this(context, null);
    }

    private Paint circlePaint;
    private Paint textPaint;

    public CalendarTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        calendar_stroke_width = getResources().getDimension(R.dimen.calendar_stroke_width);
        calendar_text_size = getResources().getDimension(R.dimen.calendar_text_size);
        calendar_circle_indicator_width = getResources().getDimension(R.dimen.calendar_circle_indicator_width);

        circlePaint = new Paint();
        circlePaint.setColor(ContextCompat.getColor(this.getContext(), R.color.e47e11));
        circlePaint.setStrokeWidth(calendar_stroke_width);
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setColor(ContextCompat.getColor(this.getContext(), R.color.black));
        textPaint.setTextSize(calendar_text_size);
        textPaint.setStrokeWidth(calendar_stroke_width);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setAntiAlias(true);
    }

    public void setDate(long time) {
        date = time;
    }

    public void setToday(boolean today) {
        isToday = today;
    }

    public void setCurrentMonth(boolean currentMonth) {
        isCurrentMonth = currentMonth;
    }

    public long getDate() {
        return date;
    }

    public boolean isToday() {
        return isToday;
    }

    public boolean isCurrentMonth() {
        return isCurrentMonth;
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        if (circleX == 0) {
            circleX = getMeasuredWidth() / 2;
        }

        if (circleY == 0) {
            circleY = getMeasuredHeight() / 2;
        }


        if (isCurrentMonth()) {
            if (isSelected()) {
                textPaint.setColor(ContextCompat.getColor(this.getContext(), R.color.white));
                circlePaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(circleX, circleY, calendar_circle_indicator_width, circlePaint);
            } else {
                textPaint.setColor(ContextCompat.getColor(this.getContext(), R.color.black));
                if (isToday()) {
                    circlePaint.setStyle(Paint.Style.STROKE);
                    canvas.drawCircle(circleX, circleY, calendar_circle_indicator_width, circlePaint);
                }
            }
        } else {
            textPaint.setColor(ContextCompat.getColor(this.getContext(), R.color.color_888888));
        }
        canvas.drawText(getText().toString(), circleX, circleY + calendar_circle_indicator_width / 2 - 1, textPaint);
    }
}
