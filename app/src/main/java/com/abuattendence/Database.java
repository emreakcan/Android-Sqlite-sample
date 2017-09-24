package com.abuattendence;

/**
 * Created by emre on 24.09.2017.
 */


import java.util.ArrayList;
import java.util.HashMap;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "sqllite_database";

    private static final String TABLE_NAME = "student_list";
    private static String STUDENT_NAME = "student_name";
    private static String STUDENT_ID = "id";


    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + STUDENT_NAME + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }




    public void addStudent(String student_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STUDENT_NAME, student_name);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }



    public  ArrayList<HashMap<String, String>> students(){

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<HashMap<String, String>> studentlist = new ArrayList<HashMap<String, String>>();

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                for(int i=0; i<cursor.getColumnCount();i++)
                {
                    map.put(cursor.getColumnName(i), cursor.getString(i));
                }

                studentlist.add(map);
            } while (cursor.moveToNext());
        }
        db.close();
        return studentlist;
    }



    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }

}
