package hust.edu.vn.timem.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.github.tlaabs.timetableview.Schedule;

import java.util.ArrayList;
import java.util.List;

public class SQLiteTimeTable  extends SQLiteOpenHelper {

    private static final String TAG = SQLiteTimeTable.class.getSimpleName();
    // Versi Database
    private static final int DATABASE_VERSION = 1;

    // Nama Database
    private static final String DATABASE_NAME = "TimeTableManager.db";

    // Nama Table
    private static final String TABLE_TIMETABLE = "timetable";

    // Kolom - kolom table
    public static final String KEY_ID_SUBJECT = "sub_id";
    public static final String KEY_SUBJECT = "subject";
    public static final String KEY_CLASSROOM = "classroom";
    public static final String KEY_PROFESSOR = "professor";


    // Nama Table
    private static final String TABLE_CLASS = "Class";

    // Kolom - kolom table
    public static final String KEY_ID_CLASS = "class_id";
    public static final String KEY_TIME_START_H = "time_start_h";
    public static final String KEY_TIME_START_M = "time_start_m";
    public static final String KEY_TIME_THU = "thu";
    public static final String KEY_TIME_END_H = "time_end_h";
    public static final String KEY_TIME_END_M = "time_end_m";
    public SQLiteTimeTable(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DB_TABLETABLE = "CREATE TABLE " + TABLE_TIMETABLE + "("
                + KEY_ID_SUBJECT + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + KEY_SUBJECT + " TEXT,"
                + KEY_CLASSROOM + " TEXT,"
                + KEY_PROFESSOR + " TEXT"
                + ")";
        String CREATE_DB_CLASS = "CREATE TABLE " + TABLE_CLASS + "("
                + KEY_ID_CLASS + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + KEY_TIME_THU + " INTEGER,"
                + KEY_TIME_START_H + " TEXT,"
                + KEY_TIME_START_M + " TEXT,"
                + KEY_TIME_END_H + " TEXT, "
                + KEY_TIME_END_M + " TEXT, "
                + KEY_ID_SUBJECT + " INTEGER NOT NULL CONSTRAINT "+ KEY_ID_SUBJECT
                + " REFERENCES "+ TABLE_TIMETABLE + "( "
                + KEY_ID_SUBJECT + " ) ON DELETE CASCADE ) ";

        db.execSQL(CREATE_DB_TABLETABLE);
        db.execSQL(CREATE_DB_CLASS);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIMETABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLASS);
        onCreate(db);
    }
    public List<Schedule> getData() {
        List<Schedule> list_timetable = new ArrayList<>();
        String USER_DETAIL_SELECT_QUERY = "SELECT * FROM " + TABLE_TIMETABLE +" AS T, "+
                TABLE_CLASS +" AS C WHERE T."+KEY_ID_SUBJECT +" = "+ "C."+KEY_ID_SUBJECT;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(USER_DETAIL_SELECT_QUERY, null);
        try {do{
                if (cursor.moveToFirst()) {
                    Schedule s = new Schedule();
                    s.setClassTitle(cursor.getString(cursor.getColumnIndex(KEY_SUBJECT)));
                    s.setClassPlace(cursor.getString(cursor.getColumnIndex(KEY_CLASSROOM)));
                    s.setProfessorName(cursor.getString(cursor.getColumnIndex(KEY_PROFESSOR)));
                    s.setDay(cursor.getInt(cursor.getColumnIndex(KEY_TIME_THU)));
                    s.getStartTime().setHour(cursor.getInt(cursor.getColumnIndex(KEY_TIME_START_H)));
                    s.getEndTime().setHour(cursor.getInt(cursor.getColumnIndex(KEY_TIME_END_H)));
                    s.getStartTime().setMinute(cursor.getInt(cursor.getColumnIndex(KEY_TIME_START_M)));
                    s.getEndTime().setMinute(cursor.getInt(cursor.getColumnIndex(KEY_TIME_END_M)));
                    list_timetable.add(s);
                }
            }while (cursor.moveToNext());
        } catch (Exception e) {
            Log.d(TAG, "error bro");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return list_timetable;
    }
    public void add(Schedule model){
        SQLiteDatabase db = this.getWritableDatabase();

        int key = checkTitleClass(model.getClassTitle());
        if(key==-1){
            ContentValues values = new ContentValues();
            values.put(KEY_SUBJECT, model.getClassTitle());
            values.put(KEY_CLASSROOM, model.getClassPlace());
            values.put(KEY_PROFESSOR, model.getProfessorName());
            db.insert(TABLE_TIMETABLE, null, values);
            key = checkTitleClass(model.getClassTitle());
        }
        ContentValues values = new ContentValues();
        values.put(KEY_TIME_THU, model.getDay());
        values.put(KEY_TIME_START_H, model.getStartTime().getHour());
        values.put(KEY_TIME_START_M, model.getStartTime().getMinute());
        values.put(KEY_TIME_END_H, model.getEndTime().getHour());
        values.put(KEY_TIME_END_M, model.getEndTime().getMinute());
        values.put(KEY_ID_SUBJECT, key);
        db.insert(TABLE_CLASS, null, values);
    }


    int checkTitleClass(String title){
        SQLiteDatabase sqldb = this.getReadableDatabase();
        String Query = "SELECT * FROM " + TABLE_TIMETABLE + " WHERE "+ KEY_SUBJECT +" ='" + title + "'";
        Cursor cursor = sqldb.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return -1;
        }
        int key = cursor.getInt(cursor.getColumnIndex(KEY_ID_SUBJECT));
        cursor.close();
        return key;
    }


     public void deleteItemSelected(int key) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            db.execSQL("DELETE from " + TABLE_CLASS + " WHERE "+KEY_ID_CLASS+ " ='" + key + "'");
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            Log.d(TAG, "Error while trying to delete  a shedule");
        } finally {
            db.endTransaction();
        }
    }

    public void update(Schedule schedule, int key_id_class) {

        deleteItemSelected(key_id_class);
        int key = checkTitleClass(schedule.getClassTitle());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SUBJECT, schedule.getClassTitle());
        values.put(KEY_CLASSROOM, schedule.getClassPlace());
        values.put(KEY_PROFESSOR, schedule.getProfessorName());
        db.update(TABLE_TIMETABLE, values, KEY_ID_SUBJECT + " = ?",
                new String[]{String.valueOf(key)});

        ContentValues value2 = new ContentValues();
        value2.put(KEY_TIME_THU,schedule.getDay());
        value2.put(KEY_TIME_START_H, schedule.getStartTime().getHour());
        value2.put(KEY_TIME_START_M, schedule.getStartTime().getMinute());
        value2.put(KEY_TIME_END_H, schedule.getEndTime().getHour());
        value2.put(KEY_TIME_END_M, schedule.getEndTime().getMinute());
        value2.put(KEY_ID_SUBJECT, key);
        db.insert(TABLE_CLASS, null, value2);
        db.close();
    }


}
