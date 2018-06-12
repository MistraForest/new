package com.a06.uebung.maus.getingstarted.pogba.myue06app.dataaccessors;


import android.widget.ListAdapter;
import com.a06.uebung.maus.getingstarted.pogba.myue06app.model.TodoItem;

import java.util.List;

/**
 * Interface for dealing with a list of data items, used by ItemListActivity
 *
 * @author Mistra Forest
 *
 */
public interface ItemListAccessor {

    List<TodoItem> getAllItems();

    /**
     * add an item to the list
     *
     * @param item
     */
    void addItem(TodoItem item);

    /**
     * get an adapter for the list
     * @return
     */
    ListAdapter getAdapter();


    /**
     * update an existing item
     *
     * @param item
     */
    void updateItem(TodoItem item);

    /**
     * delete an item
     *
     * @param item
     */
    void deleteItem(TodoItem item);

    /**
     * determine the item selected by the user given either the position in the
     * list or the item id
     *
     * @param itemPosition
     * @param itemId
     * @return
     */
    TodoItem getSelectedItem(int itemPosition, long itemId);

    /**
     * end processing the list of items
     */
    void close();
}
