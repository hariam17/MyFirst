package com.example.myfirst.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.myfirst.model.Student;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "dbKu";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_STD = "students";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_NIM = "nim";
    private static final String CREATE_TABLE_STUDENTS = "CREATE TABLE " +
            TABLE_STD + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_NAME + " TEXT, " + KEY_NIM + " TEXT );";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STUDENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STD);
        onCreate(db);
    }

    public long addUserDetail(String nim, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_NIM, nim);
        long insert = db.insert(TABLE_STD, null, values);
        db.close(); // Tutup koneksi database
        return insert;
    }

    @SuppressLint("Range")
    public ArrayList<Student> getAllUsers() {
        ArrayList<Student> userModelArrayList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_STD;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Student std = new Student();
                std.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                std.setName(c.getString(c.getColumnIndex(KEY_NAME)));
                std.setNim(c.getString(c.getColumnIndex(KEY_NIM)));
                // menambahkan ke daftar Students
                userModelArrayList.add(std);
            } while (c.moveToNext());
        }
        c.close(); // Tutup cursor setelah penggunaan
        db.close(); // Tutup koneksi database
        return userModelArrayList;
    }

    public void deleteUser(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STD, KEY_ID + " = ?", new String[]{String.valueOf(id)});
        db.close(); // Tutup koneksi database
    }

    public void updateUser(int id, String nim, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_NIM, nim);
        db.update(TABLE_STD, values, KEY_ID + " = ?", new String[]{String.valueOf(id)});
        db.close(); // Tutup koneksi database
    }
}
