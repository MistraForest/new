package com.getingstarted.pogba.contactsqlite;

public class Contact {

    private int _id;
    private String _name;
    private String _phoneNumber;

    public Contact() {
    }

    public Contact(String name, String phoneNumber) {
        setName(name);
        setPhoneNumber(phoneNumber);
    }

    public Contact(int id, String name, String phoneNumber) {
        setId(id);
        setName(name);
        setPhoneNumber(phoneNumber);
    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public String getPhoneNumber() {
        return _phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this._phoneNumber = phoneNumber;
    }
}
