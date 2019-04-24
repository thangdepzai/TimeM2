package hust.edu.vn.timem.Activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.shrikanthravi.collapsiblecalendarview.data.Day;
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar;
import com.shrikanthravi.collapsiblecalendarview.widget.UICalendar;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import hust.edu.vn.timem.R;

import static java.util.Calendar.DATE;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

public class CalendarActivity extends AppCompatActivity {
//    MaterialCalendarView calendarView;
//    Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        //getSupportActionBar().setElevation(0);
        getWindow().setStatusBarColor(getResources().getColor(R.color.google_red));

        final CollapsibleCalendar collapsibleCalendar = findViewById(R.id.calendarView);
//        Calendar today=new GregorianCalendar();
//        collapsibleCalendar.addEventTag(today.get(YEAR),today.get(MONTH),today.get(DAY_OF_MONTH));
//        today.add(DATE,1);
//        collapsibleCalendar.addEventTag(today.get(YEAR),today.get(MONTH),today.get(DAY_OF_MONTH));

       // System.out.println("Testing date "+collapsibleCalendar.getSelectedDay().getDay()+"/"+collapsibleCalendar.getSelectedDay().getMonth()+"/"+collapsibleCalendar.getSelectedDay().getYear());

        collapsibleCalendar.setCalendarListener(new CollapsibleCalendar.CalendarListener() {
            @Override
            public void onDaySelect() {
                Day day = collapsibleCalendar.getSelectedDay();
                Log.i(getClass().getName(), "Selected Day: "
                        + day.getYear() + "/" + (day.getMonth() + 1) + "/" + day.getDay());


            }

            @Override
            public void onItemClick(View view) {

            }

            @Override
            public void onDataUpdate() {

            }

            @Override
            public void onMonthChange() {

            }

            @Override
            public void onWeekChange(int i) {

            }
        });
//        calendarView = findViewById(R.id.calendarView);
//        btnBack = findViewById(R.id.btnBack);
//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                calendarView.setCurrentDate(Calendar.getInstance().getTime());
//                calendarView.setSelectedDate(Calendar.getInstance().getTime());
//            }
//        });
//        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
//            @Override
//            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
//
//            }
//        });
//        calendarView.setSelectedDate(Calendar.getInstance().getTime());
//
//        addLunarDecord();
//        CalendarDay date = CalendarDay.from(Calendar.getInstance());
//        calendarView.state().edit()
//                .setFirstDayOfWeek(Calendar.MONDAY)
//                .setMinimumDate(CalendarDay.from(date.getYear()-2, 12, 31))
//                .setMaximumDate(CalendarDay.from(date.getYear()+1, 12, 31))
//                .setCalendarDisplayMode(CalendarMode.MONTHS)
//                .commit();
//        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
//            @Override
//            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
//                Calendar cal = Calendar.getInstance();
//                cal.set(date.getYear(), date.getMonth(), 1);
//                ArrayList listDecor = new ArrayList();
//
//                for (int i = 0; i < 31; i++) {
//                    CalendarDay day = CalendarDay.from(cal);
//                    listDecor.add(new LunarDecorator(Color.parseColor("#2980b9"), day));
//                    cal.add(Calendar.DATE, 1);
//                }
//                widget.addDecorators(listDecor);
//            }
//        });


    }

//    public void addLunarDecord(){
//        calendarView.addDecorator(new DecoratorToday(this));
//        Calendar cal = getInstance();
//        CalendarDay date = CalendarDay.from(cal);
//        cal.set(date.getYear()-2, 12, 31);
//
//        ArrayList listDecor = new ArrayList();
//
//        for (int i = 0; i < 36*31; i++) {
//            CalendarDay day = CalendarDay.from(cal);
//            listDecor.add(new LunarDecorator(Color.parseColor("#2980b9"), day));
//            cal.add(DATE, 1);
//        }
//        calendarView.addDecorators(listDecor);
//
//
//    }
}
