package com.getingstarted.pogba.myu6.dataAccess;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.getingstarted.pogba.myu6.R;
import com.getingstarted.pogba.myu6.accessor.AbstractActivityAccessor;
import com.getingstarted.pogba.myu6.accessor.DataItemAccessor;

public class ItemdetailsActivity extends Activity {

    public static final String logger = ItemdetailsActivity.class.getName();

    private DataItemAccessor dataItemAccessor ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemdetails_view);


        try {

            final EditText titleView = findViewById(R.id.item_title);
            final EditText descriptionView = findViewById(R.id.item_description);
            final Button saveButton = findViewById(R.id.saveButton);
            final Button deleteButton = findViewById(R.id.deleteButton);

            // instantiate the accessor class
            Class<?> accessorClass = Class.forName(getIntent().getStringExtra(HomeActivity.ARG_ACCESSOR_CLASS));
            this.dataItemAccessor = (DataItemAccessor) accessorClass.newInstance();

            // if we have an ActivityDataAccessor, we pass ourselves
            if (dataItemAccessor instanceof AbstractActivityAccessor){
                ((AbstractActivityAccessor) dataItemAccessor).setActivity(this);
            }

            // set the title of the activity given the accessor class
            setTitle(accessorClass.getName().substring(accessorClass.getName().indexOf(".") + 1));


            if(dataItemAccessor.hasItem()){
                titleView.setText(dataItemAccessor.readItem().getTitle());
                descriptionView.setText(dataItemAccessor.readItem().getBeschreibung());
            }else {
                dataItemAccessor.createItem();
            }

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleSave(dataItemAccessor, titleView, descriptionView);
                }
            });


            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleDelete(dataItemAccessor);
                }
            });


        } catch (Exception e) {
            String err = "Got exception: " + e;
            Log.e(logger, err, e);
            e.printStackTrace();
        }


    }

    private void handleDelete(DataItemAccessor dataItemAccessor) {
        dataItemAccessor.deleteItem();

        finish();
    }

    private void handleSave(DataItemAccessor dataItemAccessor, EditText titleView, EditText descriptionView) {
        dataItemAccessor.readItem().setTitle(titleView.getText().toString());
        dataItemAccessor.readItem().setBeschreibung(descriptionView.getText().toString());

        dataItemAccessor.writeItem();

        finish();
    }

}
