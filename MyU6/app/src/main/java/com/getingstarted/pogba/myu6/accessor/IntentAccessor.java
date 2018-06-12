package com.getingstarted.pogba.myu6.accessor;

import android.content.Intent;
import com.getingstarted.pogba.myu6.dataAccess.ListAnsichtActivity;
import com.getingstarted.pogba.myu6.model.DataItem;

public class IntentAccessor extends  AbstractActivityAccessor implements DataItemAccessor {

    private DataItem item;


    @Override
    public DataItem readItem() {

        if (this.item == null){
            this.item = (DataItem) getActivity().getIntent()
                    .getSerializableExtra(ListAnsichtActivity.ARG_ITEM_OBJECT);
        }
        return this.item;
    }

    @Override
    public void writeItem() {

        Intent returnIntent = new Intent();

        returnIntent.putExtra(ListAnsichtActivity.ARG_ITEM_OBJECT, this.item);

        // set the Resultcode
        getActivity().setResult(ListAnsichtActivity.RESPONSE_ITEM_EDITED, returnIntent);
    }

    @Override
    public boolean hasItem() {
        return readItem() != null;
    }

    @Override
    public void createItem() {

        this.item = new DataItem(-1L, "", "", false, true, 125555L);

        Intent returnIntent = new Intent();
        returnIntent.putExtra(ListAnsichtActivity.ARG_ITEM_OBJECT, this.item);

        getActivity().setResult(ListAnsichtActivity.REQUEST_ITEM_CREATION, returnIntent);

    }

    @Override
    public void deleteItem() {

        Intent returnIntent = new Intent();

        returnIntent.putExtra(ListAnsichtActivity.ARG_ITEM_OBJECT, this.item);

        getActivity().setResult(ListAnsichtActivity.RESPONSE_ITEM_DELETED, returnIntent);
    }
}
