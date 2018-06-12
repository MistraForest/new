package com.getingstarted.pogba.todosqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHandler extends SQLiteOpenHelper {

   //All Static variables
    public static final String MY_TAG = DataBaseHandler.class.getName();

    //Database Version
    private static final int DATABASE_VERSION = 1;

    //Database name
    private static final String DATABASE_NAME = "todo.db ";

    //Todo Table name
    private static final String TABLE_TODOs = "todo_details ";

    //Todo Table Columns names
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_IS_DONE = "isDone";
    private static final String COLUMN_IS_FAVOURITE = "isFavourite";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TIME = "time";



    public DataBaseHandler(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TODO_TABLE = "CREATE TABLE " + TABLE_TODOs + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_NAME + " TEXT NOT NULL,"
                + COLUMN_DESCRIPTION + " TEXT NOT NULL,"
                + COLUMN_IS_DONE + " INTEGER,"
                + COLUMN_IS_FAVOURITE + " INTEGER,"
                + COLUMN_DATE + " INTEGER NOT NULL,"
                + COLUMN_TIME + " INTEGER NOT NULL" + ")";
      //  try{
            Log.d(MY_TAG," Database is creating...");

            db.execSQL(CREATE_TODO_TABLE);

            Log.d(MY_TAG," Database is created successfully with " + CREATE_TODO_TABLE);

      //  }catch (SQLiteException e){
           // Log.e(MY_TAG," Exception by Database creating: " + e.getLocalizedMessage());
      //  }

    }

    //Updating the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop table if existed
        Log.w(MY_TAG," Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODOs);

        //Create tables again
        onCreate(db);

    }

    //Adding a new Todo
    public void addEntry(MyTodo entry){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, entry.getName());                  // Todo name
        values.put(COLUMN_DESCRIPTION, entry.getDescription());    // Todo description
        values.put(COLUMN_IS_DONE, entry.getIsDone());               // Todo isDone
        values.put(COLUMN_IS_FAVOURITE, entry.getFavourite());     // Todo favourite
        values.put(COLUMN_DATE, entry.getDueDate());               // Todo due-date
        values.put(COLUMN_TIME, entry.getTime());                  // Todo time

        //Inserting Row

        Log.i(MY_TAG,"Inserting todo name "+ entry.getName());
        db.insert(TABLE_TODOs , null, values);

        Log.d(MY_TAG, entry.getName()+"...is inserted");


        db.close();
    }

    // Getting single Todo
    public MyTodo getEntry(int id){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TODOs,
                new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_DESCRIPTION, COLUMN_IS_DONE, COLUMN_IS_FAVOURITE, COLUMN_DATE, COLUMN_TIME},
                COLUMN_ID + "=?", new String[]{ String.valueOf(id)},
                null, null, null,null);

        if (cursor != null)
            cursor.moveToFirst();

        MyTodo entry = new MyTodo(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    Integer.parseInt(cursor.getString(3)),
                    Integer.parseInt(cursor.getString(4)),
                    Integer.parseInt(cursor.getString(5)),
                    Integer.parseInt(cursor.getString(6)));

        return entry;
    }

    //Getting all Todo
    public List<MyTodo> getEntries(){

        List<MyTodo> entryList = new ArrayList<>();

        //Select all Query
        String selectQuery = "SELECT * FROM " + TABLE_TODOs;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //Looping through all rows and adding to list
        if (cursor.moveToFirst()){

            do{
                MyTodo entry = new MyTodo();

                entry.setId(Integer.parseInt(cursor.getString(0)));
                entry.setName(cursor.getString(1));
                entry.setDescription(cursor.getString(2));
                entry.setIsDone(Integer.parseInt(cursor.getString(3)));
                entry.setFavourite(Integer.parseInt(cursor.getString(4)));
                Log.i(MY_TAG, "ready to date");
                entry.setDueDate(Long.parseLong(cursor.getString(5)));
                entry.setTime(Long.parseLong(cursor.getString(6)));

                //Adding Todo to list
                entryList.add(entry);

            }while (cursor.moveToNext());
            //cursor.close();
        }

        return entryList;
    }

    //Getting Entries count
    public int countEntries(){

        String countQuery = "SELECT * FROM " + TABLE_TODOs;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        //cursor.close();
        Log.i(MY_TAG, "Count = "+cursor.getCount());
        return cursor.getCount();

        //cursor.close();
    }

    // updating single Entry
    public int updateEntry(MyTodo entry){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, entry.getName());  //Todo name
        values.put(COLUMN_DESCRIPTION, entry.getDescription()); // Todo description
        values.put(COLUMN_IS_DONE, entry.getIsDone());
        values.put(COLUMN_IS_FAVOURITE, entry.getFavourite());
        values.put(COLUMN_DATE, entry.getDueDate());
        values.put(COLUMN_TIME, entry.getTime());

        //Inserting Row
        return db.update(TABLE_TODOs, values, COLUMN_ID + " = ? ", new String[]{String.valueOf(entry.getId())});
    }

    //deleting single Entry
    public void deleteEntry(MyTodo entry){

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TODOs, COLUMN_ID + " = ? ", new String[]{String.valueOf(entry.getId())});

        db.close();
    }




}
