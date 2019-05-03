package hust.edu.vn.timem.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import hust.edu.vn.timem.Model.CourseModel;

public class SQLiteTimeTable  extends SQLiteOpenHelper {

    private static final String TAG = SQLiteTimeTable.class.getSimpleName();
    // Versi Database
    private static final int DATABASE_VERSION = 1;

    // Nama Database
    private static final String DATABASE_NAME = "TimeTableManager.db";

    // Nama Table
    private static final String TABLE_TIMETABLE = "timetable";

    private String uid;
    private String cname;
    private String schoolYear;
    private String term;
    private float credit;
    private int startSection;
    private int endSection;
    private int startWeek;
    private int endWeek;
    private int dayOfWeek;
    private String classroom;

    public static final String KEY_UID= "uid";
    public static final String KEY_NAME = "cname";
//    public static final String KEY_YEAR = "schoolYear";
//    public static final String KEY_TERM = "term";
//    public static final String KEY_CREDIT = "credit";
    public static final String KEY_START_SECTION = "startSection";
    public static final String KEY_END_SECTION = "endSection";
    public static final String KEY_START_WEEK = "startWeek";
    public static final String KEY_END_WEEK = "endWeek";
    public static final String KEY_DAY_OF_WEEK = "dayOfWeek";
    public static final String KEY_CLASS_ROOM = "classroom";

    public SQLiteTimeTable(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DB_TABLETABLE = "CREATE TABLE " + TABLE_TIMETABLE + "("
                + KEY_UID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + KEY_NAME + " TEXT,"
//                + KEY_YEAR + " TEXT,"
//                + KEY_TERM + " TEXT,"
//                + KEY_CREDIT + " REAL,"
                + KEY_START_SECTION + " INTEGER,"
                + KEY_END_SECTION + " INTEGER,"
                + KEY_START_WEEK + " INTEGER,"
                + KEY_END_WEEK + " INTEGER,"
                + KEY_DAY_OF_WEEK + " INTEGER,"
                + KEY_CLASS_ROOM + " TEXT"
                + ")";
        db.execSQL(CREATE_DB_TABLETABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIMETABLE);
        onCreate(db);
    }
    public List<CourseModel> getData() {
        List<CourseModel> list_timetable = new ArrayList<>();
        String USER_DETAIL_SELECT_QUERY = "SELECT * FROM " + TABLE_TIMETABLE ;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(USER_DETAIL_SELECT_QUERY, null);
        try {do{
                if (cursor.moveToFirst()) {
                    CourseModel s = new CourseModel();
                    s.setUid(cursor.getInt(cursor.getColumnIndex(KEY_UID))+"");
                    s.setCname(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
//                    s.setSchoolYear(cursor.getString(cursor.getColumnIndex(KEY_YEAR)));
//                    s.setTerm(cursor.getString(cursor.getColumnIndex(KEY_TERM)));
//                    s.setCredit(cursor.getFloat(cursor.getColumnIndex(KEY_CREDIT)));
                    s.setStartSection(cursor.getInt(cursor.getColumnIndex(KEY_START_SECTION)));
                    s.setEndSection(cursor.getInt(cursor.getColumnIndex(KEY_END_SECTION)));
                    s.setStartWeek(cursor.getInt(cursor.getColumnIndex(KEY_START_WEEK)));
                    s.setEndWeek(cursor.getInt(cursor.getColumnIndex(KEY_END_WEEK)));
                    s.setDayOfWeek(cursor.getInt(cursor.getColumnIndex(KEY_DAY_OF_WEEK)));
                    s.setClassroom(cursor.getString(cursor.getColumnIndex(KEY_CLASS_ROOM)));
                    list_timetable.add(s);
                }
            }while (cursor.moveToNext());
        } catch (Exception e) {
            Log.d(TAG, "error bro");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            db.close();
        }
        return list_timetable;
    }
    public void add(CourseModel model){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, model.getCname());
//        values.put(KEY_YEAR, model.getSchoolYear());
//        values.put(KEY_TERM, model.getTerm());
//        values.put(KEY_CREDIT, model.getCredit());
        values.put(KEY_START_SECTION, model.getStartSection());
        values.put(KEY_END_SECTION, model.getEndSection());
        values.put(KEY_START_WEEK, model.getStartWeek());
        values.put(KEY_END_WEEK, model.getEndWeek());
        values.put(KEY_DAY_OF_WEEK, model.getDayOfWeek());
        values.put(KEY_CLASS_ROOM, model.getClassroom());
        db.insert(TABLE_TIMETABLE, null, values);
        db.close();
    }




     public void deleteItemSelected(int key) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            db.execSQL("DELETE from " + TABLE_TIMETABLE + " WHERE "+KEY_UID+ " ='" + key + "'");
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            Log.d(TAG, "Error while trying to delete  a shedule");
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public void update(CourseModel model, int key_id_class) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, model.getCname());
//        values.put(KEY_YEAR, model.getSchoolYear());
//        values.put(KEY_TERM, model.getTerm());
//        values.put(KEY_CREDIT, model.getCredit());
        values.put(KEY_START_SECTION, model.getStartSection());
        values.put(KEY_END_SECTION, model.getEndSection());
        values.put(KEY_START_WEEK, model.getStartWeek());
        values.put(KEY_END_WEEK, model.getEndWeek());
        values.put(KEY_DAY_OF_WEEK, model.getDayOfWeek());
        values.put(KEY_CLASS_ROOM, model.getClassroom());
        db.update(TABLE_TIMETABLE, values, KEY_UID + " = ?",
                new String[]{String.valueOf(key_id_class)});
        db.close();
    }


}
