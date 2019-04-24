package hust.edu.vn.timem.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.TimetableView;

import java.util.ArrayList;

import hust.edu.vn.timem.R;

public class TimeTableActivity extends AppCompatActivity {
    private TimetableView timetable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table2);

    }

    private void init(){
        timetable = findViewById(R.id.timetable);
        timetable.setHeaderHighlight(2);
        initView();
    }
    private void initView(){

        timetable.setOnStickerSelectEventListener(new TimetableView.OnStickerSelectedListener() {
            @Override
            public void OnStickerSelected(int idx, ArrayList<Schedule> schedules) {
//                Intent i = new Intent(context, EditActivity.class);
//                i.putExtra("mode",REQUEST_EDIT);
//                i.putExtra("idx", idx);
//                i.putExtra("schedules", schedules);
//                startActivityForResult(i,REQUEST_EDIT);
            }
        });
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.add_btn:
//                Intent i = new Intent(this,EditActivity.class);
//                i.putExtra("mode",REQUEST_ADD);
//                startActivityForResult(i,REQUEST_ADD);
//                break;
//            case R.id.clear_btn:
//                timetable.removeAll();
//                break;
//            case R.id.save_btn:
//                saveByPreference(timetable.createSaveData());
//                break;
//            case R.id.load_btn:
//                loadSavedData();
//                break;
//        }
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        switch (requestCode){
//            case REQUEST_ADD:
//                if(resultCode == EditActivity.RESULT_OK_ADD){
//                    ArrayList<Schedule> item = (ArrayList<Schedule>)data.getSerializableExtra("schedules");
//                    timetable.add(item);
//                }
//                break;
//            case REQUEST_EDIT:
//                /** Edit -> Submit */
//                if(resultCode == EditActivity.RESULT_OK_EDIT){
//                    int idx = data.getIntExtra("idx",-1);
//                    ArrayList<Schedule> item = (ArrayList<Schedule>)data.getSerializableExtra("schedules");
//                    timetable.edit(idx,item);
//                }
//                /** Edit -> Delete */
//                else if(resultCode == EditActivity.RESULT_OK_DELETE){
//                    int idx = data.getIntExtra("idx",-1);
//                    timetable.remove(idx);
//                }
//                break;
//        }
//    }

//    /** save timetableView's data to SharedPreferences in json format */
//    private void saveByPreference(String data){
//        SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(this);
//        SharedPreferences.Editor editor = mPref.edit();
//        editor.putString("timetable_demo",data);
//        editor.commit();
//        Toast.makeText(this,"saved!",Toast.LENGTH_SHORT).show();
//    }
//
//    /** get json data from SharedPreferences and then restore the timetable */
//    private void loadSavedData(){
//        timetable.removeAll();
//        SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(this);
//        String savedData = mPref.getString("timetable_demo","");
//        if(savedData == null && savedData.equals("")) return;
//        timetable.load(savedData);
//        Toast.makeText(this,"loaded!",Toast.LENGTH_SHORT).show();
//    }
//}

}
