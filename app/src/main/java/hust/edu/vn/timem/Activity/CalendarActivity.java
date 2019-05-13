package hust.edu.vn.timem.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.CalendarDayEvent;
import com.shrikanthravi.collapsiblecalendarview.data.Day;
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import hust.edu.vn.timem.Adapter.ReminderAdapter;
import hust.edu.vn.timem.Data.ReminderDatabase;
import hust.edu.vn.timem.Model.Reminder;
import hust.edu.vn.timem.R;
import hust.edu.vn.timem.Receiver.AlarmReceiver;

public class CalendarActivity extends AppCompatActivity {
    FloatingActionButton btn_add;
    TextView txt;
    ImageView btn_today;
    private AlarmReceiver alarmReceiver;
    private RecyclerView lst_reminder;
    String date="";
    private Toolbar toolbar;
    CompactCalendarView compactCalendarView;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());
    private Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
     ActionBar actionBar;
     String dateToday;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        //getSupportActionBar().setElevation(0);
       // getWindow().setStatusBarColor(getResources().getColor(R.color.google_red));
        btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ReminderAddActivity.class);
                startActivity(intent);
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        // Setting default toolbar title to empty
        actionBar.setTitle(null);

        compactCalendarView = findViewById(R.id.compactcalendar_view);
        compactCalendarView.drawSmallIndicatorForEvents(true);
        compactCalendarView.setUseThreeLetterAbbreviation(true);

        //set initial title
        actionBar.setTitle(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));

        //set title on calendar scroll
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                String d = dateFormat.format(dateClicked);
                txt.setText("-----"+d+"-----");
               // Toast.makeText(CalendarActivity.this, "Date : " + d, Toast.LENGTH_SHORT).show();
               DataItem(d);

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                // Changes toolbar title on monthChange
                actionBar.setTitle(dateFormatForMonth.format(firstDayOfNewMonth));

            }

        });




        //  gotoToday();

        alarmReceiver = new AlarmReceiver();
        lst_reminder = findViewById(R.id.lst_reminder);
        txt = findViewById(R.id.txt);
        LinearLayoutManager ll = new LinearLayoutManager(this);
        lst_reminder.setLayoutManager(ll);
        lst_reminder.setNestedScrollingEnabled(false);
        Calendar mCalendar= Calendar.getInstance();
        int mYear = mCalendar.get(Calendar.YEAR);
        int mMonth = mCalendar.get(Calendar.MONTH) + 1;
        int mDay = mCalendar.get(Calendar.DATE);
        String d = mYear+"/";
        if(mMonth<10){
            d+="0"+mMonth+"/";
        }else d += mMonth+"/";
        if(mDay<10){
            d+="0"+mDay;
        }else d += mDay;
        dateToday = d;
        txt.setText("-----"+dateToday+"-----");
        DataItem(d);
        AddEvent(compactCalendarView);
//        collapsibleCalendar.addEventTag(today.get(YEAR),today.get(MONTH),today.get(DAY_OF_MONTH));

       // System.out.println("Testing date "+collapsibleCalendar.getSelectedDay().getDay()+"/"+collapsibleCalendar.getSelectedDay().getMonth()+"/"+collapsibleCalendar.getSelectedDay().getYear());


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



    private void setToMidnight(Calendar calendar, String time) {
        String [] t_split = time.split(":");
        int h = Integer.parseInt(t_split[0]);
        int m = Integer.parseInt(t_split[1]);
        calendar.set(Calendar.HOUR_OF_DAY, h);
        calendar.set(Calendar.MINUTE, m);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    public void gotoToday() {

        // Set any date to navigate to particular date
        compactCalendarView.setCurrentDate(Calendar.getInstance(Locale.getDefault()).getTime());


    }
    void DataItem(String date){
        this.date = date;
        ReminderDatabase db = new ReminderDatabase(this);
        List<Reminder> lst = db.getAllRemindersInDay(date);
        ReminderAdapter adapter = new ReminderAdapter(this,lst );
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(new ReminderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Reminder reminder) {
                Toast.makeText(CalendarActivity.this, reminder.getID()+"", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(CalendarActivity.this, ReminderEditActivity.class);
                i.putExtra(ReminderEditActivity.EXTRA_REMINDER_ID, reminder.getID()+"");
                startActivity(i);
            }
        });

        lst_reminder.setAdapter(adapter);
    }
    void AddEvent(CompactCalendarView compactCalendarView){
        ReminderDatabase db = new ReminderDatabase(this);
        List<Reminder> lst = db.getAllReminders();

        for(Reminder r : lst){
            String d = r.getDate();
            String[] d_split = d.split("/");
            int day = Integer.parseInt(d_split[2])-1;
            int month = Integer.parseInt(d_split[1])-1;
            int year  = Integer.parseInt(d_split[0]);
            currentCalender.setTime(new Date());
            currentCalender.set(Calendar.DAY_OF_MONTH, 1);
            Date firstDayOfMonth = currentCalender.getTime();
            currentCalender.setTime(firstDayOfMonth);
            currentCalender.set(Calendar.MONTH, month);
            currentCalender.set(Calendar.YEAR, year);
            currentCalender.add(Calendar.DATE, day);
            setToMidnight(currentCalender, r.getTime());
            compactCalendarView.addEvent(new CalendarDayEvent(currentCalender.getTimeInMillis(), Color.argb(255, 255, 255, 255)), false);

        }
        // Refresh calendar to update events
        compactCalendarView.invalidate();
    }

    @Override
    public void onResume(){
        super.onResume();
        DataItem(date);
        AddEvent(compactCalendarView);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_calendar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            // On clicking the back arrow
            // Discard any changes
            case android.R.id.home:
                onBackPressed();
                return true;


            // On clicking save reminder button
            // Update reminder
            case R.id.today_btn:
                gotoToday();
                actionBar.setTitle(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));
                DataItem(dateToday);
                txt.setText("-----"+dateToday+"-----");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
