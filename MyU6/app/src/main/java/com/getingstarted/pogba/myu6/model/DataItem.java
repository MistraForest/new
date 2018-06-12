package com.getingstarted.pogba.myu6.model;

import java.io.Serializable;

public class DataItem implements Serializable {


    /**
     * the static id assignment
     */
    private static int ID = 1;

    private Long id;
    private String title;
    private String beschreibung;
    private boolean erledigt;
    private boolean wichtig;
    private Long datum;


    public DataItem() {
    }

    public DataItem(Long id, String title, String beschreibung, boolean erledigt, boolean wichtig, Long datum) {
        this.setId(id == -1 ? ID++ : id);
        this.setTitle(title);
        this.setBeschreibung(beschreibung);
        this.setErledigt(erledigt);
        this.setWichtig(wichtig);
        this.setDatum(datum);
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

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public boolean isErledigt() {
        return erledigt;
    }

    public void setErledigt(boolean erledigt) {
        this.erledigt = erledigt;
    }

    public boolean isWichtig() {
        return wichtig;
    }

    public void setWichtig(boolean wichtig) {
        this.wichtig = wichtig;
    }

    public Long getDatum() {
        return datum;
    }

    public void setDatum(Long datum) {
        this.datum = datum;
    }


    public void updateFromData(DataItem item){
        this.setTitle(item.getTitle());
        this.setBeschreibung(item.getBeschreibung());
        this.setErledigt(item.isErledigt());
        this.setWichtig(item.isWichtig());
        this.setDatum(item.getDatum());
    }

    @Override
    public String toString() {
        return "{DataItem " + this.getId() + " " + this.getTitle() + "}";
    }
}
