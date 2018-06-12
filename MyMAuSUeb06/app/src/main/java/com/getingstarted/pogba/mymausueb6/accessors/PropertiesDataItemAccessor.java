package com.getingstarted.pogba.mymausueb6.accessors;

import android.app.Activity;
import android.util.Log;
import com.getingstarted.pogba.mymausueb6.dataaccess.DataAccessApplication;
import com.getingstarted.pogba.mymausueb6.model.DataItem;

import java.io.IOException;
import java.util.Properties;

/**
 * Using a properties file for reading/writing a single item. Apart from this,
 * functionality is rather close to the one of {@link PreferencesDataItemAccessor}
 *
 * @author Joern Kreutel
 *
 */
public class PropertiesDataItemAccessor extends AbstractActivityDataAccessor implements DataItemAccessor{

    /**
     * the logger
     */
    protected static final String logger = PropertiesDataItemAccessor.class
            .getName();

    /**
     * the filename
     */
    private static final String PROPERTIES_FILE =  "dataitem.txt";

    /**
     * the item id property
     */
    private static final String PROP_ITEM_ID = "dataItemId";

    /**
     * the item name property
     */
    private static final String PROP_ITEM_NAME = "dataItemName";

    /**
     * the item description property
     */
    private static final String PROP_ITEM_DESC = "dataItemDescription";

    /**
     * the item type property
     */
    private static final String PROP_ITEM_TYPE = "dataItemType";

    /**
     * the properties
     */
    private Properties props;

    /**
     * the item
     */
    private DataItem item;

    @Override
    public DataItem readItem() {

        Log.i(logger, "readItem()...");
        loadProperties();

        if (this.item == null){
            this.item = new DataItem(Long.parseLong(props.getProperty(PROP_ITEM_ID, "-1")),
                    DataItem.ItemTypes.valueOf(props.getProperty(PROP_ITEM_TYPE, String.valueOf(DataItem.ItemTypes.TYPE1))),
                    props.getProperty(PROP_ITEM_NAME, ""),
                    props.getProperty(PROP_ITEM_DESC,""), null);

            Log.i(logger, "readItem(): " + this.item);
        }

        return this.item;
    }

    private void loadProperties() {
        this.props = new Properties();

        try {
            props.load(getActivity().openFileInput(PROPERTIES_FILE));
            Log.i(logger, "loadProperties(): properties have been loaded: "
                    + props);

        } catch (IOException e) {

            Log.w(logger, "got exception trying to read properties: " + e
                    + ". Maybe they do not exist so far. Try to create...", e);

            writeProperties();
        }
    }

    private void writeProperties() {


        try {
        props.store(
                getActivity().openFileOutput(PROPERTIES_FILE,
                            Activity.MODE_APPEND), "");

            Log.i(logger, "writeProperties(): properties have been written: "
                    + props);

        } catch (Exception e) {
            String err = "got exception trying to write properties: " + e;
            Log.e(logger, err, e);
            ((DataAccessApplication) getActivity().getApplication())
                    .reportError(getActivity(), err);        }
    }

    @Override
    public void writeItem() {

        Log.i(logger, "writeItem(): " + this.item);
        props.setProperty(PROP_ITEM_ID, String.valueOf(this.item.getId()));
        props.setProperty(PROP_ITEM_TYPE, String.valueOf(this.item.getType()));
        props.setProperty(PROP_ITEM_NAME, this.item.getName());
        props.setProperty(PROP_ITEM_DESC, this.item.getDescription());

        writeProperties();
    }

    @Override
    public boolean hasItem() {

        Log.i(logger, "hasItem()...");

        loadProperties();
        return !"".equals(props.getProperty(PROP_ITEM_ID, ""));
    }

    @Override
    public void createItem() {

        Log.i(logger, "createItem()...");

        this.item = new DataItem(-1, DataItem.ItemTypes.TYPE1, "", "", null);
    }

    @Override
    public void deleteItem() {

        Log.i(logger, "deleteItem()...");
        props.setProperty(PROP_ITEM_ID, "");
        props.setProperty(PROP_ITEM_TYPE, "");
        props.setProperty(PROP_ITEM_NAME, "");
        props.setProperty(PROP_ITEM_DESC, "");

        writeProperties();
    }
}
