package com.getingstarted.pogba.mymausueb6.accessors;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import com.getingstarted.pogba.mymausueb6.R;
import com.getingstarted.pogba.mymausueb6.model.DataItem;

import java.util.ArrayList;
import java.util.List;


/**
 * Read out a list of items from a (static) resource file, which does not allow
 * for write access
 *
 * @author Joern Kreutel
 *
 */
public class ResourcesDataItemListAccessor extends AbstractActivityDataAccessor implements DataItemListAccessor {


    // list
    private List<DataItem> itemlist;

    // adapter
    private ArrayAdapter<DataItem> adapter;

    // logger
    protected static final String logger = ResourcesDataItemListAccessor.class.getName();

    @Override
    public void addItem(DataItem item) {

        this.itemlist.add(item);
        this.adapter.notifyDataSetChanged();

    }

    @Override
    public ListAdapter getAdapter() {

        // the options
        String[] items = getActivity().getResources().getStringArray(R.array.item_list);

        // create a list
        itemlist = new ArrayList<>();
        for (String item : items) {

            itemlist.add(new DataItem(-1, DataItem.ItemTypes.TYPE1, item, "",
                    null));
        }

        // create the adapter from the list

        adapter = (DataItemListViews.createDataItemArrayAdapter(getActivity(), itemlist));

        return adapter;
    }

    @Override
    public void updateItem(DataItem item) {

        Log.i(logger, "updating item: " + item);
        lookupItem(item).updateFrom(item);

        this.adapter.notifyDataSetChanged();
    }

    @Override
    public void deleteItem(DataItem item) {

        this.itemlist.remove(lookupItem(item));
        this.adapter.notifyDataSetChanged();
    }



    /**
     * get the item from the list, checking identity of ids (as the argument
     * value may have been serialised/deserialised we cannot check for identity
     * of objects)
     */
    private DataItem lookupItem(DataItem item) {

        for (DataItem current : this.itemlist) {
            if (current.getId() == item.getId()) {
                return current;
            }
        }
        return null;
    }

    @Override
    public DataItem getSelectedItem(int itemPosition, long itemId) {

        return adapter.getItem(itemPosition);
    }

    @Override
    public void close() {

    }
}
