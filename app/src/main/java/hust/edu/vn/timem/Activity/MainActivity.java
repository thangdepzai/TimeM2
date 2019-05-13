package hust.edu.vn.timem.Activity;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import hust.edu.vn.timem.Adapter.ReminderAdapter;
import hust.edu.vn.timem.Adapter.ViewPagerAdapter;
import hust.edu.vn.timem.Data.ReminderDatabase;
import hust.edu.vn.timem.Fragment.WeatherForecastFragment;
import hust.edu.vn.timem.Model.Reminder;
import hust.edu.vn.timem.R;
import hust.edu.vn.timem.UserPreference;
import hust.edu.vn.timem.WeatherUtil.Common.Common;


public class MainActivity extends AppCompatActivity {
    ImageView TipActivity, NoteActivity, TimeTableActivity, CalendarActivity;
    ViewPager vpPager;
    CircleImageView img_user;
    TextView txt_user_name;
    //TabLayout tabLayout;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    private UserPreference userPreference;
    private RecyclerView  lst_reminder;
    private static final long milMinute = 60000L;
    private static final long milHour = 3600000L;
    private static final long milDay = 86400000L;
    private static final long milWeek = 604800000L;
    private static final long milMonth = 2592000000L;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TipActivity = findViewById(R.id.TipActivity);
        NoteActivity = findViewById(R.id.NoteActivity);
        CalendarActivity = findViewById(R.id.CalendarActivity);
        TimeTableActivity = findViewById(R.id.TimeTableActivity);
        img_user = findViewById(R.id.img_user);
        txt_user_name = findViewById(R.id.txt_user_name);
        userPreference = UserPreference.getUserPreference(this);
        boolean state=userPreference.isUserLoggedIn();
        if(state){
            txt_user_name.setText(userPreference.getUserName());
            if(!userPreference.getUserUrl().equals("") ){
//                String url = userPreference.getUserUrl();
//                Uri  uri = Uri.parse(url);
//                Log.i("IMAGE", url);
//                String [] filepath = {MediaStore.Images.Media.DATA};
//                Cursor cu = getContentResolver().query(uri,filepath, null, null,null);
//                cu.moveToFirst();
//                int index = cu.getColumnIndex(filepath[0]);
//                String picpath = cu.getColumnName(index);
//                img_user.setImageBitmap(BitmapFactory.decodeFile(picpath));

            }
        }else{
            txt_user_name.setText("Guess");

        }

