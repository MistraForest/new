package com.a06.uebung.maus.getingstarted.pogba.myue06app.dataaccessors;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import com.a06.uebung.maus.getingstarted.pogba.myue06app.model.TodoItem;

import java.util.ArrayList;
import java.util.List;

public class ItemListImplement extends AbstractActivityItemAccessor implements ItemListAccessor {


    /**
     * the logger
     */
    protected static final String logger = ItemListImplement.class
            .getName();

    /**
     * the list of items
     */
    List<TodoItem> items = new ArrayList<>();

    /**
     * the adapter operating on the list
     */
    ArrayAdapter<TodoItem> adapter;


    @Override
    public List<TodoItem> getAllItems() {
        return this.items;
    }

    @Override
    public void addItem(TodoItem item) {

        /**
         * we then add the item to the list and notify the adapter
         */
        //items.add(item);
        this.adapter.add(item);


        // the following two lines have the same effect provided
        // setNotifyOnChange has been set to true on the adapter (which is the
        // case here)
        // this.items.add(item);
         //this.adapter.notifyDataSetChanged();
    }

    @Override
    public ListAdapter getAdapter() {

        // create the adapter, overriding the getView method for accessing the
        // name field of the item
        Log.i(logger, "getAdapter()....");

        this.adapter = new ListviewAdapter(getActivity(), items);
        this.adapter.setNotifyOnChange(true);

        return this.adapter;
    }


    @Override
    public void updateItem(TodoItem item) {

        // then update the item and notify the adapter
        lookupItem(item).updateFromTodoItem(item);

        this.adapter.notifyDataSetInvalidated();
    }


    /**
     * get the item from the list, checking identity of ids (as the argument
     * value may have been serialised/deserialised we cannot check for identity
     * of objects)
     */
    private TodoItem lookupItem(TodoItem item) {
        for (TodoItem todoItem : getAllItems() ) {
            if (todoItem.getId() == item.getId()){
                return todoItem;
            }
        }
        return null;
    }

    @Override
    public void deleteItem(TodoItem item) {

        // then we remove the item and notify the adapter
        this.adapter.remove(lookupItem(item));

        // this could be done instead:
        // this.items.remove(lookupItem(item));
        // this.adapter.notifyDataSetChanged();
    }

    @Override
    public TodoItem getSelectedItem(int itemPosition, long itemId) {
        return this.adapter.getItem(itemPosition);
    }

    @Override
    public void close() {

    }
}
