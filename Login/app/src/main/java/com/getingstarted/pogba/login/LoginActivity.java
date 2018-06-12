package com.getingstarted.pogba.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
    private  Button signIn_Button;
    private EditText userName;
    private EditText userPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signIn_Button = findViewById(R.id.sign_in_button_id);
        userName = findViewById(R.id.username_id);
        userPassword = findViewById(R.id.password_id);

        signIn_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView loginStatus = findViewById(R.id._forest_text);
                loginStatus.setText("Your Login Information:");
                onClickSignButton();
            }
        });

        signIn_Button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                clearUserInfo();
                return true;
            }
        });


    }

    public void onClickSignButton(){
        Toast myToast = Toast.makeText(getApplicationContext(),
                "Username = "+getUserName()+"\n Password= "+getPassword(),
                Toast.LENGTH_LONG);
        myToast.show();
    }

    public String getUserName(){
        String user = userName.getText().toString();
        return user;
    }
    public String getPassword(){
        String password = userPassword.getText().toString();
        return password;
    }

    public void clearUserInfo(){
        TextView loginStatus = findViewById(R.id._forest_text);
        loginStatus.setText("Please Login ");
        userName.setText("");
        userPassword.setText("");
    }
}
