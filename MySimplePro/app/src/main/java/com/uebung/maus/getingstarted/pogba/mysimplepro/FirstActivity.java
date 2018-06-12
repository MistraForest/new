package com.uebung.maus.getingstarted.pogba.mysimplepro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FirstActivity extends Activity {

    public static final String EXTRA_MSG = FirstActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        final EditText editText = findViewById(R.id.edit);
        Button transmit = findViewById(R.id.transmit);

        transmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                intent.putExtra(EXTRA_MSG, new MyData(editText.getText().toString()));

                startActivity(intent);
            }
        });

    }
}
