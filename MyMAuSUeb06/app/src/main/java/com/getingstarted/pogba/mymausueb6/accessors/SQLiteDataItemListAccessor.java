package com.getingstarted.pogba.mymausueb6.accessors;

import android.database.Cursor;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import com.getingstarted.pogba.mymausueb6.model.DataItem;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDataItemListAccessor extends
        AbstractActivityDataAccessor implements DataItemListAccessor{


    /**
     * the logger
     */
    protected static final String logger = SQLiteDataItemListAccessor
            .class.getName();

    /**
     * the list of items
     */
    List<DataItem> items = new ArrayList<>();

    /**
     * the adapter operating on the list
     */
    ArrayAdapter<DataItem> adapter;

    private SQLiteDBHelper mHelper;



    @Override
    public void addItem(DataItem item) {

        mHelper.addItemToDb(item);

        /**
         * we then add the item to the list and notify the adapter
         */
        this.adapter.add(item);

        // the following two lines have the same effect provided
        // setNotifyOnChange has been set to true on the adapter (which is the
        // case here)
        // this.items.add(item);
        // this.adapter.notifyDataSetChanged();
    }


    @Override
    public ListAdapter getAdapter() {

        mHelper = new SQLiteDBHelper(getActivity());
        mHelper.prepareSQLiteDatabase();

        readOutItemsFromDatabase();

        return this.adapter;
    }

    private void readOutItemsFromDatabase() {
        Cursor c = mHelper.getCursor();

        Log.i(logger, "getAdapter(): got a cursor: " + c);

        c.moveToFirst();

        while (!c.isAfterLast()){

            //create a new item and add it to the list
            this.items.add(mHelper.createItemFromCursor(c));

            c.moveToNext();
        }
    }

    @Override
    public void updateItem(DataItem item) {

        mHelper.updateItemInDb(item);
        // then update the item and notify the adapter
        lookupItem(item).updateFrom(item);
        this.adapter.notifyDataSetChanged();

    }

    /**
     * get the item from the list, checking identity of ids (as the argument
     * value may have been serialised/deserialised we cannot check for identity
     * of objects)
     */
    private DataItem lookupItem(DataItem item) {

        for (DataItem current :
                items) {
            if (current.getId() == item.getId()) {
                return current;
            }
            }

        return null;
    }


    /**
     * get the items
     */
    public List<DataItem> getItems() {
        return this.items;
    }

    @Override
    public void deleteItem(DataItem item) {

        mHelper.removeItemFromDb(item);

        // then we remove the item and notify the adapter
        this.adapter.remove(lookupItem(item));

        // this could be done instead:
        // this.items.remove(lookupItem(item));
        // this.adapter.notifyDataSetChanged();

    }

    @Override
    public DataItem getSelectedItem(int itemPosition, long itemId) {
        return this.adapter.getItem(itemPosition);
    }

    @Override
    public void close() {
        mHelper.close();
    }
}
