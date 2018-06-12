package com.getingstarted.pogba.editho;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.*;
import android.widget.*;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout edithLayout;
    private Button redButton;
    private TextView loginIfo;
    private EditText userName;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        //Layout
        setContent();

        redButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginIfo.setText("Your Login-Information ");
                showUserInfo();
            }
        });

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    public void showUserInfo(){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.custom_toast_container));

        TextView text = (TextView) layout.findViewById(R.id.text_mister);
        text.setText("Username = "+getUserName()+"\n Password= "+getPassword());

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public String getUserName(){
        String user = userName.getText().toString();
        return user;
    }
    public String getPassword(){
        String pwd = password.getText().toString();
        return pwd;
    }

    public void clearUserInfo(){
        loginIfo.setText("Please Login ");
        userName.setText("");
        password.setText("");
    }

    public  void setContent(){
        edithLayout = new RelativeLayout(this);
        edithLayout.setBackgroundColor(Color.GREEN);

        //Button
        redButton = new Button(this);
        redButton.setText("Log in");
        redButton.setBackgroundColor(Color.RED);

        //TextView
        loginIfo = new TextView(this);
        loginIfo.setText("Please Login");

        //User input
        userName = new EditText(this);
        password = new EditText(this);

        redButton.setId(1);
        userName.setId(2);
        password.setId(3);
        loginIfo.setId(4);

        //Button rules
        RelativeLayout.LayoutParams buttonDetails = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        //userName rules
        RelativeLayout.LayoutParams userNameDetails = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        //password rules
        RelativeLayout.LayoutParams passwordDetails = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        //LoginInfo Rules
        RelativeLayout.LayoutParams loginDetails = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        //Give Rules to positions Widget

        //add Button rules
        buttonDetails.addRule(RelativeLayout.CENTER_HORIZONTAL);
        buttonDetails.addRule(RelativeLayout.CENTER_VERTICAL);

        // add username rules
        userNameDetails.addRule(RelativeLayout.ABOVE,password.getId());
        userNameDetails.addRule(RelativeLayout.CENTER_HORIZONTAL);
        userNameDetails.setMargins(0,0,0,50);
        passwordDetails.addRule(RelativeLayout.ABOVE,userName.getId());

        //add Login Info rules
        loginDetails.addRule(RelativeLayout.ABOVE, userName.getId());
        loginDetails.addRule(RelativeLayout.CENTER_HORIZONTAL);
        loginDetails.setMargins(0,0,0,50);

        //add password rules
        passwordDetails.addRule(RelativeLayout.ABOVE, redButton.getId());
        passwordDetails.addRule(RelativeLayout.CENTER_HORIZONTAL);
        passwordDetails.setMargins(0,0,0,50);

        //Convert Pixel to dip
        Resources r = getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,250,
                r.getDisplayMetrics());

        //it's going to work to any device(phone, tablet,...)
        userName.setWidth(px);
        password.setWidth(px);
        loginIfo.setWidth(px);

        //add the Widget to the Layout
        edithLayout.addView(redButton, buttonDetails);
        edithLayout.addView(userName, userNameDetails);
        edithLayout.addView(password, passwordDetails);
        edithLayout.addView(loginIfo, loginDetails);

        setContentView(edithLayout);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
