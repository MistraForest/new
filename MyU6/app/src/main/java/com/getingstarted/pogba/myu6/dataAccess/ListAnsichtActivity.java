package com.getingstarted.pogba.myu6.dataAccess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.getingstarted.pogba.myu6.R;
import com.getingstarted.pogba.myu6.accessor.IntentAccessor;
import com.getingstarted.pogba.myu6.model.DataItem;

import java.util.ArrayList;
import java.util.List;

public class ListAnsichtActivity extends Activity {


    protected static final String logger = ListAnsichtActivity.class.getName();



    /**
     * the argname with which we will pass the item to the subview
     */
    public static final String ARG_ITEM_OBJECT = "itemObject";

    /**
     * the constant for the subview request
     */
    public static final int REQUEST_ITEM_DETAILS = 2;

    /**
     * the result code that indicates that some item was changed
     */
    public static final int RESPONSE_ITEM_EDITED = 1;

    /**
     * the result code that indicates that the item shall be deleted
     */
    public static final int RESPONSE_ITEM_DELETED = 2;

    /**
     * the constant for the new item request
     */
    public static final int REQUEST_ITEM_CREATION = 1;



    private ListView listview;

    private List<DataItem> items = new ArrayList<>();

    private ArrayAdapter<DataItem> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_ansicht);

        Log.i(logger, " Started");

        try {
            listview = findViewById(R.id.list);

            Button newitemButton = findViewById(R.id.newitemButton);

            final ListAdapter adapter = getListAdapter();
            this.listview.setAdapter(adapter);

            this.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long itemId) {
                    Log.i(logger, "onItemClick(): position is: " + position
                                    + ", id ist: " + itemId + " Title is: " + parent.getAdapter().getItem(position));

                    DataItem item = getSelectedItem(position, itemId);

                    Toast.makeText(ListAnsichtActivity.this,  "Id:" + itemId + " item: " + item.getTitle() + " is selected.", Toast.LENGTH_LONG).show();

                    handleItemSelected(item);
                }
            });

            listview.setScrollBarStyle(ListView.SCROLLBARS_INSIDE_OVERLAY);

            newitemButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleNewItemRequest();
                }
            });


        } catch (Exception e) {
            String err = "Got exception: " + e;
            Log.e(logger, err, e);
            e.printStackTrace();
        }
    }

    private void handleNewItemRequest() {


    }

    private void handleItemSelected(DataItem item) {

        Log.i(logger, "handleItemSelected(): " + item);

        // create an intent for opening the details view
        Intent intentToDetails = new Intent(ListAnsichtActivity.this, ItemdetailsActivity.class);

        // pass the item to the intent
        intentToDetails.putExtra(ARG_ITEM_OBJECT, item);
        intentToDetails.putExtra(HomeActivity.ARG_ACCESSOR_CLASS, IntentAccessor.class);

        startActivityForResult(intentToDetails, REQUEST_ITEM_DETAILS);

    }

    private DataItem getSelectedItem(int position, long itemId) {

        return adapter.getItem(position);
    }

    private ListAdapter getListAdapter() {
        this.adapter = MyAdapterListview.createItemAdapter(this, items);

        this.adapter.add(new DataItem(1L,"Football","collectiv",true, false, 552222L));
        this.adapter.add(new DataItem(-2L,"Handball","collectiv",true, false, 552222L));
        this.adapter.add(new DataItem(-3L,"Basketball","collectiv",true, false, 552222L));
        this.adapter.add(new DataItem(-4L,"Volleyball","collectiv",true, false, 552222L));
        this.adapter.add(new DataItem(-5L,"Wasserball","collectiv",true, false, 552222L));
        this.adapter.setNotifyOnChange(true);

        return this.adapter;
    }
}
