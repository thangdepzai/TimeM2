package hust.edu.vn.timem.Activity;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

import java.util.List;

import hust.edu.vn.timem.Adapter.ViewPagerAdapter;
import hust.edu.vn.timem.Fragment.WeatherForecastFragment;
import hust.edu.vn.timem.R;
import hust.edu.vn.timem.UserPreference;
import hust.edu.vn.timem.WeatherUtil.Common.Common;


public class MainActivity extends AppCompatActivity {
    ImageView TipActivity, NoteActivity, TimeTableActivity, CalendarActivity;
    ViewPager vpPager;
    ImageView img_user;
    TextView txt_user_name;
    //TabLayout tabLayout;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    private UserPreference userPreference;
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
        }else txt_user_name.setText("Guess");
        img_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), hust.edu.vn.timem.Activity.SettingActivity.class));
            }
        });
        //tabLayout = (TabLayout) findViewById(R.id.tabDots);

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
}



