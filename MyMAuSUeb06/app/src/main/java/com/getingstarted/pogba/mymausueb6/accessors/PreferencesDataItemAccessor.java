package com.getingstarted.pogba.mymausueb6.accessors;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import com.getingstarted.pogba.mymausueb6.model.DataItem;


/**
* Using SharedPreferences to read and write a single item
*
* @author Joern Kreutel
*
*/
public class PreferencesDataItemAccessor extends AbstractActivityDataAccessor implements DataItemAccessor {



    /**
     * the logger
     */
    protected static final String logger = PreferencesDataItemAccessor.class
            .getName();

    /**
     * the shared preferences id
     */
    public static final String PREFERENCES_ID = "SharedPreferencesDataItem";

    /**
     * the item id preference
     */
    private static final String PREF_ITEM_ID = "dataItemId";

    /**
     * the item name preference
     */
    private static final String PREF_ITEM_NAME = "dataItemName";

    /**
     * the item description preference
     */
    private static final String PREF_ITEM_DESC = "dataItemDescription";

    /**
     * the item type preference
     */
    private static final String PREF_ITEM_TYPE = "dataItemType";

    /**
     * the item
     */
    private DataItem item;

    private SharedPreferences prefs;


    @Override
    public DataItem readItem() {

        Log.i(logger, "readItem()....");

        loadPreferences();

        if (this.item == null){
            this.item = new DataItem(prefs.getLong(PREF_ITEM_ID, -1),
                                    DataItem.ItemTypes.valueOf(prefs.getString(PREF_ITEM_TYPE,
                                            String.valueOf(DataItem.ItemTypes.TYPE1))),
                                    prefs.getString(PREF_ITEM_NAME, ""),
                                    prefs.getString(PREF_ITEM_DESC, ""), null);

            Log.i(logger, "readItem(): " + this.item);
        }

        return this.item;
    }


    @Override
    public void writeItem() {

        Log.i(logger, "writeItem(): " + this.item);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(PREF_ITEM_ID, this.item.getId());
        editor.putString(PREF_ITEM_TYPE, String.valueOf(this.item.getType()));
        editor.putString(PREF_ITEM_NAME, this.item.getName());
        editor.putString(PREF_ITEM_DESC, this.item.getDescription());

        editor.commit();

    }

    @Override
    public boolean hasItem() {

        Log.i(logger, "hasItem()...");

        loadPreferences();

        return prefs.contains(PREF_ITEM_ID);
    }

    @Override
    public void createItem() {

        Log.i(logger, "createItem()...");

        this.item = new DataItem(-1, DataItem.ItemTypes.TYPE1, "", "", null);
    }

    @Override
    public void deleteItem() {

        Log.i(logger, "deleteItem()...");

        SharedPreferences.Editor editor = prefs.edit();

        editor.remove(PREF_ITEM_ID);
        editor.remove(PREF_ITEM_TYPE);
        editor.remove(PREF_ITEM_NAME);
        editor.remove(PREF_ITEM_DESC);

        editor.commit();
    }

    /**
     * access the preferences
     */
    private void loadPreferences() {

        if (this.prefs == null){
            this.prefs = getActivity().getSharedPreferences(PREFERENCES_ID, Activity.MODE_PRIVATE);

            Log.i(logger, "loadPreferences(): " + this.prefs.getAll());
        }

    }
}
