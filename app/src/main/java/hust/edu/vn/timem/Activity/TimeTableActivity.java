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


    }
    private List<CourseModel> getData() {
        List<CourseModel> list = new ArrayList<>();

        CourseModel course1 = new CourseModel();
        course1.setCname("Hệ Phân tán");
        course1.setClassroom("D6-101");
        course1.setStartSection(3);
        course1.setEndSection(6);
        course1.setDayOfWeek(1);
        course1.setStartWeek(1);
        course1.setEndWeek(12);
        list.add(course1);

        CourseModel course2 = new CourseModel();
        course2.setCname("Xử lý thông tin mờ");
        course2.setClassroom("D6-101");
        course2.setStartSection(2);
        course2.setEndSection(5);
        course2.setDayOfWeek(2);
        course2.setStartWeek(1);
        course2.setEndWeek(12);
        list.add(course2);


        CourseModel course3 = new CourseModel();
        course3.setCname("Đồ họa và hiện thực ảo");
        course3.setClassroom("D6-101");
        course3.setStartSection(1);
        course3.setEndSection(3);
        course3.setDayOfWeek(3);
        course3.setStartWeek(1);
        course3.setEndWeek(12);
        list.add(course3);


        CourseModel course4 = new CourseModel();
        course4.setCname("Tương tác người máy");
        course4.setClassroom("D6-101");
        course4.setStartSection(4);
        course4.setEndSection(6);
        course4.setDayOfWeek(3);
        course4.setStartWeek(1);
        course4.setEndWeek(12);
        list.add(course4);


        CourseModel course5 = new CourseModel();
        course5.setCname("Thuật toán ứng dụng");
        course5.setClassroom("D6-101");
        course5.setStartSection(7);
        course5.setEndSection(12);
        course5.setDayOfWeek(4);
        course5.setStartWeek(1);
        course5.setEndWeek(12);
        list.add(course5);


        CourseModel course6 = new CourseModel();
        course6.setCname("Tương tác người máy");
        course6.setClassroom("D6-101");
        course6.setStartSection(4);
        course6.setEndSection(6);
        course6.setDayOfWeek(5);
        course6.setStartWeek(1);
        course6.setEndWeek(12);
        list.add(course6);

        CourseModel course7 = new CourseModel();
        course7.setCname("Ghép nối máy tính");
        course7.setClassroom("D6-405");
        course7.setStartSection(8);
        course7.setEndSection(12);
        course7.setDayOfWeek(5);
        course7.setStartWeek(1);
        course7.setEndWeek(12);
        list.add(course7);

        return list;
    }

}


