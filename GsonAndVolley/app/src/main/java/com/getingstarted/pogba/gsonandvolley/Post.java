package com.getingstarted.pogba.gsonandvolley;

import com.google.gson.annotations.SerializedName;

import java.util.Date;


public class Post {

    @SerializedName("id")
    private long ID;

    @SerializedName("date")
    private Date dateCreated;

    private String title;
    private String author;
    private String url;
    private String body;

    public Post() {
    }

    public Post(long ID, Date dateCreated, String title, String author, String url, String body) {
        this.setID(ID);
        this.setDateCreated(dateCreated);
        this.setTitle(title);
        this.setAuthor(author);
        this.setUrl(url);
        this.setBody(body);
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
