package com.getingstarted.pogba.mydatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHandler extends SQLiteOpenHelper {

    //All Static variables

    //Database Version
    private static final int DATABASE_VERSION = 1;

    //Database name
    private static final String DATABASE_NAME = "todo.db";

    //Todo Table name
    private static final String TABLE_TODO = "todo";

    //Todo Table Columns names
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_ISDONE = "isDone";
    private static final String COLUMN_ISFAVOURITE = "isFavourite";
    private static final String COLUMN_DATE = "date";

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    //Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TODO_TABLE = "CREATE TABLE" + TABLE_TODO + "("
                + COLUMN_ID + "INTEGER PRIMARY KEY,"
                + COLUMN_NAME + "TEXT,"
                + COLUMN_DESCRIPTION + "TEXT,"
                + COLUMN_ISDONE + "INTEGER,"
                + COLUMN_ISFAVOURITE + "INTEGER,"
                + COLUMN_DATE + "INTEGER" + ")";


    }

    //Updating the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop table if existed
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_TODO);

        //Create tables again
        onCreate(db);

    }

    //Adding Todo
    public  void addTodo(){

    }
}