        lst_reminder = findViewById(R.id.lst_reminder);
        LinearLayoutManager  ll = new LinearLayoutManager(this);
        lst_reminder.setLayoutManager(ll);
        lst_reminder.setNestedScrollingEnabled(false);
        img_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), hust.edu.vn.timem.Activity.SettingActivity.class));

            }
        });

        //tabLayout = (TabLayout) findViewById(R.id.tabDots);
        CalendarActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), hust.edu.vn.timem.Activity.CalendarActivity.class));
            }
        });
        TimeTableActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), hust.edu.vn.timem.Activity.TimeTableActivity.class));

            }
        });
        NoteActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), hust.edu.vn.timem.Activity.NoteActivity.class));
            }
        });
        TipActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), hust.edu.vn.timem.Activity.TipsActivity.class));
            }
        });
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            buildLocationRequest();
                            buildLocationCallBack();
                            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }
                            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
                            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                    }
                }).check();


        DataItem();

    }

    private void buildLocationCallBack() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Common.currentLocation = locationResult.getLastLocation();
                vpPager = findViewById(R.id.vpPager);
                setUpViewPager(vpPager);
                //tabLayout.setupWithViewPager(vpPager, true);

            }
        };


    }

    private void setUpViewPager(ViewPager vpPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        //adapter.addFragment(WeatherFragment.getInstance(), "today");
        adapter.addFragment(WeatherForecastFragment.getInstance(), "5 Days");
        vpPager.setAdapter(adapter);
    }

    private void buildLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setSmallestDisplacement(10.0f);

    }
    void DataItem(){

        Calendar mCalendar= Calendar.getInstance();
        Long currentTimeinLong = mCalendar.getTimeInMillis();
        int mYear = mCalendar.get(Calendar.YEAR);
        int mMonth = mCalendar.get(Calendar.MONTH) + 1;
        int mDay = mCalendar.get(Calendar.DATE);
        int mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        int mMinute = mCalendar.get(Calendar.MINUTE);
        int thu = mCalendar.get(Calendar.DAY_OF_WEEK);
        String mTime;
        if (mHour < 10) {
            mTime = "0" + mHour +":";
        } else {
            mTime = mHour + ":";
        }
        if (mMinute < 10) {
            mTime = mTime  + "0" + mMinute;
        } else {
            mTime = mTime  + mMinute;
        }
        String d = mYear+"/";
        if(mMonth<10){
            d+="0"+mMonth+"/";
        }else d += mMonth+"/";
        if(mDay<10){
            d+="0"+mDay;
        }else d += mDay;
        ReminderDatabase  rb = new ReminderDatabase(this);
        List<Reminder> lst = rb.getToday(d);
//        for(Reminder r: lst) {
//            if (r.getDate().equals(d)) {
//                if (r.getRepeat().equals("false") && r.getTime().compareTo(mTime) < 0) {
//                    lst.remove(r);
//                    continue;
//                }
//            }else{
//                 long time_repeat = Integer.parseInt(r.getRepeatNo());
//                 if(r.getRepeatType().equals("Minute")){
//                     time_repeat= time_repeat*milMinute;
//                }else if(r.getRepeatType().equals("Hour")){
//                    time_repeat= time_repeat*milHour;
//                }else if(r.getRepeatType().equals("Day")){
//                     time_repeat= time_repeat*milDay;
//                 }else if(r.getRepeatType().equals("Week")){
//                     time_repeat= time_repeat*milWeek;
//                 }else if(r.getRepeatType().equals("Month")){
//                     time_repeat= time_repeat*milMonth;
//                 }
//                long diff=(long)(currentTimeinLong-time_repeat);
//
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTimeInMillis(diff);
//
//                int y1 = calendar.get(Calendar.YEAR);
//                int m1 = calendar.get(Calendar.MONTH);
//                int d1 = calendar.get(Calendar.DAY_OF_MONTH);
//                String date = r.getDate();
//                String[] _date = date.split("/");
//                int day = Integer.parseInt(_date[2]);
//                int month = Integer.parseInt(_date[1]);
//                int year = Integer.parseInt(_date[0]);
//                if(y1> year || m1> month || d1 >day){
//                    lst.remove(r);
//                    continue;
//                }
//
//
//                if(r.getRepeatType().equals("Day")){
//                    if(r.getTime().compareTo(mTime)<0){
//                        lst.remove(r);
//                        continue;
//                    }
//                }
//
//                 if (r.getRepeatType().equals("Month")) {
//                    if (mDay != day) {
//                        lst.remove(r);
//                        continue;
//                    }
//                } else if (r.getRepeatType().equals("Week")) {
//                     Calendar c= Calendar.getInstance();
//                     c.set(Calendar.DAY_OF_MONTH, day);
//                     c.set(Calendar.MONTH, month-1);
//                     c.set(Calendar.YEAR, year);
//                     int thu1 = c.get(Calendar.DAY_OF_WEEK);
//                     if(thu!=thu1){
//                         lst.remove(r);
//                         continue;
//                     }
//
//
//                }
//            }
//        }
        ReminderAdapter ad = new ReminderAdapter(this,lst );
        lst_reminder.setAdapter(ad);
    }
    @Override
    public void onResume(){
        super.onResume();
//        if(!userPreference.getUserUrl().equals("") ){
//            String url = userPreference.getUserUrl();
//            Uri  uri = Uri.parse(url);
//            Log.i("IMAGE", url);
//            String [] filepath = {MediaStore.Images.Media.DATA};
//            Cursor cu = getContentResolver().query(uri,filepath, null, null,null);
//            cu.moveToFirst();
//            int index = cu.getColumnIndex(filepath[0]);
//            String picpath = cu.getColumnName(index);
//            img_user.setImageBitmap(BitmapFactory.decodeFile(picpath));
//
//        }
        DataItem();

    }
}



