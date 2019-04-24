//package hust.edu.vn.timem.CalendarUtil;
//
//import android.content.Context;
//import android.graphics.Color;
//import android.graphics.Typeface;
//import android.graphics.drawable.ColorDrawable;
//import android.graphics.drawable.Drawable;
//import android.text.style.ForegroundColorSpan;
//import android.text.style.RelativeSizeSpan;
//import android.text.style.StyleSpan;
//
//import com.prolificinteractive.materialcalendarview.CalendarDay;
//import com.prolificinteractive.materialcalendarview.DayViewDecorator;
//import com.prolificinteractive.materialcalendarview.DayViewFacade;
//
//import java.util.Calendar;
//
//import hust.edu.vn.timem.R;
//
//
//public class DecoratorToday implements DayViewDecorator {
//
//    private final Calendar calendar = Calendar.getInstance();
//    private  Drawable highlightDrawable;
//    private final int color = Color.parseColor("#228BC34A");
//    private CalendarDay date;
//    private Context context;
//
//    public DecoratorToday(Context context) {
//        this.context = context;
//        highlightDrawable = this.context.getResources().getDrawable(R.drawable.shape_currdate);
//        date = CalendarDay.today();
//    }
//
//    @Override
//    public boolean shouldDecorate(CalendarDay day) {
//        day.copyTo(calendar);
//        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
//        return date != null && day.equals(date);
//    }
//
//    @Override
//    public void decorate(DayViewFacade view) {
//        view.setBackgroundDrawable(highlightDrawable);
//
//    }
//}