package com.uebung.maus.getingstarted.pogba.mysimplepro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DataAdapter extends ArrayAdapter<MyData> {


    private Context context;
    private ArrayList<MyData> myDataList;



    public DataAdapter(Context context, int resource, ArrayList<MyData> objects) {
        super(context, resource, objects);
        this.context = context;
        this.myDataList = objects;
        setNotifyOnChange(true);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MyData myData = myDataList.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null){

            convertView = inflater.inflate(R.layout.data_layout, parent, false);

            TextView testName = convertView.findViewById(R.id.data);

            testName.setText(myData.getDataName());

        }


        return convertView;
    }
}
