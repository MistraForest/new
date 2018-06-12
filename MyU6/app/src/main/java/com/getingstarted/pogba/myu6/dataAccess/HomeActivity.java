package com.getingstarted.pogba.myu6.dataAccess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.getingstarted.pogba.myu6.R;

public class HomeActivity extends Activity {


    /**
     * the name of the argument that specifies the actual class to be used for
     * data access in the activities started from here
     */
    public static final String ARG_ACCESSOR_CLASS = "accessorClass";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        final Button listButton = findViewById(R.id.list_button);
        final Button detailButton = findViewById(R.id.detail_button);

        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleListButton();
            }
        });

        detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDetailButton();
            }
        });
    }

    private void handleDetailButton() {
        Intent intent = new Intent(HomeActivity.this, ItemdetailsActivity.class);
        startActivity(intent);
    }

    private void handleListButton() {
        Intent intent = new Intent(HomeActivity.this, ListAnsichtActivity.class);
        startActivity(intent);
    }
}
