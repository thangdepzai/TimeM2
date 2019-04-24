package hust.edu.vn.timem.CalendarUtil;

import android.content.res.Resources;
import android.graphics.Color;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class LunarDecorator implements DayViewDecorator {
    private int color;
    private CalendarDay date;

    public LunarDecorator(int color, CalendarDay date) {
        this.color = color;
        this.date = date;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return date != null && day.equals(date);
    }

    @Override
    public void decorate(DayViewFacade view) {
        Calendar cal = Calendar.getInstance();
        cal.set(date.getYear(), date.getMonth() + 1, date.getDay());

        float tz = TimeUnit.HOURS.convert(cal.getTimeZone().getRawOffset(), TimeUnit.MILLISECONDS);
        YMD tmp = LunarCalendarUtil.convertSolar2Lunar(date.getYear(), date.getMonth()+1, date.getDay(), tz);

        String ngayAm = tmp.day+"";
        if(tmp.day == 1){
            ngayAm = tmp.day+"/"+tmp.month;
            color = Color.RED;
        }

        if(tmp.day == 15){
            ngayAm = tmp.day+"";
            color = Color.RED;
        }


        final float scale = Resources.getSystem().getDisplayMetrics().density;
//        SpannableString span = new SpannableString(ngayAm+"");
//        span.setSpan(new ForegroundColorSpan(color),0,span.length(),
//                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        view.addSpan(new CalendarViewSpan(ngayAm+"", color));
    }
}
