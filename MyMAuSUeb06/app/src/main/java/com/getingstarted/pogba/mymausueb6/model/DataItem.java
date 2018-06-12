package com.getingstarted.pogba.mymausueb6.model;

import java.io.Serializable;
import java.net.URL;

public class DataItem implements Serializable {

    /**
     * some static id assignment
     */
    private static int ID = 0;

    /**
     * the fields
     */
    private long id;
    private ItemTypes type;
    private String name;
    private String description;
    private URL iconUrl;

    public void setType(ItemTypes type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIconUrl(URL iconurl) {
        this.iconUrl = iconurl;
    }

    public DataItem(long id,ItemTypes type, String name, String description,
                    URL iconUrl) {
        this.setId(id == -1 ? ID++ : id);
        this.setType(type);
        this.setName(name);
        this.setDescription(description);
        this.setIconUrl(iconUrl);
    }

    public DataItem() {
        // TODO Auto-generated constructor stub
    }

    public ItemTypes getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    /**
     * TODO implement
     * @return
     */
    public URL getIconUrl() {
        return this.iconUrl;
    }

    public enum ItemTypes {
        TYPE1, TYPE2, TYPE3
    }

    public void updateFrom(DataItem item) {
        this.setName(item.getName());
        this.setDescription(item.getDescription());
        this.setType(item.getType());
        this.setIconUrl(item.getIconUrl());
    }

    public String toString() {
        return "{DataItem " + this.getId() + " " + this.getName() + "}";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
