package com.getingstarted.pogba.mymausueb6.dataaccess;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.getingstarted.pogba.mymausueb6.R;
import com.getingstarted.pogba.mymausueb6.accessors.AbstractActivityDataAccessor;
import com.getingstarted.pogba.mymausueb6.accessors.DataItemAccessor;


/**
 * Show the details of an item
 *
 * @author Joern Kreutel
 *
 */
public class ItemDetailsActivity extends Activity {

    /**
     * the logger
     */
    protected static final String logger = ItemDetailsActivity.class.getName();

    /**
     * the accessor for dealing with the item to be displayed and edited
     */
    private DataItemAccessor accessor;

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemview);

        try {
            // obtain the ui elements
            final EditText itemName = findViewById(R.id.item_name);
            final  EditText itemDescription = findViewById(R.id.item_description);
            Button saveButton = findViewById(R.id.saveButton);
            Button deleteButton = findViewById(R.id.deleteButton);

            // instantiate the accessor class
            Class<?> accessorClass = Class.forName(getIntent().getStringExtra(DataAccessActivity.ARG_ACCESSOR_CLASS));
            this.accessor = (DataItemAccessor) accessorClass.newInstance();

            // if we have an ActivityDataAccessor, we pass ourselves
            if (accessor instanceof AbstractActivityDataAccessor){
                ((AbstractActivityDataAccessor)accessor).setActivity(this);
            }

            // set the title of the activity given the accessor class
            setTitle(accessorClass.getName().substring(accessorClass.getName().lastIndexOf(".") + 1));

            // if we do not have an item, we assume we need to create a new one
            if (accessor.hasItem()){

                // set name and description
                itemName.setText(accessor.readItem().getName());
                itemDescription.setText(accessor.readItem().getDescription());

            }else {
                accessor.createItem();
            }


            // we only set a listener on the ok button that will collect the
            // edited fields and set the values on the item
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    processItemSave(accessor, itemName, itemDescription);
                }
            });

            // we also set a listener on the delete button
            // we only set a listener on the ok button that will collect the
            // edited fields and set the values on the item
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    processItemDelete(accessor);
                }
            });


        } catch (Exception e) {

            String err = "got exception: " + e;
            Log.e(logger, err, e);
            ((DataAccessApplication) getApplication())
                    .reportError(this, err);

        }

    }

    /**
     * delete the item and finish
     * @param accessor
     *
     */
    private void processItemDelete(DataItemAccessor accessor) {

        // delete the item
        accessor.deleteItem();

        //and finish
        finish();

    }

    /**
     * save the item and finish
     * @param accessor
     * @param itemName
     * @param itemDescription
     */
    private void processItemSave(DataItemAccessor accessor, EditText itemName, EditText itemDescription) {

        // re-set the fields of the item
        accessor.readItem().setName(itemName.getText().toString());
        accessor.readItem().setDescription(itemDescription.getText().toString());

        // save the item
        accessor.writeItem();

        // and finish
        finish();

    }
}
