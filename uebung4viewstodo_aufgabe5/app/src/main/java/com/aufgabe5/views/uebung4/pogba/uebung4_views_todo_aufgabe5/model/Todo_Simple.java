package com.aufgabe5.views.uebung4.pogba.uebung4_views_todo_aufgabe5.model;

import java.io.Serializable;

public class Todo_Simple implements Serializable {

    private static int ID = 0;

    private long id;
    private String name;
    private String description;
    private  boolean isDone;
    private  boolean isFavourite;
    private long dueDate;

    public Todo_Simple() {
    }

    public Todo_Simple(long id, String name, String description, boolean isDone, boolean isFavourite, long dueDate) {
        this.setId(id == -1 ? ID++ : id);
        this.setName(name);
        this.setDescription(description);
        this.setIsDone(isDone);
        this.setIsFavourite(isFavourite);
        this.setDueDate(dueDate);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Todo_Simple)) return false;

        Todo_Simple todoSimple = (Todo_Simple)obj;
        if (todoSimple.getId() == this.getId()
                && todoSimple.getDescription().equals(this.getDescription())
                && todoSimple.getName().equals(this.getName())
                && todoSimple.getIsDone() == this.getIsDone()
                && todoSimple.getIsFavourite() == this.getIsFavourite()
                && todoSimple.getDueDate() == this.getDueDate())
            return true;
        else return false;
    }

    public void updateFrom(Todo_Simple item) {
        this.setName(item.getName());
        this.setDescription(item.getDescription());
        this.setIsDone(item.getIsDone());
        this.setIsFavourite(item.getIsFavourite());
        this.setDueDate(item.getDueDate());
    }

    public String toString() {
        return "{DataItem " + this.getId() + " , " + this.getName() + " , " +this.getDueDate() +"}";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    public boolean getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
    }

    public long getDueDate() {
        return dueDate;
    }

    public void setDueDate(long dueDate) {
        this.dueDate = dueDate;
    }


}
