package hust.edu.vn.timem.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import hust.edu.vn.timem.Model.UserModel;

public class SQLiteUser extends SQLiteOpenHelper {

    private static final String TAG = SQLiteUser.class.getSimpleName();


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UserManager.db";
    private static final String TABLE_USER = "user";

    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_SDT = "user_sdt";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
    String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT," + COLUMN_USER_SDT + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";

    public SQLiteUser(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_USER_TABLE);
        onCreate(db);
    }

    public List<UserModel> getAllUser() {
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_NAME,
                COLUMN_USER_SDT,
                COLUMN_USER_EMAIL,
                COLUMN_USER_PASSWORD
        };


        String sortOrder = COLUMN_USER_NAME + " ASC";
        List<UserModel> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order
        if (cursor.moveToFirst()) {
            do {
                UserModel user = new UserModel();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setUserName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setSdt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_SDT)));
                user.setMail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassW(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return userList;

    }
    public void updateUser(UserModel user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getUserName());
        values.put(COLUMN_USER_SDT, user.getSdt());
        values.put(COLUMN_USER_EMAIL, user.getMail());
        values.put(COLUMN_USER_PASSWORD, user.getPassW());

        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }
    public void deleteUser(UserModel user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }
    public void addUser(UserModel user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getUserName());
        values.put(COLUMN_USER_EMAIL, user.getMail());
        values.put(COLUMN_USER_PASSWORD, user.getPassW());
        values.put(COLUMN_USER_SDT, user.getSdt());
        db.insert(TABLE_USER, null, values);
        db.close();
    }
    public boolean checkUser(String username, String password) {
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_NAME + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
    public boolean checkUser(String name) {
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_NAME + " = ?";

        String[] selectionArgs = {name};
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
    public boolean checkUserMail(String email) {
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ?";

        String[] selectionArgs = {email};
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
    public boolean checkUserSDT(String Sdt) {
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_SDT + " = ?";

        String[] selectionArgs = {Sdt};
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
    public UserModel getUser(String username, String password) {
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_NAME,
                COLUMN_USER_SDT,
                COLUMN_USER_EMAIL,
                COLUMN_USER_PASSWORD
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_NAME + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        UserModel user = new UserModel();
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setUserName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setSdt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_SDT)));
                user.setMail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassW(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
            }
            cursor.close();
        }

        db.close();
        return user;
    }
    public UserModel getUserSDT(String Sdt) {
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_NAME,
                COLUMN_USER_SDT,
                COLUMN_USER_EMAIL,
                COLUMN_USER_PASSWORD
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_SDT + " = ?";

        String[] selectionArgs = {Sdt};
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        UserModel user = new UserModel();
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setUserName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setSdt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_SDT)));
                user.setMail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassW(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
            }
            cursor.close();
        }

        db.close();
        return user;
    }
    public UserModel getUserMail(String mail) {
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_NAME,
                COLUMN_USER_SDT,
                COLUMN_USER_EMAIL,
                COLUMN_USER_PASSWORD
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ?";

        String[] selectionArgs = {mail};
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        UserModel user = new UserModel();
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setUserName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setSdt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_SDT)));
                user.setMail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassW(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
            }
            cursor.close();
        }

        db.close();
        return user;
    }
}