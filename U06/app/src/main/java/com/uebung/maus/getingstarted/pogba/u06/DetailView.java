package com.uebung.maus.getingstarted.pogba.u06;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


public class DetailView extends Activity {

    protected static final String logger = DetailView.class.getName();
    private ItemDetailsAccessor accessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_ansicht);

        try {

            final EditText itemName =  findViewById(R.id.item_name);
            final EditText itemDescription =  findViewById(R.id.item_description);
            Button saveButton =  findViewById(R.id.saveButton);
            Button deleteButton = findViewById(R.id.deleteButton);

            accessor = new ItemDetailsAccessor() {

                private TodoData data;

                @Override
                public TodoData readItem() {

                    Log.i(logger, "readItem() ");
                    if (this.data == null){
                        this.data = (TodoData)getIntent()
                                .getSerializableExtra(ListsView.ARG_ITEM_OBJECT);

                    }
                    return this.data;
                }

                @Override
                public void writeItem() {

                    Intent returnIntent = new Intent();

                    //this.data.setTitle(itemName.getText().toString());

                    returnIntent.putExtra(ListsView.ARG_ITEM_OBJECT, this.data);

                    setResult(ListsView.RESPONSE_ITEM_EDITED, returnIntent);
                }

                @Override
                public boolean hasItem() {
                    return readItem() != null;
                }

                @Override
                public void createItem() {

                }

                @Override
                public void deleteItem() {

                    Intent returnIntent = new Intent();

                    returnIntent.putExtra(ListsView.ARG_ITEM_OBJECT, this.data);

                    setResult(ListsView.RESPONSE_ITEM_DELETED, returnIntent);
                }
            };

            if (accessor.hasItem()){
                itemName.setText(accessor.readItem().getTitle());
                itemDescription.setText(accessor.readItem().getDescription());

            }else {
                accessor.createItem();
            }

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        processItemSave(itemName, itemDescription);
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    processItemDelete(accessor);
                }
            });



        } catch (Exception e) {
            String err = "got exception: " + e;
            Log.e(logger, err, e);
        }
    }

    private void processItemDelete(ItemDetailsAccessor accessor) {

        accessor.deleteItem();

        finish();
    }

    private void processItemSave(EditText itemName, EditText itemDescription) {
        Log.i(logger, "processItemSave()    " + accessor.readItem());
        accessor.readItem().setTitle(itemName.getText().toString());
        accessor.readItem().setDescription(itemDescription.getText().toString());

        accessor.writeItem();

        finish();
    }
}
