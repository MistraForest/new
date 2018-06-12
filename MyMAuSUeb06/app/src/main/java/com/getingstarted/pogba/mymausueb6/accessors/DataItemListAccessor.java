package com.getingstarted.pogba.mymausueb6.accessors;

import android.widget.ListAdapter;
import com.getingstarted.pogba.mymausueb6.model.DataItem;


/**
 * Interface for dealing with a list of data items, used by ItemListActivity
 *
 * @author Joern Kreutel
 *
 */
public interface DataItemListAccessor {

    /**
     * add an item to the list
     *
     * @param item
     */
    public void addItem(DataItem item);

    /**
     * get an adapter for the list
     * @return
     */
    public ListAdapter getAdapter();

    /**
     * update an existing item
     *
     * @param item
     */
    public void updateItem(DataItem item);

    /**
     * delete an item
     *
     * @param item
     */
    public void deleteItem(DataItem item);

    /**
     * determine the item selected by the user given either the position in the
     * list or the item id
     *
     * @param itemPosition
     * @param itemId
     * @return
     */
    public DataItem getSelectedItem(int itemPosition, long itemId);

    /**
     * end processing the list of items
     */
    public void close();
}
