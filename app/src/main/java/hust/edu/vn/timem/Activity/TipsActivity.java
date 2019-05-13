package hust.edu.vn.timem.Activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.label305.asynctask.SimpleAsyncTask;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;

import hust.edu.vn.timem.Adapter.ViewPagerAdapter;
import hust.edu.vn.timem.CalendarUtil.LunarCalendarUtil;
import hust.edu.vn.timem.CalendarUtil.YMD;
import hust.edu.vn.timem.Fragment.CityWeatherFragment;
import hust.edu.vn.timem.R;

public class TipsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    TextView txt_am_lich, txt_duong_lich;
    ImageView img_tran;
    ViewPager vpPager;
    int [] res ={ R.drawable.ic_arrow_forward_blue_24dp, R.drawable.ic_arrow_back_blue_24dp};
    int mark =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        txt_am_lich = findViewById(R.id.txt_am_lich);
        txt_duong_lich = findViewById(R.id.txt_duong_lich);
        img_tran = findViewById(R.id.img_tran);
        mark =0;
        vpPager = findViewById(R.id.vpPager);
        setUpViewPager(vpPager);
        img_tran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mark = 1 - mark;
                img_tran.setImageResource(res[mark]);
               if(mark==1) txt_duong_lich.setText("");
               else  txt_am_lich.setText("");
            }
        });
        txt_duong_lich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mark==0){
                    txt_am_lich.setText("");

                    Calendar now = Calendar.getInstance();
                    DatePickerDialog dpd = DatePickerDialog.newInstance(
                            TipsActivity.this,
                            now.get(Calendar.YEAR),
                            now.get(Calendar.MONTH),
                            now.get(Calendar.DAY_OF_MONTH)
                    );
                    dpd.show(getFragmentManager(), "Datepickerdialog");
                }


            }
        });
        txt_am_lich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mark==1){
                    txt_duong_lich.setText("");
                    Calendar now = Calendar.getInstance();
                    DatePickerDialog dpd = DatePickerDialog.newInstance(
                            TipsActivity.this,
                            now.get(Calendar.YEAR),
                            now.get(Calendar.MONTH),
                            now.get(Calendar.DAY_OF_MONTH)
                    );
                    dpd.show(getFragmentManager(), "Datepickerdialog");
                }


            }
        });

    }

    private void setUpViewPager(ViewPager vpPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        //adapter.addFragment(WeatherFragment.getInstance(), "today");
        adapter.addFragment(CityWeatherFragment.getInstance(), "city");
        vpPager.setAdapter(adapter);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        monthOfYear++;
        if(mark==0) txt_duong_lich.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
       // else txt_am_lich.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
        Calendar cal = Calendar.getInstance();
        cal.set(year, monthOfYear, dayOfMonth);

        float tz = TimeUnit.HOURS.convert(cal.getTimeZone().getRawOffset(), TimeUnit.MILLISECONDS);
        YMD tmp;

        tmp = LunarCalendarUtil.convertSolar2Lunar(year, monthOfYear, dayOfMonth, tz);
        //else tmp = LunarCalendarUtil.convertLunar2Solar(year, monthOfYear, dayOfMonth, tz);
        txt_am_lich.setText(tmp.getDay()+"/"+tmp.getMonth()+"/"+tmp.getYear());


    }






}


