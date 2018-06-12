package com.uebung.maus.getingstarted.pogba.u06;

import java.io.Serializable;

public class TodoData implements Serializable {

    private static int ID = 0;

    private Long id;
    private String title;
    private String description;
    private boolean done;
    private boolean important;
    private Long date;

    public TodoData(){

    }

    public TodoData(Long id, String title, String description, boolean done, boolean important, Long date) {
        this.setId(id == -1 ? ID++ : id);
        this.setTitle(title);
        this.setDescription(description);
        this.setDone(done);
        this.setImportant(important);
        this.setDate(date);
    }


    public void updateFromTodo(TodoData todoItem){

        this.setTitle(todoItem.getTitle());
        this.setDescription(todoItem.getDescription());
        this.setDone(todoItem.isDone());
        this.setImportant(todoItem.isImportant());
        this.setDate(todoItem.getDate());

    }


    @Override
    public String toString() {
        return "{TodoItem: id = " + this.getId() + " title = " + this.getTitle() + " Desc. = " + this.getDescription() + "}";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }
}
