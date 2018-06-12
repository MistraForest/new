package com.getingstarted.pogba.mymausueb6.accessors;

import android.content.Intent;
import android.util.Log;
import com.getingstarted.pogba.mymausueb6.dataaccess.ItemListActivity;
import com.getingstarted.pogba.mymausueb6.model.DataItem;


/**
 * Access a single item provided by the ItemListActivity in the intent used for
 * calling ItemDetailsActivity
 *
 * @author Joern Kreutel
 *
 */
public class IntentDataItemAccessor extends AbstractActivityDataAccessor
        implements DataItemAccessor {

    public static final String logger = IntentDataItemAccessor.class.getName();

    // the item
    private DataItem item;

    @Override
    public DataItem readItem() {

        if (this.item == null){
            this.item = (DataItem) getActivity().getIntent().getSerializableExtra(ItemListActivity.ARG_ITEM_OBJECT);
        }
//        Log.i(logger, "the readed item is" + this.item.getId() + "Desc. : " + this.item.getDescription());
        return this.item;
    }

    @Override
    public void writeItem() {

        // and return to the calling activity
        Intent returnIntent = new Intent();

        // set the item
        returnIntent.putExtra(ItemListActivity.ARG_ITEM_OBJECT, this.item);
        Log.i(logger, "the wrote item is  " + this.item.getId() + "   Desc. : " + this.item.getDescription());

        // set the result code
        getActivity().setResult(ItemListActivity.RESPONSE_ITEM_EDITED, returnIntent);

    }

    @Override
    public boolean hasItem() {
        return readItem() != null;
    }

    @Override
    public void createItem() {
        this.item = new DataItem(-1, DataItem.ItemTypes.TYPE1, "","", null);
    }

    @Override
    public void deleteItem() {

        // and return to the calling activity
        Intent returnIntent = new Intent();

        // set the item
        returnIntent.putExtra(ItemListActivity.ARG_ITEM_OBJECT, this.item);

        // set the result code
        getActivity().setResult(ItemListActivity.RESPONSE_ITEM_DELETED, returnIntent);

    }
}
