package com.getingstarted.pogba.contactsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "contactsManager";

    private static final String TABLE_CONTACTS = "contacts";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PH_NO = "phoneNumber";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CONTACTS_TABLE = "CREATE TABLE "+TABLE_CONTACTS+"("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_NAME + " TEXT NOT NULL,"
                + COLUMN_PH_NO + " TEXT NOT NULL" + ")";

        db.execSQL(CREATE_CONTACTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        onCreate(db);

    }

    public void addContact(Contact contact){


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, contact.getName());
        values.put(COLUMN_PH_NO, contact.getPhoneNumber());

        db.insert(TABLE_CONTACTS, null, values);
        db.close();

    }

    public Contact getContact(int id){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_PH_NO},
                COLUMN_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));

        return  contact;

    }

    public List<Contact> getAllContacts(){

        List<Contact> contactList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do{
                Contact contact = new Contact();

                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));

                contactList.add(contact);

            }while (cursor.moveToNext());
        }

        return contactList;

    }

    public int getContactsCount(){

        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT * FROM " + TABLE_CONTACTS;

        Cursor cursor = db.rawQuery(countQuery, null);
        //cursor.close();

        return cursor.getCount();

    }

    public int updateContact(Contact contact){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, contact.getName());
        values.put(COLUMN_PH_NO, contact.getPhoneNumber());


        return db.update(TABLE_CONTACTS, values, COLUMN_ID + "=?", new String[]{String.valueOf(contact.getId())});

    }

    public void deleteContact(Contact contact){

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_CONTACTS, COLUMN_ID + "=?", new String[]{String.valueOf(contact.getId())});

        db.close();

    }
}
