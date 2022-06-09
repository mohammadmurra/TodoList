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
import com.example.todotask.Todo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

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

    public ArrayList<Todo> AllTask(String username) {
        // on below line we are creating a
        // database for reading our database.
        MyDB = LoginActivity.DB.getReadableDatabase();
        // on below line we are creating a cursor with query to read data from database.
           // public Todo(String id, String userName, String decrption, String name, String date) {

            Cursor cursorCourses = MyDB.rawQuery("SELECT * FROM Todo WHERE name = ?", new String[] {username});

        // on below line we are creating a new array list.
        ArrayList<Todo> courseModalArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorCourses.moveToFirst()) {
            do {  //  public Todo(String id, String userName, String decrption, String name, String date) {

                // on below line we are adding the data from cursor to our array list.
                courseModalArrayList.add(new Todo(cursorCourses.getString(0),cursorCourses.getString(4),cursorCourses.getString(2),cursorCourses.getString(1),cursorCourses.getString(3)));


            } while (cursorCourses.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorCourses.close();
        return courseModalArrayList;
    }
    public ArrayList<Todo> Todayask(String user) {
        // on below line we are creating a
        // database for reading our database.
        MyDB = LoginActivity.DB.getReadableDatabase();
        String dates = "";
        Date currentTime = Calendar.getInstance().getTime();
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        dates=dateFormat.format(currentTime.getTime());


        // on below line we are creating a cursor with query to read data from database.
        // public Todo(String id, String userName, String decrption, String name, String date) {
        Cursor cursorCourses = MyDB.rawQuery("SELECT * FROM Todo WHERE date  = ? AND name = ?",  new String[] {dates,user});


        // on below line we are creating a new array list.
        ArrayList<Todo> courseModalArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorCourses.moveToFirst()) {
            do {  //  public Todo(String id, String userName, String decrption, String name, String date) {

                // on below line we are adding the data from cursor to our array list.
                courseModalArrayList.add(new Todo(cursorCourses.getString(0),cursorCourses.getString(4),cursorCourses.getString(2),cursorCourses.getString(1),cursorCourses.getString(3)));


            } while (cursorCourses.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorCourses.close();
        return courseModalArrayList;
    }
    public ArrayList<Todo> weakTask(String user) {
        // on below line we are creating a
        // database for reading our database.
        String date = "";
        Date currentTime = Calendar.getInstance().getTime();
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        date=dateFormat.format(currentTime.getTime());

        Calendar c = Calendar.getInstance();
        int currentWeekNumber = c.get(Calendar.WEEK_OF_YEAR);

        SimpleDateFormat timeStampFormat = new SimpleDateFormat("MM/dd/yy" ,Locale.US);
        c.set(Calendar.DAY_OF_WEEK, 7);
        String endDate = timeStampFormat.format(c.getTime());

        MyDB = LoginActivity.DB.getReadableDatabase();



        // on below line we are creating a cursor with query to read data from database.
        // public Todo(String id, String userName, String decrption, String name, String date) {
        Cursor cursorCourses = MyDB.rawQuery("SELECT * FROM Todo WHERE   name = ?",  new String[] {user});


        // on below line we are creating a new array list.
        ArrayList<Todo> courseModalArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorCourses.moveToFirst()) {
            do {  //  public Todo(String id, String userName, String decrption, String name, String date) {
if ((endDate.compareTo(cursorCourses.getString(3))>=0)&&(date.compareTo(cursorCourses.getString(3))<=0))
                courseModalArrayList.add(new Todo(cursorCourses.getString(0),cursorCourses.getString(4),cursorCourses.getString(2),cursorCourses.getString(1),cursorCourses.getString(3)));


            } while (cursorCourses.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorCourses.close();
        Collections.sort(courseModalArrayList);
        return courseModalArrayList;
    }
    public ArrayList<Todo> searchTask(String user,String start , String end) {



        MyDB = LoginActivity.DB.getReadableDatabase();

//        Log.d(TAG, "Todayask: "+endDate + " ssss" + date);

        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorCourses = MyDB.rawQuery("SELECT * FROM Todo WHERE   name = ?",  new String[] {user});


        // on below line we are creating a new array list.
        ArrayList<Todo> courseModalArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorCourses.moveToFirst()) {
            do {  //  public Todo(String id, String userName, String decrption, String name, String date) {
                if ((end.compareTo(cursorCourses.getString(3))>=0)&&(start.compareTo(cursorCourses.getString(3))<=0))
                    courseModalArrayList.add(new Todo(cursorCourses.getString(0),cursorCourses.getString(4),cursorCourses.getString(2),cursorCourses.getString(1),cursorCourses.getString(3)));


            } while (cursorCourses.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorCourses.close();
        Collections.sort(courseModalArrayList);
        return courseModalArrayList;
    }
}