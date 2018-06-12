package com.getingstarted.pogba.todosqlite;



public class MyTodo {


    private int _id;
    private String _name;
    private String _description;
    private  int _isDone;
    private  int _isFavourite;
    private long _dueDate;
    private long _time;

    public static final String YES = "0";
    public static final String NO = "1";



    public MyTodo() {
    }

    public MyTodo(String name, String description, int isDone, int isFavourite, long date, long time) {
        setName(name);
        setDescription(description);
        setIsDone(isDone);
        setFavourite(isFavourite);
        setDueDate(date);
        setTime(time);
    }

    public MyTodo(int id, String name, String description, int isDone, int isFavourite, long date, long time) {

        setId(id);
        setName(name);
        setDescription(description);
        setIsDone(isDone);
        setFavourite(isFavourite);
        setDueDate(date);
        setTime(time);

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



    public String getDescription() {
        return _description;
    }

    public void setDescription(String description) {
        this._description = description;
    }

    public int getFavourite() {
        return _isFavourite;
    }

    public void setFavourite(int isFavourite) {
        this._isFavourite = isFavourite;
    }

    public int getIsDone() {
        return _isDone;
    }

    public void setIsDone(int isDone) {
        this._isDone = isDone;
    }

    public long getTime() {
        return _time;
    }

    public void setTime(long time) {
        this._time = time;
    }

    public long getDueDate() {
        return _dueDate;
    }

    public void setDueDate(long dueDate) {
        this._dueDate = dueDate;
    }
}
