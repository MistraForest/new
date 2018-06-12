package com.a06.uebung.maus.getingstarted.pogba.myue06app.dataaccess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.a06.uebung.maus.getingstarted.pogba.myue06app.R;
import com.a06.uebung.maus.getingstarted.pogba.myue06app.dataaccessors.AbstractActivityItemAccessor;
import com.a06.uebung.maus.getingstarted.pogba.myue06app.dataaccessors.ItemListAccessor;
import com.a06.uebung.maus.getingstarted.pogba.myue06app.dataaccessors.ItemListImplement;
import com.a06.uebung.maus.getingstarted.pogba.myue06app.model.TodoItem;

import java.util.ArrayList;
import java.util.List;


/**
 * Show a list of items
 *
 * @author Mistra Forest
 *
 */
public class TodoList_Activity extends Activity {

    protected static final String logger = TodoList_Activity.class.getName();

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

    private List<TodoItem> itemList = new ArrayList<>();

    /**
     * the field for the listview
     */
    private ListView listView;


    /**
     * the data accessor for the data items
     */
    private ItemListAccessor listAccessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_list);

        try {

           /* Class<?> classA = Class.forName(AbstractActivityItemAccessor.class.getName());
            this.listAccessor = (ItemListAccessor) classA.newInstance();

            if (listAccessor instanceof AbstractActivityItemAccessor){
                ((AbstractActivityItemAccessor)listAccessor).setActivity(this);
            }*/


           listAccessor = new ItemListImplement();
           ((ItemListImplement) listAccessor).setActivity(this);

            /*
             * access the list view for the options to be displayed
             */
            listView = findViewById(R.id.list);

            // the button for adding new items
            Button addButton = findViewById(R.id.addButton);

            // obtain the adapter from the accessor, passing it the id of the
            // item layout to be used
            final ListAdapter adapter = this.listAccessor.getAdapter();

            // set the adapter on the list view
            listView.setAdapter(adapter);

            // set a listener that reacts to the selection of an element
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.i(logger, "onItemClick(): position is: " + position
                            + ", id is: " + id);
                    Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();

                    TodoItem todoItem = listAccessor.getSelectedItem(position, id);
                    itemList.add(todoItem);

                    // this.notifyAll();
                    handleSelectedItem(todoItem);


                }
            });


            // set the listview as scrollable
            listView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

            // set a listener for the newItemButton
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(logger, "Add Button was clicked");

                    handleNewItemRequest();

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    /*    ListviewAdapter adapter = new ListviewAdapter(this, getItemList());

        Log.i(logger, "setting the Adapter");
        listView.setAdapter(adapter);
    }

    public List<TodoItem> getItemList() {
        TodoItem todoItem = new TodoItem(-1L, "Foot", "11 gegen 11", false, false, null);
        itemList.add(todoItem);
        itemList.add(new TodoItem(-2L,"Handball","collectiv",true, false, 552222L));
        return this.itemList;
    }*/
    }

    private void handleNewItemRequest() {

        Log.i(logger, "handleNewItemRequest(): ");

        // create an intent for opening the details view
        Intent intent = new Intent(TodoList_Activity.this, TodoDetail_Activity.class);

        // start the details activity with the intent
        startActivityForResult(intent, REQUEST_ITEM_CREATION);

    }

    private void handleSelectedItem(TodoItem todoItem) {

        Log.i(logger, "handleSelectedItem(): " + todoItem);

        // create an intent for opening the details view
        Intent intent = new Intent(TodoList_Activity.this, TodoDetail_Activity.class);

        // pass the item to the intent
        intent.putExtra(ARG_ITEM_OBJECT, todoItem);

        // start the details activity with the intent
        startActivityForResult(intent, REQUEST_ITEM_DETAILS);
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
        //super.onActivityResult(requestCode, resultCode, data);

        Log.i(logger, "onActivityResult(): " + data + " requestCode:" + requestCode
        + " resultCode:" + resultCode);

        final TodoItem todoItem = data != null ? (TodoItem)data.getSerializableExtra(ARG_ITEM_OBJECT)
                                    : null;

        Log.i(logger, "The return data is: " + todoItem.toString());

        Log.i(logger, "requestCode == REQUEST_ITEM_DETAILS ? "+ (requestCode == REQUEST_ITEM_DETAILS));
        Log.i(logger, "requestCode == REQUEST_ITEM_CREATION ? "+ (requestCode == REQUEST_ITEM_CREATION));
        if (requestCode == REQUEST_ITEM_DETAILS){

            if (resultCode == RESPONSE_ITEM_EDITED){

               // Log.i(logger, "resultCode == RESPONSE_ITEM_EDITED ? "+ (resultCode == RESPONSE_ITEM_EDITED));
                Log.i(logger, "onActivityResult(): updating the edited item");
                listAccessor.updateItem(todoItem);


            }else if (resultCode == RESPONSE_ITEM_DELETED){

                Log.i(logger, "onActivityResult(): deleting the item");
                listAccessor.deleteItem(todoItem);
            }

        }else if (requestCode == REQUEST_ITEM_CREATION &&
                  resultCode == RESPONSE_ITEM_DELETED){

            //Log.i(logger, "requestCode == REQUEST_ITEM_CREATION ? "+ (requestCode == REQUEST_ITEM_CREATION));
            Log.i(logger, "onActivityResult(): adding the created item");
            listAccessor.addItem(todoItem);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(logger,"onDestroy(): will signal finalisation to the accessors");
        this.listAccessor.close();

        onStop();
    }
}
