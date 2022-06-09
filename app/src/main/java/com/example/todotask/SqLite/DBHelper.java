package com.example.todotask.SqLite;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.todotask.LoginActivity;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    static  SQLiteDatabase MyDB;
    public static final String DBNAME = "Login.db";
    public DBHelper(Context context) {

        super(context, "Login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
      MyDB.execSQL("create Table users(username TEXT primary key, password TEXT, firstname TEXT, lastname TEXT)");
      MyDB.execSQL("create Table if not exists Todo(id INTEGER primary key AUTOINCREMENT, task TEXT, description TEXT, date TEXT,name TEXT)");
           //MyDB = getWritableDatabase();
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
        MyDB.execSQL("drop Table if exists Todo");
         //  MyDB = getWritableDatabase();
        onCreate(MyDB);
    }


    public Boolean insertData(String email, String password,String firstname,String lastname){
        MyDB = getWritableDatabase();
        Log.d(TAG, "insertData: "+MyDB.isOpen());
        ContentValues contentValues= new ContentValues();
        contentValues.put("username", email);
        contentValues.put("firstname", firstname);
        contentValues.put("lastname", lastname);
        contentValues.put("password", password);
        long result = MyDB.insert("users", null, contentValues);

        if(result==-1) return false;
        else
            return true;

    }
    public boolean insertTask(String taskName, String taskDes, String taskdate ,String name) {

        Log.d(TAG, "insertTask: "+ LoginActivity.DB.getWritableDatabase());
        MyDB = LoginActivity.DB.getWritableDatabase();
        if (MyDB.isOpen()){



            ContentValues contentValues = new ContentValues();

            contentValues.put("task", taskName);
            contentValues.put("description", taskDes);
            contentValues.put("date", taskdate);
            contentValues.put("name", name);
            long result = MyDB.insert("Todo", null, contentValues);

            if (result == -1) return false;
            else {
                return true;

            }
        }
        return false;
    }
//    public Boolean insertTask(String taskName, String taskDes, String taskdate) {
//         MyDB = this.getWritableDatabase();
//
//        // MyDB.execSQL("insert into  Tasks(task,description,date) values(taskName=?,taskDes=?,taskdate=?)", new String[] {taskName,taskDes,taskdate});
//        try (Cursor cursor = MyDB.rawQuery("insert into  Tasks(task,description,date) values(?,?,?);", new String[]{taskName, taskDes, taskdate})) {
//            Log.d(TAG, "insertTask: "+cursor.toString());
//            if (cursor.getCount() > 0)
//                return true;
//            else
//                return false;
//        } catch (Exception e) {
//
//           return true;
//
//
//        }
//
//    }


    public Boolean checkusername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();

        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkusernamepassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();

        Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[] {username,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }


    public ArrayList<String> getInfo(String username) {
        Log.d(TAG, "getInfo11111111: "+username);
        MyDB = LoginActivity.DB.getReadableDatabase();
        ArrayList<String> temp = new ArrayList<>();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM users WHERE username = ?", new String[] {username});


        if (cursor.moveToFirst()){
            do {
                // Passing values
                temp.add(cursor.getString(0));
                temp.add(cursor.getString(2));
                temp.add(cursor.getString(3));
                // Do something Here with values
            } while(cursor.moveToNext());
        }


        Log.d(TAG, "getInfo: "+temp.toString());

        return temp;
    }
}