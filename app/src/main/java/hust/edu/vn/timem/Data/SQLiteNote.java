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

import hust.edu.vn.timem.Model.NoteModel;

public class SQLiteNote extends SQLiteOpenHelper {

        private static final String TAG = SQLiteNote.class.getSimpleName();
        // Versi Database
        private static final int DATABASE_VERSION = 1;

        // Nama Database
        private static final String DATABASE_NAME = "sttnf_result";

        // Nama Table
        private static final String TABLE_NOTE = "tbl_note_daeng";

        // Kolom - kolom table
        public static final String KEY_ID = "_id";
        public static final String KEY_TIME = "time";
        public static final String KEY_TITLE = "title";
        public static final String KEY_MOTA = "mota";


    public SQLiteNote(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_NOTE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + KEY_TIME + " TEXT,"
                + KEY_TITLE + " TEXT,"
                + KEY_MOTA+ " TEXT"
               + ")";
        sqLiteDatabase.execSQL(CREATE_LOGIN_TABLE);
        Log.i(TAG, "da tao xong bang");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);
        onCreate(sqLiteDatabase);
    }

    public void addData(String time, String title, String mota) {
        SQLiteDatabase db = this.getWritableDatabase();

        if (!CheckData(title)) {
            ContentValues values = new ContentValues();
            values.put(KEY_TIME, time);
            values.put(KEY_TITLE, title);
            values.put(KEY_MOTA, mota);


            db.insert(TABLE_NOTE, null, values);
        } else {
            try {
                db.beginTransaction();
                db.execSQL("UPDATE " + TABLE_NOTE +
                        " SET time='" + time + "', title='" + title + "', mota='" + mota +
                        " WHERE title ='" + title + "'");
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        }

        db.close();
    }

    public List<NoteModel> getData() {
        List<NoteModel> usersdetail = new ArrayList<>();
        String USER_DETAIL_SELECT_QUERY = "SELECT * FROM " + TABLE_NOTE;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(USER_DETAIL_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    NoteModel itemObject = new NoteModel();
                    itemObject.time = cursor.getString(cursor.getColumnIndex(KEY_TIME));
                    itemObject.title = cursor.getString(cursor.getColumnIndex(KEY_TITLE));
                    itemObject.mota = cursor.getString(cursor.getColumnIndex(KEY_MOTA));

                    usersdetail.add(itemObject);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "error bro");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return usersdetail;
    }

    public boolean CheckData(String title) {
        SQLiteDatabase sqldb = this.getReadableDatabase();
        String Query = "SELECT * FROM " + TABLE_NOTE + " WHERE title ='" + title + "'";
        Cursor cursor = sqldb.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public void deleteItemSelected(String time) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            db.execSQL("DELETE from " + TABLE_NOTE + " WHERE time ='" + time + "'");
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            Log.d(TAG, "Error while trying to delete  users detail");
        } finally {
            db.endTransaction();
        }
    }

}
