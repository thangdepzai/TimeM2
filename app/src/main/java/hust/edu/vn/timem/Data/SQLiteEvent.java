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

import hust.edu.vn.timem.Model.EventModel;
import hust.edu.vn.timem.UserPreference;

public class SQLiteEvent extends SQLiteOpenHelper {
    private static final String TAG = SQLiteEvent.class.getSimpleName();
    // Versi Database
    private static final int DATABASE_VERSION = 1;

    // Nama Database
    private static final String DATABASE_NAME = "EventManager";

    // Kolom - kolom table
    public static final String KEY_ID = "_id";
    public static final String KEY_TIME = "time";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MOTA = "mota";

    // Nama Table
    private static final String TABLE_EVENT = "event";
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_EVENT + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + KEY_TIME + " DATE,"
                + KEY_TITLE + " TEXT,"
                + KEY_MOTA+ " TEXT"
                + ")";
        db.execSQL(CREATE_LOGIN_TABLE);
        Log.i(TAG, "da tao xong bang");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENT);
        onCreate(db);
    }
    public SQLiteEvent(Context context) {
        super(context, DATABASE_NAME+ ""+ UserPreference.getUserPreference(context).getUserName(), null, DATABASE_VERSION);

    }

    public void addData(EventModel model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TIME, model.getTime());
        values.put(KEY_TITLE,model.getTitle());
        values.put(KEY_MOTA,model.getMota());
        db.insert(TABLE_EVENT, null, values);
        db.close();
    }
    public List<EventModel> getData() {
        List<EventModel> usersdetail = new ArrayList<>();
        String USER_DETAIL_SELECT_QUERY = "SELECT * FROM " + TABLE_EVENT;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(USER_DETAIL_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    EventModel itemObject = new EventModel();
                    itemObject.setTime(cursor.getString(cursor.getColumnIndex(KEY_TIME)));
                    itemObject.setTitle(cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
                    itemObject.setMota(cursor.getString(cursor.getColumnIndex(KEY_MOTA)));
                    usersdetail.add(itemObject);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "error bro");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            db.close();
        }
        return usersdetail;
    }
    public List<EventModel> getDataTime(String time) {
        List<EventModel> usersdetail = new ArrayList<>();
        String USER_DETAIL_SELECT_QUERY = "SELECT * FROM " + TABLE_EVENT +"WHERE " +KEY_TIME+" = "+time;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(USER_DETAIL_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    EventModel itemObject = new EventModel();
                    itemObject.setTime(cursor.getString(cursor.getColumnIndex(KEY_TIME)));
                    itemObject.setTitle(cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
                    itemObject.setMota(cursor.getString(cursor.getColumnIndex(KEY_MOTA)));
                    usersdetail.add(itemObject);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "error bro");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            db.close();
        }
        return usersdetail;
    }
    public void deleteItemSelected(String time) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            db.execSQL("DELETE from " + TABLE_EVENT + " WHERE time ='" + time + "'");
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            Log.d(TAG, "Error while trying to delete  users detail");
        } finally {
            db.endTransaction();
            db.close();
        }
    }

}
