package com.aufgabe5.views.uebung4.pogba.uebung4_views_todo_aufgabe5;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login_Activity extends AppCompatActivity  {

    private static final String logger = Login_Activity.class.getName();
    //private LoginHandler loginHandler;
    private  final String INVALID_PWD = "Please enter exactly 6 numbers for password";//getString(R.string.invalid_pwd);
    private  final String INVALID_EMAIL = "Please enter a valid email addresse: sample@example.com";//getString(R.string.invalid_mail);

    private static final int IME_ACTION_NEXT = EditorInfo.IME_ACTION_NEXT;
    private static final int IME_ACTION_DONE = EditorInfo.IME_ACTION_DONE;
    private static final int PASSWORD_MAX_RESTRICTION = 6;


    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:123456"
            , "bar@example.com:123456"
            , "maus@thb.de:123456"
            , "MAuS18@thb-projekt.de:123456"
    };


    private static HashMap<View, String> errors = new HashMap<>();

    private     EditText emailTextField;
    private     EditText passwordField;
    private     TextView errorPWD;
    private     Button   loginButton;
    private     ProgressBar progressBar;
    private     TextView errorEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        loginButton = findViewById(R.id.login);
        //loginButton.setVisibility(View.GONE);
        loginButton.setEnabled(false);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        progressBar.setMax(10);

        emailTextField = findViewById(R.id.email);
        errorEmail = findViewById(R.id.error_email);
        passwordField = findViewById(R.id.password);
        errorPWD = findViewById(R.id.error_pwd);

        processOnEditorAction(emailTextField, errorEmail);
        processOnEditorAction(passwordField, errorPWD);

        moveErrorByTipping(emailTextField, errorEmail);
        moveErrorByTipping(passwordField, errorPWD);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(logger, "onClick(): View: "+ v + " is clicked");
               // progressBar.setProgress(1);
                LoginTask loginTask = new LoginTask(emailTextField.getText().toString(), passwordField.getText().toString());
                loginTask.execute((Integer) null);
               // new MyTysk().execute(10);
            }
        });

    }

    private class LoginTask extends AsyncTask<Integer, Integer, Boolean>{

        private String email;
        private String password;
        int count = 1;

        private LoginTask(String email, String password) {
            this.email = email;
            this.password = password;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(Integer... voids) {
            boolean result = false;
            try {
                // Simulate network access.
                result = checkCredentials();
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                return result;
            }


            return result;
        }

        private boolean checkCredentials() {
            boolean result = false;
            for (String credentials : DUMMY_CREDENTIALS){
                String[] logInfo = credentials.split(":");
                count++;
                publishProgress(count);//(int)((double)count/logInfo.length)* 10);
                if (logInfo[0].equals(email)){
                    result = logInfo[1].equals(password);
                }

            }
            return result;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Boolean succes) {
            if (succes){
                Log.i(logger, "Succes: " + succes);
            processLogin();
                progressBar.setVisibility(View.GONE);
             } else {
                errorEmail.setText("Permission denied! Check your email or password");
                emailTextField.requestFocus();
                passwordField.setText("");
                progressBar.setVisibility(View.GONE);
             }
        }
    }

    /*class MyTysk extends AsyncTask<Void, Void, Boolean>{

           // private ProgressDialog dialog = null;
            Integer count = 1;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar.setVisibility(View.VISIBLE);
                //progressBar.setProgress(0);
                // dialog = ProgressDialog.show(Login_Activity.this,"Please wait!","...processing the login informations");
            }

            @Override
            protected void onPostExecute(boolean o) {
                //dialog.cancel();
                progressBar.setVisibility(View.GONE);
                //Toast.makeText(Login_Activity.this,o, Toast.LENGTH_LONG).show();
            }

            @Override
            protected void onProgressUpdate(Void... values) {
               // super.onProgressUpdate(values[0]);
                //count +=2;
                //progressBar.setProgress(values[0]);

            }

            @Override
            protected Boolean doInBackground(Void... voids) {
                    if (!validateEmail()){
                        setErrorOnTextView(emailTextField, errorEmail, INVALID_EMAIL);
                    }if (!validatePWD()){
                        setErrorOnTextView(passwordField, errorPWD, INVALID_PWD);
                    }

               // for (;count <= voids[0];count++){
                    try {
                        Thread.sleep(3000);
                        //publishProgress(count +=2);

                    } catch (InterruptedException e) {
                        Log.e(logger, "Error on doInBackground(): ", e);
                    }
               // }
                processLogin();

                return true;//"Processing finished";
            }

    }*/

  /*  private void validateLogin(){
        if (!(validateEmail() && validatePWD())) {
            processLogin();
        }else {
            if (!validateEmail()){
                setErrorOnTextView(emailTextField, errorEmail, INVALID_EMAIL);
            }if (!validatePWD()){
                setErrorOnTextView(passwordField, errorPWD, INVALID_PWD);
            }
        }
    }*/


    private void processLogin() {
        Log.i(logger, "processLogin(): going to list view...");
        if (validateEmail() && validatePWD())
            startActivity(new Intent(Login_Activity.this, TodoListActivity.class));


    }

    private void processOnEditorAction(final EditText field, final TextView textView) {
        Log.i(logger, "processOnEditorAction() on View: "+ field);
        field.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.i(logger, "onEditorAction() on: "+ v+ "  with action "+ actionId);
                return isFieldValidated(field, textView, actionId);
            }
        });
    }

    private void moveErrorByTipping(final EditText editTextField, final TextView textViewError) {
        Log.i(logger, "moveErrorByTipping() on Field "+ editTextField);
        editTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                disableLoginBtn();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i(logger,"onTextChanged()... ");
                removeErrorOnTextView(editTextField,textViewError);
                enableLoginBtn();
            }

            @Override
            public void afterTextChanged(Editable s) {
                enableLoginBtn();
            }

        });
    }

    private  void disableLoginBtn() {
        if (!(validateEmail() && !validatePWD())){
            //loginButton.setVisibility(View.GONE);
            loginButton.setEnabled(false);
        }
    }

    private void enableLoginBtn() {
        if (validateEmail() && validatePWD()){
           // loginButton.setVisibility(View.VISIBLE);
            loginButton.setEnabled(true);
        }
    }

       /*
        * Validate the editable text field  by pressing next or done on key bord.
        * If an error occur it will be write in the given textView
        * @param editText The editText be validate
        * @param textView the textView for writing possible error
        * @param actionId  the pressed key
        */
     private  boolean isFieldValidated(EditText editText, TextView textView, int actionId) {
        Log.i(logger,
                "isFieldValidated(): processing field validation on... "+ editText);

        return actionId == IME_ACTION_NEXT
                ? setUpAction(editText, validateEmail(), INVALID_EMAIL, textView)
                : (actionId == IME_ACTION_DONE) && setUpAction(editText, validatePWD(), INVALID_PWD, textView);
    }

    private boolean setUpAction(EditText editText, boolean isFieldValid, String error, TextView textView) {
        Log.i(logger, "setUpAction()....");
        if (!isFieldValid) {
            setErrorOnTextView(editText,textView, error);
            return true;
        } else {
            removeErrorOnTextView(editText, textView);
            return false;
        }
    }

    private boolean validatePWD() {

        Log.i(logger, "validatePWD(): Password validation...");
        String password = passwordField.getText().toString();

        if (password.trim().isEmpty() || password.length() < PASSWORD_MAX_RESTRICTION){
            //setErrorOnTextView(passwordField, errorPWD, "Password field is empty. Please enter a password");
            setErrorOnTextView(passwordField,errorPWD, INVALID_PWD);
            removeErrorOnTextView(passwordField, errorPWD);
            return  false;
        }else
            return true;
    }

    private void removeErrorOnTextView(EditText editTextField, TextView textView) {
        setErrors(editTextField, printMsg(""));
        textView.setText(getErrors(editTextField));
    }
    private void setErrorOnTextView(EditText v, TextView textView, String txt) {
        setErrors(v, printMsg(txt));
        textView.setText(getErrors(v));
    }

    private String printMsg(String msg) {
        return msg;
    }

    private boolean validateEmail() {
        Log.i(logger, "validateEmail(): Email validation...");
        String target = emailTextField.getText().toString();
        String EMAIL_PATTERN = "[a-zA-Z0-9+._%\\-]{1,256}" +
                "@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+";
        Matcher matcher = Pattern.compile(EMAIL_PATTERN).matcher(target);
      //  Log.i(logger, "validateEmail(): Email validation RETURN..." + matcher.matches());
        return matcher.matches();
    }

    private void setErrors(View v, String error){
        errors.put(v, error);
    }
    private static String getErrors(View v){
        return errors.get(v);
    }


}
