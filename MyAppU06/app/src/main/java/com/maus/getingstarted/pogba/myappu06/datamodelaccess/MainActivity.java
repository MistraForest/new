package com.maus.getingstarted.pogba.myappu06.datamodelaccess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.maus.getingstarted.pogba.myappu06.R;
import com.maus.getingstarted.pogba.myappu06.model.TodoItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    public static final String logger = MainActivity.class.getName();
    private static final String ARG_ITEM_OBJECT = "itemObjet";
    private static final int REQUEST_ITEM_DETAILS = 1;

    private List<TodoItem> itemList = new ArrayList<>();
    private ListView listView;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

        listView = findViewById(R.id.list);
        addButton = findViewById(R.id.addButton);

        final MyAdapter adapter = new MyAdapter(this, getItemList());

        Log.i(logger, "setting the Adapter");
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_LONG).show();
                TodoItem todoItem = adapter.getItem(position);
                handleListClicked(todoItem);
            }
        });

    }

    private void handleListClicked(TodoItem todoItem) {
        Log.i(logger, "handleSelectedItem(): " + todoItem);

        // create an intent for opening the details view
        Intent intent = new Intent(MainActivity.this, TodoDetailsActivity.class);

        // pass the item to the intent
        intent.putExtra(ARG_ITEM_OBJECT, intent);

        // start the details activity with the intent
        startActivityForResult(intent, REQUEST_ITEM_DETAILS);
    }

    public List<TodoItem> getItemList() {
        TodoItem todoItem = new TodoItem(-1L, "Foot", "11 gegen 11", false, false, null);
        itemList.add(todoItem);
        itemList.add(new TodoItem(-2L,"Handball","collectiv",true, false, 552222L));
        return this.itemList;
    }

}
