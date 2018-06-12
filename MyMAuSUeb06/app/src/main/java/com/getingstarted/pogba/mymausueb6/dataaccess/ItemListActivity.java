package com.getingstarted.pogba.mymausueb6.dataaccess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.getingstarted.pogba.mymausueb6.R;
import com.getingstarted.pogba.mymausueb6.accessors.AbstractActivityDataAccessor;
import com.getingstarted.pogba.mymausueb6.accessors.DataItemListAccessor;
import com.getingstarted.pogba.mymausueb6.accessors.IntentDataItemAccessor;
import com.getingstarted.pogba.mymausueb6.model.DataItem;


/**
 * Show a list of items
 *
 * @author Joern Kreutel
 *
 */
public class ItemListActivity extends Activity {

    // the logger
    protected static final String logger = DataAccessActivity.class.getName();

    /**
     * the argname with which we will pass the item to the subview
     */
    public static final String ARG_ITEM_OBJECT = "itemObject";

    /**
     * the result code that indicates that some item was changed
     */
    public static final int RESPONSE_ITEM_EDITED = 1;

    /**
     * the result code that indicates that the item shall be deleted
     */
    public static final int RESPONSE_ITEM_DELETED = 2;

    /**
     * the result code that indicates that nothing has been changed
     */
    public static final int RESPONSE_NOCHANGE = -1;

    /**
     * the constant for the subview request
     */
    public static final int REQUEST_ITEM_DETAILS = 2;

    /**
     * the constant for the new item request
     */
    public static final int REQUEST_ITEM_CREATION = 1;

    /**
     * the field for the listview
     */
    private ListView listview;

    /**
     * the data accessor for the data items
     */
    private DataItemListAccessor accessor;



    /** Called when the activity is first created.
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.itemlistview);


        try {
            // access the listview
            /*
             * access the list view for the options to be displayed
             */
            listview = findViewById(R.id.list);

            // the button for adding new items
            Button newitemButton = findViewById(R.id.newitemButton);

            // determine the accessor which shall be used
            Class<?> accessorClass = Class.forName(getIntent().getStringExtra(DataAccessActivity.ARG_ACCESSOR_CLASS));
            accessor = (DataItemListAccessor) accessorClass.newInstance();

            // if we have an ActivityDataAccessor, we pass ourselves
            if (accessor instanceof AbstractActivityDataAccessor){

                ((AbstractActivityDataAccessor)accessor).setActivity(this);
            }

            // set the title of the activity given the accessor class
            setTitle(accessorClass.getName().substring(
                    accessorClass.getName().lastIndexOf(".") + 1));

            Log.i(logger, "will use accessor: " + accessor);

            // obtain the adapter from the accessor, passing it the id of the
            // item layout to be used
            final ListAdapter listAdapter = accessor.getAdapter();

            // set the adapter on the list view
            listview.setAdapter(listAdapter);


            // set a listener that reacts to the selection of an element
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View itemView, int itemPosition, long itemId) {

                    Log.i(logger, "onItemClick(): position is: " + itemPosition + " id is: " + itemId);

                    DataItem item = accessor.getSelectedItem(itemPosition, itemId);

                    processItemSelection(item);
                }
            });

            // set the listview as scrollable
            listview.setScrollBarStyle(ListView.SCROLLBARS_INSIDE_OVERLAY);

            // set a listener for the newItemButton
            newitemButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    processNewItemRequest();
                }
            });


        } catch (Exception e) {

            String err = "got exception: " + e;
            Log.e(logger, err, e);
            ((DataAccessApplication)getApplication()).reportError(this, err);
        }
    }


    private void processItemSelection(DataItem item) {

        Log.i(logger, "processItemSelection(): " + item);

        // create an intent for opening the details view
        Intent intent = new Intent(ItemListActivity.this, ItemListActivity.class);

        // pass the item to the intent
        intent.putExtra(ARG_ITEM_OBJECT, item);

        // also specify the accessor class
        intent.putExtra(DataAccessActivity.ARG_ACCESSOR_CLASS, IntentDataItemAccessor.class.getName());

        // start the details activity with the intent
        startActivityForResult(intent, REQUEST_ITEM_DETAILS);

    }


    private void processNewItemRequest() {

        Log.i(logger, "processNewItemRequest()");
        Intent intent = new Intent(ItemListActivity.this, ItemDetailsActivity.class);

        // we only specify the accessor class
        intent.putExtra(DataAccessActivity.ARG_ACCESSOR_CLASS, IntentDataItemAccessor.class.getName());

        // start the details activity with the intent
        startActivityForResult(intent, REQUEST_ITEM_CREATION);
    }



    /**
     * process the result of the item details subactivity, which may be the
     * creation, modification or deletion of an item.
     *
     * NOTE that is not necessary to invalidate the listview if changes are
     * communicated to the adapter using notifyDataSetChanged()
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.i(logger, "onActivityResult(): " + data);

        DataItem item = data != null ? (DataItem) data.getSerializableExtra(ARG_ITEM_OBJECT) : null;
        Log.i(logger, "Returned Item is: " + item.getId() +  "    Desc. :   " +  item.getDescription());

        // check which request we had

        if (requestCode == REQUEST_ITEM_DETAILS){

            if (requestCode == RESPONSE_ITEM_EDITED){
                Log.i(logger, "onActivityResult(): updating the edited item");
                this.accessor.updateItem(item);
            } else if (resultCode == RESPONSE_ITEM_DELETED){
                Log.i(logger, "onActivityResult(): deleting an Item");
                this.accessor.deleteItem(item);
            }

            else if (requestCode == REQUEST_ITEM_CREATION && resultCode == RESPONSE_ITEM_EDITED){
                Log.i(logger, "onActivityResult(): adding the created item");
                this.accessor.addItem(item);
            }

        }
    }

    /**
     * if we stop, we signal this to the accessor (which is necessary in order to avoid trouble when operating on dbs)
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(logger,"onDestroy(): will signal finalisation to the accessors");
        this.accessor.close();

        super.onStop();
    }
}
