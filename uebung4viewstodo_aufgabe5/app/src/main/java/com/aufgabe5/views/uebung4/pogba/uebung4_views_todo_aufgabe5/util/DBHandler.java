package com.aufgabe5.views.uebung4.pogba.uebung4_views_todo_aufgabe5.util;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;
import com.aufgabe5.views.uebung4.pogba.uebung4_views_todo_aufgabe5.model.Todo_Simple;

import java.util.Locale;

public class DBHandler {

    private final Activity activity;

    protected static final String logger = DBHandler.class
            .getName();

    private static final String DB_NAME = "todos.db";

    /**
     * the initial version of the db based on which we decide whether to create
     * the table or not
     */
    private static final String TABLE_NAME = "todo_items";

    /**
     * the table name
     */
    protected static final int INITIAL_DB_VERSION = 0;

    /**
     * the column names
     *
     * the _id column follows the convention required by the CursorAdapter usage
     */
    public static final String COL_ID = "_id";
    public static final String COL_NAME = "name";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_DONE = "doneORnot";
    public static final String COL_FAVOURITE = "favourite";
    public static final String COL_DATE = "date";

    /**
     * the creation query
     */
    private static final String TABLE_CREATION_QUERY = "CREATE TABLE "
            + TABLE_NAME + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + COL_NAME + " TEXT,\n" + COL_DESCRIPTION + " TEXT,\n"
            + COL_DONE + " TEXT,\n" + COL_FAVOURITE + " TEXT,\n"
            + COL_DATE + " INTEGER);";

    /**
     * the where clause for item deletion
     */
    private static final String WHERE_IDENTIFY_ITEM = COL_ID + "=?";

    /**
     * the database
     */
    SQLiteDatabase database;



    public DBHandler(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }

    /**
     * Prepare the Database
     */
    public void initDatabase(){

        this.database = getActivity().openOrCreateDatabase(
                DB_NAME, SQLiteDatabase.OPEN_READWRITE, null
        );

        // check DB if it's empty or not...

        Log.i(logger,"db version is " + database.getVersion());
        if (this.database.getVersion() == INITIAL_DB_VERSION){
            database.setLocale(Locale.getDefault());
            database.setVersion(INITIAL_DB_VERSION + 1);
            database.execSQL(TABLE_CREATION_QUERY);
        } else {
            Log.i(logger,"The db exists already. " +
                    "No need for table creation.");
        }
    }

    /**
     * create a ContentValues object which can be passed to a db query
     *
     * @param item
     * @return ContentValues
     */
    public static ContentValues createDBTodoItem(Todo_Simple item){

        ContentValues values = new ContentValues();

        values.put(COL_NAME, item.getName());                  // Todo name
        values.put(COL_DESCRIPTION, item.getDescription());    // Todo description
        values.put(COL_DONE, item.getIsDone());               // Todo isDone
        values.put(COL_FAVOURITE, item.getIsFavourite());     // Todo favourite
        values.put(COL_DATE, item.getDueDate());               // Todo due-date
        //values.put(COL_TIME, item.getTime());                  // Todo time

        return values;
    }

    // Cursor for DB
    public Cursor getCursor(){

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TABLE_NAME);

        String[] columnToReturn = {COL_ID, COL_NAME, COL_DESCRIPTION
                ,COL_DONE, COL_FAVOURITE, COL_DATE};

        String ordering = COL_ID +" ASC";
        Cursor cursor = queryBuilder.query(
                this.database, columnToReturn,
                null, null,
                null, null, ordering
        );

        return cursor;
    }

    public Todo_Simple createTodoFromCursor(Cursor cursor){

        Todo_Simple todoItem = new Todo_Simple();

        todoItem.setId(cursor.getInt(cursor
                .getColumnIndex(COL_ID)));
        todoItem.setName(cursor.getString(cursor
                .getColumnIndex(COL_NAME)));
        todoItem.setDescription(cursor.getString(cursor
                .getColumnIndex(COL_DESCRIPTION)));
        todoItem.setIsDone(Boolean.parseBoolean(cursor
                .getColumnName(3)));
        todoItem.setIsFavourite(Boolean.parseBoolean(cursor
                .getColumnName(4)));
        todoItem.setDueDate(Long.parseLong((cursor
                .getString(5))));

        return todoItem;
    }

    /**
     * add an item to the db
     *
     * @param item
     */
    public void addTodoItemToDb(Todo_Simple item){

        Log.i(logger,"addTodoItemToDb(): " + item);

        ContentValues contentValues = DBHandler.createDBTodoItem(item);

        long newItemID = this.database.insert(
                TABLE_NAME, null, contentValues
        );

        Log.i(logger, "addTodoItemToDb(): DB_ID = "+ newItemID);

        item.setId(newItemID);
    }


    /**
     * remove an item from the db
     *
     * @param item
     */
    public void removeItemFromDb(Todo_Simple item) {
        Log.i(logger,"removeItemFromDb(): " + item);

        this.database.delete(TABLE_NAME, WHERE_IDENTIFY_ITEM
                , new String[] {String.valueOf(item.getId())} );
        Log.i(logger,"removeItemFromDb():  deletion complete!" );
    }

    /**
     * update an item in the db
     *
     * @param item
     */
    public void updateItemInDb(Todo_Simple item) {
        Log.i(logger, "updateItemInDb(): " + item);

        this.database.update(TABLE_NAME, createDBTodoItem(item)
                , WHERE_IDENTIFY_ITEM, new String[]{
                        String.valueOf(item.getId())});
        Log.i(logger, "updateItemInDb(): update has been carried out");
    }

    public void close() {
        this.database.close();
        Log.i(logger, "db has been closed");
    }

}
