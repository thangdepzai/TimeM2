package hust.edu.vn.timem.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;


public class TTDBProvider extends ContentProvider {
    static final public String DB_DATA_TABLE = "DBdata";
    static final public String DB_LECTURE_TABLE = "Lecture";
    static final public String DB_LECTURE_TIME_TABLE = "Time";
    static final public String DB_LECTURE_NOTE_TABLE = "Note";

    static final public String AUTHORITY = "TimeM.hust.edu.vn";

    static final public Uri DB_DATA_CONTENT_URI =
            Uri.parse("content://" + AUTHORITY + "/" + DB_DATA_TABLE);
    static final public Uri LECTURE_TABLE_CONTENT_URI =
            Uri.parse("content://" + AUTHORITY + "/" + DB_LECTURE_TABLE);
    static final public Uri TIME_TABLE_CONTENT_URI =
            Uri.parse("content://" + AUTHORITY + "/" + DB_LECTURE_TIME_TABLE);
    static final public Uri NOTE_TABLE_CONTENT_URI =
            Uri.parse("content://" + AUTHORITY + "/" + DB_LECTURE_NOTE_TABLE);

    static final int DB_DATA_TABLE_GETALL = 1;
    static final int DB_DATA_TABLE_GETONE = 2;
    static final int TIME_TABLE_GETALL = 3;
    static final int TIME_TABLE_GETONE = 4;
    static final int TIME_TABLE_TIME_GETALL = 5;
    static final int TIME_TABLE_TIME_GETONE = 6;
    static final int TIME_TABLE_NOTE_GETALL = 7;
    static final int TIME_TABLE_NOTE_GETONE = 8;

    // Fore Database Data Table
    static final public String DB_DATA_ID = "_id";
    static final public String DB_DATA_NAME = "DBName";
    static final public String DB_DATA_DATE = "DBDate";
    static final public String DB_DATA_SEMESTER = "DBSemester";

    // For Lecture Table
    static final public String LECTURE_ID = "_id";
    static final public String LECTURE_MAJOR = "lectureMajor";
    static final public String LECTURE_NAME = "lectureName";
    static final public String LECTURE_ROOM = "lectureRoom";
    static final public String LECTURE_PROFESSOR = "professor";
    static final public String LECTURE_INFORMATION = "lectureInformation";
    static final public String LECTURE_DB_FK = "lectureDBFK";

    // For Lecture Time Table
    static final public String LECTURE_TIME_ID = "_id";
    static final public String LECTURE_TIME_DAY = "lectureDay";
    static final public String LECTURE_TIME_START_TIME = "lectureStartTime";
    static final public String LECTURE_TIME_END_TIME = "lectureEndTime";
    static final public String LECTURE_TIME_LECTURE_FK = "lectureTimeFK";

    // For Lecture Note Table
    static final public String LECTURE_NOTE_ID = "_id";
    static final public String LECTURE_NOTE_CONTENT = "lectureNoteContent";
    static final public String LECTURE_NOTE_TIME = "lectureNoteTime";
    static final public String LECTURE_NOTE_LECTURE_FK = "lectureNoteFK";

    static final UriMatcher matcher;
    static {
        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(AUTHORITY, DB_DATA_TABLE, DB_DATA_TABLE_GETALL);
        matcher.addURI(AUTHORITY, DB_DATA_TABLE + "/*", DB_DATA_TABLE_GETONE);

        matcher.addURI(AUTHORITY, DB_LECTURE_TABLE, TIME_TABLE_GETALL);
        matcher.addURI(AUTHORITY, DB_LECTURE_TABLE + "/*", TIME_TABLE_GETONE);

        matcher.addURI(AUTHORITY, DB_LECTURE_TIME_TABLE, TIME_TABLE_TIME_GETALL);
        matcher.addURI(AUTHORITY, DB_LECTURE_TIME_TABLE + "/*", TIME_TABLE_TIME_GETONE);

        matcher.addURI(AUTHORITY, DB_LECTURE_NOTE_TABLE, TIME_TABLE_NOTE_GETALL);
        matcher.addURI(AUTHORITY, DB_LECTURE_NOTE_TABLE + "/*", TIME_TABLE_NOTE_GETONE);
    }

