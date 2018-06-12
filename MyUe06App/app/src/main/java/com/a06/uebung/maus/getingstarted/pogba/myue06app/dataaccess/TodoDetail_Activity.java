package com.a06.uebung.maus.getingstarted.pogba.myue06app.dataaccess;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.a06.uebung.maus.getingstarted.pogba.myue06app.R;
import com.a06.uebung.maus.getingstarted.pogba.myue06app.dataaccessors.AbstractActivityItemAccessor;
import com.a06.uebung.maus.getingstarted.pogba.myue06app.dataaccessors.IntentDetailsAccessor;
import com.a06.uebung.maus.getingstarted.pogba.myue06app.dataaccessors.ItemDetailsAccessor;
import com.a06.uebung.maus.getingstarted.pogba.myue06app.model.TodoItem;

public class TodoDetail_Activity extends Activity {

    /**
     * the logger
     */
    protected static final String logger = TodoDetail_Activity.class.getName();

    /**
     * the accessor for dealing with the item to be displayed and edited
     */
    private ItemDetailsAccessor detailAccessor;

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_detail);

        try {

           /* Class<?> myClass = Class.forName("com.a06.uebung.maus.getingstarted.pogba.myue06app.dataaccessors.AbstractActivityItemAccessor");
            this.detailAccessor = (ItemDetailsAccessor) getClass().newInstance();

            if (this.detailAccessor instanceof AbstractActivityItemAccessor){
                ((AbstractActivityItemAccessor)detailAccessor).setActivity(this);
            }*/

           detailAccessor = new IntentDetailsAccessor();
           ((IntentDetailsAccessor) detailAccessor).setActivity(this);

            // obtain the ui elements
            final EditText itemTitle = findViewById(R.id.item_detail_title);
            final EditText itemDescription = findViewById(R.id.item_description);
            Button saveButton = findViewById(R.id.saveButton);
            Button deleteButton = findViewById(R.id.deleteButton);


            // if we do not have an item, we assume we need to create a new one
            //detailAccessor.readItem();
            if (detailAccessor.hasItem()){
                // set title and description
                Log.i(logger, "Accessor has item:" + detailAccessor.hasItem());
                itemTitle.setText(detailAccessor.readItem().getTitle());
                itemDescription.setText(detailAccessor.readItem().getDescription());
            }else {
                detailAccessor.createItem();

            }

            // we only set a listener on the ok button that will collect the
            // edited fields and set the values on the item
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(logger, "onClick(): saveButton has been clicked");
                    handleItemSaved(detailAccessor, itemTitle, itemDescription);
                }
            });

            // we also set a listener on the delete button
            // we only set a listener on the ok button that will collect the
            // edited fields and set the values on the item
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(logger, "onClick(): deleteButton has been clicked");
                    handleItemDeleted(detailAccessor);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleItemDeleted(ItemDetailsAccessor accessor) {

        Log.i(logger, "handleItemDeleted(): handle the deleting process ");
        accessor.deleteItem();

        // and finish
        finish();

    }

    private void handleItemSaved(ItemDetailsAccessor accessor, EditText itemTitle, EditText itemDescription) {

        Log.i(logger, "handleItemSaved(): Item is reset on the editable view");
        // re-set the fields of the item
        accessor.readItem().setTitle(itemTitle.getText().toString());
        accessor.readItem().setDescription(itemDescription.getText().toString());

        // save the item
        accessor.writeItem();

        // and finish
        finish();
    }
}
