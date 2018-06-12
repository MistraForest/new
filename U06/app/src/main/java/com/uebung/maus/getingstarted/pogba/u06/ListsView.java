package com.uebung.maus.getingstarted.pogba.u06;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

public class ListsView extends Activity {

    protected static final String logger = ListsView.class.getName();

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

    private ItemListAccessor accessor;

    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_ansicht);

        try {

            listview = findViewById(R.id.list_data);
            Button addButton = findViewById(R.id.add_button);


            accessor = new ItemListAccessor() {

                ArrayAdapter<TodoData> todoDataAdapter;
                ArrayList<TodoData> dataList = new ArrayList<>();

                @Override
                public ArrayList<TodoData> getAllItems() {
                    return this.dataList;
                }

                @Override
                public void addItem(TodoData item) {

                    this.todoDataAdapter.add(item);
                }

                @Override
                public ListAdapter getAdapter() {
                    //getAllItems();
                    this.todoDataAdapter = MyadapterClass.buildMyAdapter(ListsView.this, this.dataList);
                    this.todoDataAdapter.setNotifyOnChange(true);

                    return this.todoDataAdapter;
                }

                @Override
                public void updateItem(TodoData item) {

                    lookupData(item).updateFromTodo(item);
                    this.todoDataAdapter.notifyDataSetInvalidated();
                }

                @Override
                public void deleteItem(TodoData item) {

                    this.todoDataAdapter.remove(lookupData(item));
                }

                @Override
                public TodoData getSelectedItem(int itemPosition) {
                    return this.todoDataAdapter.getItem(itemPosition);
                }

                private TodoData lookupData(TodoData todoData){
                    for (TodoData currentData : this.dataList){
                        if (currentData.getId() == todoData.getId()){
                            return currentData;
                        }
                    }
                    return null;
                }

            };

            ListAdapter adapter = accessor.getAdapter();

            listview.setAdapter(adapter);


            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Log.i(logger, "onItemClick: position is: " + position
                            + ", id is: " + id);

                    TodoData data = accessor.getSelectedItem(position);

                    processItemSelection(data);
                }
            });

            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    processNewItemRequest();
                }
            });


        }catch (Exception e){
            String err = "got exception: " + e;
            Log.e(logger, err, e);
        }
    }

    private void processNewItemRequest() {
        Log.i(logger, "processNewItemRequest() called...");
        Intent intent = new Intent(ListsView.this, DetailView.class);

        startActivityForResult(intent, REQUEST_ITEM_CREATION);
    }

    private void processItemSelection(TodoData data) {
        Log.i(logger, "processItemSelection(" + data +")  called...");
        Intent intent = new Intent(ListsView.this, DetailView.class);

        intent.putExtra(ARG_ITEM_OBJECT, data);

        startActivityForResult(intent, REQUEST_ITEM_DETAILS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(logger, "onActivityResult(): " + data);

        TodoData item = data != null ? (TodoData) data
                .getSerializableExtra(ARG_ITEM_OBJECT) : null;

        // check which request we had
        if (requestCode == REQUEST_ITEM_DETAILS) {
            if (resultCode == RESPONSE_ITEM_EDITED) {
                Log.i(logger, "onActivityResult(): updating the edited item");
                this.accessor.updateItem(item);
            } else if (resultCode == RESPONSE_ITEM_DELETED) {
                this.accessor.deleteItem(item);
            }
            // this.listview.invalidate();
        } else if (requestCode == REQUEST_ITEM_CREATION
                && resultCode == RESPONSE_ITEM_EDITED) {
            Log.i(logger, "onActivityResult(): adding the created item");
            this.accessor.addItem(item);
        }

    }
}
