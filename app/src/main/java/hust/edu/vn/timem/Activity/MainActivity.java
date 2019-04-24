package hust.edu.vn.timem.Activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import hust.edu.vn.timem.R;


public class MainActivity extends Activity {
    ImageView TipActivity, NoteActivity, TimeTableActivity, CalendarActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TipActivity = findViewById(R.id.TipActivity);
        NoteActivity = findViewById(R.id.NoteActivity);
        CalendarActivity = findViewById(R.id.CalendarActivity);
        TimeTableActivity = findViewById(R.id.TimeTableActivity);
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

    }


}
