package com.getingstarted.pogba.contactsqlite;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends Activity {

    private EditText name;
    private EditText phoneNo;

    private Button putButton;
    private Button countButton;
    private Button deleteButton;
    private Button listAllButton;

    private DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fillDatabase();

        name = (EditText) findViewById(R.id.name_id);
        phoneNo = (EditText) findViewById(R.id.phone_no_id);

        putButton = (Button) findViewById(R.id.button_put_id);
        countButton = (Button) findViewById(R.id.button_count_id);
        deleteButton = (Button) findViewById(R.id.button_delete_id);
        listAllButton = (Button) findViewById(R.id.button_list_all_id);

        putButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fillDatabase();
                addEditTextContact();
            }
        });

        countButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toasterCounter();
            }
        });



        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               deleteContact();
            }
        });

        listAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listAllContacts();
            }
        });


    }

    public void deleteContact(){
        String na = name.getText().toString();
        List<Contact> allContacts = db.getAllContacts();
        if (!(name.getText().toString().matches("") || phoneNo.getText().toString().matches(""))){
            for (Contact c : allContacts){
                if (c.getName().matches(na))
                    db.deleteContact(c);
            }
            Toast.makeText(this,na+" is delete" ,Toast.LENGTH_SHORT).show();
            name.setText("");
            phoneNo.setText("");
        }else {
            Toast.makeText(this," Fill the fields to delete contact" ,Toast.LENGTH_SHORT).show();
        }

    }

    public void listAllContacts(){
        int i = 0;
        List<Contact> allContacts = db.getAllContacts();
        for (Contact c : allContacts){
            String all = "\tId: " + c.getId() + "\tName: " + c.getName() + "\t\t\tPhone Number: " + c.getPhoneNumber();

            i++;
            Log.d("Contact " + i +":", all);
        }
        Toast.makeText(this,"Contacts are listed in Android Monitor", Toast.LENGTH_SHORT ).show();
    }

    public void toasterCounter(){
        db.getContactsCount();
        Log.d("Total: ", "\t"+ db.getContactsCount());
        Toast.makeText(this,"Count = "+db.getContactsCount(),Toast.LENGTH_SHORT).show();
    }

    public void addEditTextContact(){

        Contact contact = new Contact();

        if (!(name.getText().toString().matches("") || phoneNo.getText().toString().matches(""))){

            contact.setName(name.getText().toString());
            contact.setPhoneNumber(phoneNo.getText().toString());
            db.addContact(contact);

            Toast.makeText(this,"Contact "+ contact.getName()+" is added",Toast.LENGTH_SHORT).show();
            name.setText("");
            phoneNo.setText("");
        }else {
            Toast.makeText(this,"Fill the fields to add contact", Toast.LENGTH_SHORT).show();
        }



    }

    public void fillDatabase(){

        db.addContact(new Contact("Mistra", "01259875222"));
        db.addContact(new Contact("Solo", "1875222501"));
        db.addContact(new Contact("Youssouf", "01754836910"));
        db.addContact(new Contact("Salif", "014775222"));
        db.addContact(new Contact("Andre", "58732600145"));
        db.addContact(new Contact("David", "0147895632"));

        Log.d(this.getPackageName(), "Reading all Contact");
        List<Contact> contactList = db.getAllContacts();

        int i = 0;
        for (Contact c : contactList){
            String all = "\tId: " + c.getId() + "\tName: " + c.getName() + "\t\t\tPhone Number: " + c.getPhoneNumber();

            i++;
            Log.i("Contact " + i +":", all);
        }
    }

}