    SQLiteDatabase timeTableDB;
    class timeTableDBHelper extends SQLiteOpenHelper {
        public timeTableDBHelper(Context c) {
            super(c, TTData.TIME_TABLE_NAME + ".tt", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + DB_LECTURE_NOTE_TABLE
                    + " (" +  LECTURE_NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + LECTURE_NOTE_CONTENT+ " TEXT, "
                    + LECTURE_NOTE_TIME + " INTEGER, "
                    + LECTURE_NOTE_LECTURE_FK + " INTEGER, "
                    + "FOREIGN KEY (" + LECTURE_NOTE_LECTURE_FK + ") REFERENCES "
                    + DB_LECTURE_TABLE + "(" + LECTURE_ID +"));");

            db.execSQL("CREATE TABLE IF NOT EXISTS " + DB_LECTURE_TIME_TABLE
                    + " (" +  LECTURE_TIME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + LECTURE_TIME_DAY + " INTEGER, "
                    + LECTURE_TIME_START_TIME + " INTEGER, "
                    + LECTURE_TIME_END_TIME + " INTEGER, "
                    + LECTURE_TIME_LECTURE_FK + " INTEGER, "
                    + "FOREIGN KEY (" + LECTURE_TIME_LECTURE_FK + ") REFERENCES "
                    + DB_LECTURE_TABLE + "(" + LECTURE_ID +"));");

            db.execSQL("CREATE TABLE IF NOT EXISTS " + DB_LECTURE_TABLE
                    + " (" + LECTURE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + LECTURE_MAJOR + " TEXT, "
                    + LECTURE_NAME + " TEXT, "
                    + LECTURE_ROOM + " TEXT, "
                    + LECTURE_PROFESSOR + " TEXT, "
                    + LECTURE_INFORMATION + " TEXT, "
                    + LECTURE_DB_FK + " INTEGER, "
                    + "FOREIGN KEY (" + LECTURE_DB_FK + ") REFERENCES "
                    + DB_DATA_TABLE + "(" + DB_DATA_ID+"));)");

            db.execSQL("CREATE TABLE IF NOT EXISTS " + DB_DATA_TABLE
                    + " (" +  DB_DATA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + DB_DATA_NAME+ " TEXT, "
                    + DB_DATA_DATE+ " TEXT, "
                    + DB_DATA_SEMESTER+ " TEXT);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DB_LECTURE_TABLE + ";");
            onCreate(db);
        }
    }

