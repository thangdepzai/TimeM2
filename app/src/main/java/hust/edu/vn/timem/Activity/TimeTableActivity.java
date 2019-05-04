package hust.edu.vn.timem.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import hust.edu.vn.timem.Data.SQLiteTimeTable;
import hust.edu.vn.timem.Model.CourseModel;
import hust.edu.vn.timem.R;
import hust.edu.vn.timem.View.TimetableView;


public class TimeTableActivity extends AppCompatActivity {

    ImageView btnAdd;
    public static final int REQUEST_ADD = 1;
    public static final int REQUEST_EDIT = 2;
    private TimetableView mTimetable;
    static ArrayList<CourseModel> listCourse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_time_table2);
        init();




    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("obj", listCourse);
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            listCourse =  savedInstanceState.getParcelableArrayList("obj");


            if (listCourse != null) {
              mTimetable.loadCourses((List<CourseModel>) listCourse);
            }
        }
    }

    private void init(){

        mTimetable = (TimetableView) findViewById(R.id.timetable);
        listCourse = (ArrayList<CourseModel>) getData();
        mTimetable.loadCourses((List<CourseModel>) listCourse);
        btnAdd = findViewById(R.id.btnAdd);
        mTimetable.setOnCourseItemLongClickListener(new TimetableView.OnCourseItemLongClickListener() {
            @Override
            public boolean onCourseItemLongClick(final CourseModel course) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                deleteData(Integer.parseInt(course.getUid()));
                               listCourse.remove(course);
                               mTimetable.loadCourses(listCourse);
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(TimeTableActivity.this);
                builder.setMessage("Are you sure to delete ?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
                return true;

            }
        });
        mTimetable.setOnCourseItemClickListener(new TimetableView.OnCourseItemClickListener() {
            @Override
            public void onCourseItemClick(final CourseModel course) {
                final EditText subject_dialog;
                final EditText teacher_dialog;
                final EditText room_dialog;
                final EditText from_time_dialog;
                final EditText to_time_dialog;
                final EditText to_week_dialog;
                final EditText from_week_dialog;
                final EditText day_dialog;
                Button btn_cancel;
                Button btn_save;
                AlertDialog.Builder dialogBuilder =	new AlertDialog.Builder(TimeTableActivity.this);
                LayoutInflater inflater	= TimeTableActivity.this.getLayoutInflater();
                @SuppressLint("ResourceType") View dialogView	=	inflater.inflate(R.layout.dialog_custom_edit_timetable, (ViewGroup)findViewById(R.layout.activity_time_table2));
                subject_dialog = dialogView.findViewById(R.id.subject_dialog);
                teacher_dialog = dialogView.findViewById(R.id.teacher_dialog);
                room_dialog = dialogView.findViewById(R.id.room_dialog);
                from_time_dialog = dialogView.findViewById(R.id.from_time_dialog);
                from_week_dialog = dialogView.findViewById(R.id.from_week_dialog);
                to_time_dialog = dialogView.findViewById(R.id.to_time_dialog);
                to_week_dialog = dialogView.findViewById(R.id.to_week_dialog);
                day_dialog = dialogView.findViewById(R.id.day_dialog);
                dialogBuilder.setView(dialogView);
                dialogBuilder.setTitle("Edit Subject");
                final AlertDialog b = dialogBuilder.create();
                subject_dialog.setText(course.getCname());
                teacher_dialog.setText(course.getTeacher());
                room_dialog.setText(course.getClassroom());
                from_time_dialog.setText(course.getStartSection()+"");
                from_week_dialog.setText(course.getStartWeek()+"");
                to_time_dialog.setText(course.getEndSection()+"");
                to_week_dialog.setText(course.getEndWeek()+"");
                day_dialog.setText((course.getDayOfWeek()+1)+"");

                btn_cancel= dialogView.findViewById(R.id.cancel);
                btn_save = dialogView.findViewById(R.id.save);
                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listCourse.remove(course);
                        CourseModel courseModel = new CourseModel();
                        courseModel.setDayOfWeek(Integer.parseInt(day_dialog.getText().toString())-1);
                        courseModel.setTeacher(teacher_dialog.getText().toString());
                        courseModel.setClassroom(room_dialog.getText().toString());
                        courseModel.setCname(subject_dialog.getText().toString());
                        courseModel.setStartWeek(Integer.parseInt(from_week_dialog.getText().toString()));
                        courseModel.setEndWeek(Integer.parseInt(to_week_dialog.getText().toString()));
                        courseModel.setStartSection(Integer.parseInt(from_time_dialog.getText().toString()));
                        courseModel.setEndSection(Integer.parseInt(to_time_dialog.getText().toString()));
                        updateData(courseModel, Integer.parseInt(course.getUid()));
                        courseModel.setUid(course.getUid());
                        listCourse.add(courseModel);
                        mTimetable.loadCourses((List<CourseModel>) listCourse);
                        b.dismiss();

                    }
                });
                btn_cancel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        b.dismiss();
                    }
                });
                b.show();



            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText subject_dialog;
                final EditText teacher_dialog;
                final EditText room_dialog;
                final EditText from_time_dialog;
                final EditText to_time_dialog;
                final EditText to_week_dialog;
                final EditText from_week_dialog;
                final EditText day_dialog;
                Button btn_cancel;
                Button btn_save;
                AlertDialog.Builder dialogBuilder =	new AlertDialog.Builder(TimeTableActivity.this);
                LayoutInflater inflater	= TimeTableActivity.this.getLayoutInflater();
                @SuppressLint("ResourceType") View dialogView	=	inflater.inflate(R.layout.dialog_custom_edit_timetable, (ViewGroup)findViewById(R.layout.activity_time_table2));
                subject_dialog = dialogView.findViewById(R.id.subject_dialog);
                teacher_dialog = dialogView.findViewById(R.id.teacher_dialog);
                room_dialog = dialogView.findViewById(R.id.room_dialog);
                from_time_dialog = dialogView.findViewById(R.id.from_time_dialog);
                from_week_dialog = dialogView.findViewById(R.id.from_week_dialog);
                to_time_dialog = dialogView.findViewById(R.id.to_time_dialog);
                to_week_dialog = dialogView.findViewById(R.id.to_week_dialog);
                day_dialog = dialogView.findViewById(R.id.day_dialog);
                dialogBuilder.setView(dialogView);
                dialogBuilder.setTitle("Add Subject");
                final AlertDialog b = dialogBuilder.create();
                to_week_dialog.setText("15");
                from_week_dialog.setText("1");

                btn_cancel= dialogView.findViewById(R.id.cancel);
                btn_save = dialogView.findViewById(R.id.save);
                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CourseModel courseModel = new CourseModel();
                        courseModel.setDayOfWeek(Integer.parseInt(day_dialog.getText().toString())-1);
                        courseModel.setTeacher(teacher_dialog.getText().toString());
                        courseModel.setClassroom(room_dialog.getText().toString());
                        courseModel.setCname(subject_dialog.getText().toString());
                        courseModel.setStartWeek(Integer.parseInt(from_week_dialog.getText().toString()));
                        courseModel.setEndWeek(Integer.parseInt(to_week_dialog.getText().toString()));
                        courseModel.setStartSection(Integer.parseInt(from_time_dialog.getText().toString()));
                        courseModel.setEndSection(Integer.parseInt(to_time_dialog.getText().toString()));
                        addData(courseModel);

                        listCourse = (ArrayList<CourseModel>) getData();
                        //listCourse.add(courseModel);
                        mTimetable.loadCourses((List<CourseModel>) listCourse);
                        b.dismiss();

                    }
                });
                btn_cancel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        b.dismiss();
                    }
                });
                b.show();

            }
        });


    }
    private void deleteData(int key){
        SQLiteTimeTable sqLiteTimeTable = new SQLiteTimeTable(this);
        sqLiteTimeTable.deleteItemSelected(key);
        sqLiteTimeTable.close();
    }
    private void updateData(CourseModel model, int key){
        SQLiteTimeTable sqLiteTimeTable = new SQLiteTimeTable(this);
        sqLiteTimeTable.update(model, key);
        sqLiteTimeTable.close();
    }
    private void addData(CourseModel model){
        SQLiteTimeTable sqLiteTimeTable = new SQLiteTimeTable(this);
        sqLiteTimeTable.add(model);
        sqLiteTimeTable.close();
    }
    private List<CourseModel> getData() {
        SQLiteTimeTable sqLiteTimeTable = new SQLiteTimeTable(this);
        List<CourseModel> list =  sqLiteTimeTable.getData();
        sqLiteTimeTable.close();
        return  list;


    }

}


