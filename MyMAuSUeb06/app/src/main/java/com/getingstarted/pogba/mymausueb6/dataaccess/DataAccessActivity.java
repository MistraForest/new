package com.getingstarted.pogba.mymausueb6.dataaccess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.getingstarted.pogba.mymausueb6.R;

import java.util.Arrays;


/**
* Provide the main menu for this demo application and allow to select the
* particular data access way
*
* @author Joern Kreutel
*
*/

public class DataAccessActivity extends Activity {


    /**
     * the logger
     */
    protected static final String logger = DataAccessActivity.class.getName();

    /**
     * the name of the argument that specifies the actual class to be used for
     * data access in the activities started from here
     */
    public static final String ARG_ACCESSOR_CLASS = "accessorClass";

    /**
     * the subpackage name for accessor
     */
    private static final String ACCESSORS_SUBPACKAGE = "accessors";


    /** Called when the activity is first created. */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {
            // set the list view as content view
            setContentView(R.layout.itemlistview);

            /*
             * access the list view for the options to be displayed
             */
            ListView listview = findViewById(R.id.list);

            // read out the options
            final String[] menuItems = getResources().getStringArray(
                    R.array.main_menu);

            /*
             * create an adapter that allows for the view to access the list's
             * content and that holds information about the visual
             * representation of the list items
             */
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, menuItems);

            // set the adapter on the list view
            listview.setAdapter(adapter);

            // set a listener that reacts to the selection of an element
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    String selectedItem = (String) parent.getAdapter().getItem(position);

                    Log.i(this.getClass().getName(), "got Item selected: " + selectedItem);

                    handleSelectedItem(selectedItem);
                    Log.i(logger, "handleSelectedItem()");
                }
            });


        } catch (Exception e) {

            String err = "got an exception: " + e;
            Log.e(logger, err, e);
            ((DataAccessApplication) getApplication()).reportError(this, err);
        }


    }

    private void handleSelectedItem(String selectedItem) {

        /*
         * depending on the selected item, we create an activity and pass it the
         * accessor class to be used. The class name will be created given the
         * selected item
         *
         * that quite lengthy expressions do the following:
         *
         * 1) determine the position of the selected item in the main_menu array
         * resource
         *
         * 2) read out that position from the main_menu_activities and
         * main_menu_accessors resources, respectively
         */

        try {
            Intent intent = new Intent(DataAccessActivity.this,
                    Class.forName(this.getClass().getPackage().getName()//.replace(".dataaccess","")
                        + "."
                        + getResources().getStringArray(
                                R.array.main_menu_activities)[Arrays
                                .asList(getResources().getStringArray(
                                        R.array.main_menu)).indexOf(selectedItem)]));


            intent.putExtra(
                    ARG_ACCESSOR_CLASS,
                    DataAccessActivity.class.getPackage().getName().replace(".dataaccess","")
                            + "."
                        +ACCESSORS_SUBPACKAGE
                        +"."
                    +getResources().getStringArray(
                                R.array.main_menu_accessors)[Arrays.asList(
                                getResources().getStringArray(
                                        R.array.main_menu)).indexOf(selectedItem)]);

            /*
             * start the activity
             */
            startActivity(intent);

        } catch (Exception e) {

            String err = "got exception trying to handle selected item "
                    + selectedItem + ": " + e;
            ((DataAccessApplication)getApplication()).reportError(this, err);
        }

    }
}
