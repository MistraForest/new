package com.getingstarted.pogba.mymausueb6.accessors;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;
import com.getingstarted.pogba.mymausueb6.model.DataItem;

import java.util.Locale;

public class SQLiteDBHelper {

    private  Activity mActivity;

    protected static final String logger = SQLiteDBHelper.class.getName();

    /**
     * the db name
	 */
    private static final String DBNAME = "dataItems.db";

    /**
     * the initial version of the db based on which we decide whether to create
     * the table or not
     */
    private static final int INITIAL_DBVERSION = 0;

    /**
     * the table name
     */
    protected static final String TABNAME = "dataitems";

    /**
     * the column names
     *
     * the _id column follows the convention required by the CursorAdapter usage
     */
    protected static final String COL_ID = "_id";
    protected static final String COL_TYPE = "type";
    protected static final String COL_NAME = "name";
    protected static final String COL_DESCRIPTION = "description";
    protected static final String COL_ICONURL = "iconUrl";

    /**
     * the creation query
     */
    private static final String TABLE_CREATION_QUERY = "CREATE TABLE IF NOT EXISTS "
            + TABNAME + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + COL_TYPE + " TEXT,\n" + COL_NAME + " TEXT,\n" + COL_DESCRIPTION
            + " TEXT,\n" + COL_ICONURL + " TEXT);";

    /**
     * the where clause for item deletion
     */
    private static final String WHERE_IDENTIFY_ITEM = COL_ID + "=?";

    /**
     * the database
     */
    SQLiteDatabase db;


    public SQLiteDBHelper(Activity aActivity) {
        super();
        this.mActivity = aActivity;
    }

    public Activity getActivity() {
        return mActivity;
    }

    /**
     * prepare the database
     */
    protected void prepareSQLiteDatabase(){

        this.db = getActivity().openOrCreateDatabase(DBNAME,
                Context.MODE_PRIVATE, null);

        // we need to check whether it is empty or not...
        Log.d(logger, "db version is: " + db.getVersion());

        if (this.db.getVersion() == INITIAL_DBVERSION){
            Log.i(logger,
                    "The db has just been created. Need for table creation...");

            db.setLocale(Locale.getDefault());
            db.setVersion(INITIAL_DBVERSION);
            db.execSQL(TABLE_CREATION_QUERY);
        }else {
            Log.i(logger, "The db exists already. No need for table creation");
        }


    }

    /**
     * create a ContentValues object which can be passed to a db query
     *
     * @param item
     * @return
     */
    public static ContentValues createDBDataItem(DataItem item){

        ContentValues insertItem = new ContentValues();
        insertItem.put(COL_NAME, item.getName());
        insertItem.put(COL_TYPE, String.valueOf(item.getType()));
        insertItem.put(COL_DESCRIPTION, item.getDescription());

        return insertItem;
    }


    public Cursor getCursor(){

        // we make a query, which possibly will return an empty list
        SQLiteQueryBuilder querybuilder = new SQLiteQueryBuilder();
        querybuilder.setTables(TABNAME);

        // we specify all columns
        String[] asColumsToReturn = {COL_ID, COL_TYPE, COL_NAME, COL_DESCRIPTION, COL_ICONURL};

        // we specify an ordering
        String ordering = COL_ID + " ASC";

        Cursor c = querybuilder.query(this.db, asColumsToReturn, null, null
                                    , null, null, ordering);

        return c;
    }


    /**
     * create an item from the cursor
     *
     * @param c
     * @return
     */
    public DataItem createItemFromCursor(Cursor c){

        //create the Item
        DataItem currentItem = new DataItem();

        // then populate the item with the results from the cursor
        currentItem.setId(c.getInt(c.getColumnIndex(COL_ID)));
        currentItem.setType(DataItem.ItemTypes.valueOf(c.getString(c.getColumnIndex(COL_TYPE))));
        currentItem.setName(c.getString(c.getColumnIndex(COL_NAME)));
        currentItem.setDescription(c.getString(c.getColumnIndex(COL_DESCRIPTION)));

        return  currentItem;
    }

    /**
     * add an item to the db
     *
     * @param item
     */
    public void addItemToDb(DataItem item){
        Log.i(logger, "addItemToDb(): " + item);

        /**
         * add the item to the db
         */
        ContentValues insertItem = SQLiteDBHelper.createDBDataItem(item);

        long newItemId = this.db.insert(TABNAME, null, insertItem);
        Log.i(logger, "addItemToDb(): got new item id after insertion: "
                + insertItem);
        item.setId(newItemId);
    }


    /**
     * remove an item from the db
     *
     * @param item
     */
    public void removeItemFromDb(DataItem item) {

        Log.i(logger, "removeItemFromDb(): " + item);

        //we first delete the item
        this.db.delete(TABNAME, WHERE_IDENTIFY_ITEM,
                new String[]{String.valueOf(item.getId())});
        Log.i(logger, "removeItemFromDb(): deletion in db done");
    }


    /**
     * update an item in the db
     *
     * @param item
     */
    public void updateItemInDb(DataItem item) {

        Log.i(logger, "updateItemInDb():" + item);

        // do the update in the db
        this.db.update(TABNAME, createDBDataItem(item), WHERE_IDENTIFY_ITEM,
                new String[]{String.valueOf(item.getId())});
        Log.i(logger, "updateItemInDb(): update has been carried out");

    }

    public void close() {
        this.db.close();
        Log.i(logger, "db has been closed");
    }

}
