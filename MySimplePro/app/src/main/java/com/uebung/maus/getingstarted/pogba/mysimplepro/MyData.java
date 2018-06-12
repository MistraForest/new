package com.uebung.maus.getingstarted.pogba.mysimplepro;

import java.io.Serializable;

public class MyData implements Serializable {

    private String dataName;

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getDataName() {
        return dataName;
    }

    public MyData(String dataName){

        this.setDataName(dataName);
    }
    public MyData(){
    }

    @Override
    public String toString() {
        return "{MyData: " + this.getDataName() + "}";
    }

    public void updateData(MyData data){
        data.setDataName(data.getDataName());
    }
}
