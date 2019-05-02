package hust.edu.vn.timem.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import hust.edu.vn.timem.Model.CourseModel;
import hust.edu.vn.timem.R;
import hust.edu.vn.timem.View.TimetableView;

//import com.github.tlaabs.timetableview.Schedule;
//import com.github.tlaabs.timetableview.TimetableView;

public class TimeTableActivity extends AppCompatActivity {
//    private TimetableView timetable;
    ImageView btnAdd;
//    public static final int REQUEST_ADD = 1;
//    public static final int REQUEST_EDIT = 2;
    private TimetableView mTimetable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table2);
        init();



    }

    private void init(){
//        timetable = findViewById(R.id.timetable);
//        timetable.setHeaderHighlight(2);
        mTimetable = (TimetableView) findViewById(R.id.timetable);
        mTimetable.loadCourses(getData());
        btnAdd = findViewById(R.id.btnAdd);
//        initView();
//        initData();
    }
    private List<CourseModel> getData() {
        List<CourseModel> list = new ArrayList<>();

        CourseModel course1 = new CourseModel();
        course1.setCname("计算机网络");
        course1.setClassroom("1号楼103");
        course1.setStartSection(1);
        course1.setEndSection(4);
        course1.setDayOfWeek(1);
        course1.setStartWeek(1);
        course1.setEndWeek(12);
        list.add(course1);

        CourseModel course2 = new CourseModel();
        course2.setCname("计算机组成原理");
        course2.setClassroom("1号楼312");
        course2.setStartSection(7);
        course2.setEndSection(8);
        course2.setDayOfWeek(1);
        course2.setStartWeek(1);
        course2.setEndWeek(12);
        list.add(course2);


        CourseModel course3 = new CourseModel();
        course3.setCname("数据结构与算法");
        course3.setClassroom("1号楼201");
        course3.setStartSection(1);
        course3.setEndSection(2);
        course3.setDayOfWeek(2);
        course3.setStartWeek(4);
        course3.setEndWeek(18);
        list.add(course3);


        CourseModel course4 = new CourseModel();
        course4.setCname("高等数学");
        course4.setClassroom("3号楼602");
        course4.setStartSection(5);
        course4.setEndSection(6);
        course4.setDayOfWeek(2);
        course4.setStartWeek(1);
        course4.setEndWeek(12);
        list.add(course4);


        CourseModel course5 = new CourseModel();
        course5.setCname("离散数学");
        course5.setClassroom("1号楼311");
        course5.setStartSection(3);
        course5.setEndSection(4);
        course5.setDayOfWeek(3);
        course5.setStartWeek(1);
        course5.setEndWeek(12);
        list.add(course5);


        CourseModel course6 = new CourseModel();
        course6.setCname("无线网络技术");
        course6.setClassroom("1号楼210");
        course6.setStartSection(1);
        course6.setEndSection(2);
        course6.setDayOfWeek(4);
        course6.setStartWeek(4);
        course6.setEndWeek(8);
        list.add(course6);

        CourseModel course7 = new CourseModel();
        course7.setCname("大学体育");
        course7.setClassroom("运动场");
        course7.setStartSection(5);
        course7.setEndSection(6);
        course7.setDayOfWeek(4);
        course7.setStartWeek(4);
        course7.setEndWeek(12);
        list.add(course7);

        CourseModel course8 = new CourseModel();
        course8.setCname("操作系统");
        course8.setClassroom("2号楼303");
        course8.setStartSection(1);
        course8.setEndSection(4);
        course8.setDayOfWeek(5);
        course8.setStartWeek(1);
        course8.setEndWeek(12);
        list.add(course8);

        CourseModel course9 = new CourseModel();
        course9.setCname("Java语言程序设计");
        course9.setClassroom("2号楼104");
        course9.setStartSection(7);
        course9.setEndSection(8);
        course9.setDayOfWeek(5);
        course9.setStartWeek(1);
        course9.setEndWeek(12);
        list.add(course9);

        return list;
    }
//    private void initData(){
////        ContentResolver cr = getContentResolver();
////        Cursor cur = cr.query(TTDBProvider.DB_DATA_CONTENT_URI,
////                null, null, null, null);
//        SQLiteTimeTable sqLiteTimeTable =  new SQLiteTimeTable(this);
//        List<Schedule> data = sqLiteTimeTable.getData();
//        timetable.add((ArrayList<Schedule>) data);
//
//    }
//    private void initView(){
//        btnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getApplicationContext(), hust.edu.vn.timem.Activity.EditActivity.class);
//                i.putExtra("mode",REQUEST_ADD);
//                startActivityForResult(i,REQUEST_ADD);
//            }
//        });
//
//        timetable.setOnStickerSelectEventListener(new TimetableView.OnStickerSelectedListener() {
//            @Override
//            public void OnStickerSelected(int idx, ArrayList<Schedule> schedules) {
//                Intent i = new Intent(getApplicationContext(), EditActivity.class);
//                i.putExtra("mode",REQUEST_EDIT);
//                i.putExtra("idx", idx);
//                i.putExtra("schedules", schedules);
//                startActivityForResult(i,REQUEST_EDIT);
//            }
//        });
//    }
//
////    @Override
////    public void onClick(View v) {
////        switch (v.getId()){
////            case R.id.add_btn:
////                Intent i = new Intent(this,EditActivity.class);
////                i.putExtra("mode",REQUEST_ADD);
////                startActivityForResult(i,REQUEST_ADD);
////                break;
////            case R.id.clear_btn:
////                timetable.removeAll();
////                break;
////            case R.id.save_btn:
////                saveByPreference(timetable.createSaveData());
////                break;
////            case R.id.load_btn:
////                loadSavedData();
////                break;
////        }
////    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        switch (requestCode){
//            case REQUEST_ADD:
//                if(resultCode == EditActivity.RESULT_OK_ADD){
//                    SQLiteTimeTable sqLiteTimeTable =  new SQLiteTimeTable(this);
//                    ArrayList<Schedule> item = (ArrayList<Schedule>) sqLiteTimeTable.getData();
//                    timetable.add(item);
//
//                }
//                break;
//            case REQUEST_EDIT:
//                /** Edit -> Submit */
//                if(resultCode == EditActivity.RESULT_OK_EDIT){
//                    int idx = data.getIntExtra("idx",-1);
//                    SQLiteTimeTable sqLiteTimeTable =  new SQLiteTimeTable(this);
//                    ArrayList<Schedule> item = (ArrayList<Schedule>) sqLiteTimeTable.getData();
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
//
////    /** save timetableView's data to SharedPreferences in json format */
////    private void saveByPreference(String data){
////        SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(this);
////        SharedPreferences.Editor editor = mPref.edit();
////        editor.putString("timetable_demo",data);
////        editor.commit();
////        Toast.makeText(this,"saved!",Toast.LENGTH_SHORT).show();
////    }
////
////    /** get json data from SharedPreferences and then restore the timetable */
////    private void loadSavedData(){
////        timetable.removeAll();
////        SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(this);
////        String savedData = mPref.getString("timetable_demo","");
////        if(savedData == null && savedData.equals("")) return;
////        timetable.load(savedData);
////        Toast.makeText(this,"loaded!",Toast.LENGTH_SHORT).show();
////    }
}


