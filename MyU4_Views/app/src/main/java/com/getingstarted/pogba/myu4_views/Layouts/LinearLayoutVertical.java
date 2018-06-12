package com.getingstarted.pogba.myu4_views.Layouts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.getingstarted.pogba.myu4_views.R;

public class LinearLayoutVertical extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linear_vertical);

        Button okButton =findViewById(R.id.ok);

        okButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i(LinearLayoutVertical.class.getName(), "onClick(): " + v);
                startActivity(new Intent(LinearLayoutVertical.this, TableLayout.class));
            }
        });




    }
}