    @Override
    public boolean onCreate() {
        timeTableDBHelper timeTableHelper = new timeTableDBHelper(getContext());

        timeTableDB = timeTableHelper.getWritableDatabase();
        timeTableDB.execSQL("PRAGMA foreign_keys=ON;"); // Foreign Key 사용 허용

        Log.e("DB", timeTableDB.toString());

        return (timeTableDB != null);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int cnt = 0;
        String where;

        switch (matcher.match(uri)) {
            case TIME_TABLE_GETALL:
                cnt = timeTableDB.delete(DB_LECTURE_TABLE, selection, selectionArgs);
                break;

            case TIME_TABLE_TIME_GETALL:
                cnt = timeTableDB.delete(DB_LECTURE_TIME_TABLE, selection, selectionArgs);
                break;

            case TIME_TABLE_NOTE_GETALL:
                cnt = timeTableDB.delete(DB_LECTURE_NOTE_TABLE, selection, selectionArgs);
                break;

            case TIME_TABLE_GETONE:
                where = LECTURE_NAME + " = '" + uri.getPathSegments().get(1) + "'";
                if(TextUtils.isEmpty(selection) == false) {
                    where += " AND " + selection;
                }
                cnt = timeTableDB.delete(DB_LECTURE_TABLE, where, selectionArgs);
                break;

            case TIME_TABLE_TIME_GETONE:
                where = LECTURE_NAME + " = '" + uri.getPathSegments().get(1) + "'";
                if(TextUtils.isEmpty(selection) == false) {
                    where += " AND " + selection;
                }
                cnt = timeTableDB.delete(DB_LECTURE_TIME_TABLE, where, selectionArgs);
                break;

            case TIME_TABLE_NOTE_GETONE:
                where = LECTURE_NAME + " = '" + uri.getPathSegments().get(1) + "'";
                if(TextUtils.isEmpty(selection) == false) {
                    where += " AND " + selection;
                }
                cnt = timeTableDB.delete(DB_LECTURE_NOTE_TABLE, where, selectionArgs);
                break;
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return cnt;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String sql = null;

        switch(matcher.match(uri)) {
            case DB_DATA_TABLE_GETALL:
            case TIME_TABLE_GETALL:
            case TIME_TABLE_TIME_GETALL:
            case TIME_TABLE_NOTE_GETALL:
                Log.e("SELECT * FROM", uri.getPathSegments().get(0));
                sql = "SELECT * FROM ";
                sql += uri.getPathSegments().get(0);
                break;

            case DB_DATA_TABLE_GETONE:
            case TIME_TABLE_GETONE:
            case TIME_TABLE_TIME_GETONE:
            case TIME_TABLE_NOTE_GETONE:
                Log.e("SELECT * FROM ONE", uri.getPathSegments().get(0));
                sql = "SELECT * FROM ";
                sql += uri.getPathSegments().get(0);
                sql += " WHERE " + selection + " = '";
                sql += selectionArgs[0];
                sql += "'";
                break;
        }

        if(sortOrder != null) {
            sql += sortOrder;
        }

        sql += ";";

        Cursor cur = timeTableDB.rawQuery(sql, null);
        return cur;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (matcher.match(uri)) {
            case TIME_TABLE_GETALL:
                return "vnd.android.cursor.dir/vnd.ydksoftwork.lecture";
            case TIME_TABLE_GETONE:
                return "vnd.android.cursor.item/vnd.ydksoftwork.lecture";
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long row = 0;
        Uri notiuri = null;
        switch (matcher.match(uri)) {
            case DB_DATA_TABLE_GETALL:
                row = timeTableDB.insert(DB_DATA_TABLE, null, values);
                notiuri = ContentUris.withAppendedId(DB_DATA_CONTENT_URI, row);
                break;

            case TIME_TABLE_GETALL:
                row = timeTableDB.insert(DB_LECTURE_TABLE, null, values);
                notiuri = ContentUris.withAppendedId(LECTURE_TABLE_CONTENT_URI, row);
                break;

            case TIME_TABLE_TIME_GETALL:
                row = timeTableDB.insert(DB_LECTURE_TIME_TABLE, null, values);
                notiuri = ContentUris.withAppendedId(TIME_TABLE_CONTENT_URI, row);
                break;

            case TIME_TABLE_NOTE_GETALL:
                row = timeTableDB.insert(DB_LECTURE_NOTE_TABLE, null, values);
                notiuri = ContentUris.withAppendedId(NOTE_TABLE_CONTENT_URI, row);
                break;
        }
        if(row > 0) {
            getContext().getContentResolver().notifyChange(notiuri, null);
            return notiuri;
        }
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int cnt = 0;
        String where;

        switch (matcher.match(uri)) {
            case TIME_TABLE_GETALL:
                cnt = timeTableDB.update(DB_LECTURE_TABLE, values, selection, selectionArgs);
                break;

            case TIME_TABLE_TIME_GETALL:
                cnt = timeTableDB.update(DB_LECTURE_TIME_TABLE, values, selection, selectionArgs);
                break;

            case TIME_TABLE_NOTE_GETALL:
                cnt = timeTableDB.update(DB_LECTURE_NOTE_TABLE, values, selection, selectionArgs);
                break;

            case TIME_TABLE_GETONE:
                where = LECTURE_ID + " = '" + uri.getPathSegments().get(1) + "'";
                if(TextUtils.isEmpty(selection) == false) {
                    where += " AND " + selection;
                }
                cnt = timeTableDB.update(DB_LECTURE_TABLE, values, where, selectionArgs);
                break;

            case TIME_TABLE_TIME_GETONE:
                where = LECTURE_TIME_ID + " = '" + uri.getPathSegments().get(1) + "'";
                if(TextUtils.isEmpty(selection) == false) {
                    where += " AND " + selection;
                }
                cnt = timeTableDB.update(DB_LECTURE_TIME_TABLE, values, where, selectionArgs);
                break;

            case TIME_TABLE_NOTE_GETONE:
                where = LECTURE_NOTE_ID + " = '" + uri.getPathSegments().get(1) + "'";
                if(TextUtils.isEmpty(selection) == false) {
                    where += " AND " + selection;
                }
                cnt = timeTableDB.update(DB_LECTURE_NOTE_TABLE, values, where, selectionArgs);
                break;
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return cnt;
    }
}
