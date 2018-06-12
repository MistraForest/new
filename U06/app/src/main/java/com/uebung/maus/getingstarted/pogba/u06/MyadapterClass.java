package com.uebung.maus.getingstarted.pogba.u06;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyadapterClass {

    public static ArrayAdapter<TodoData> buildMyAdapter(final Activity activity, ArrayList<TodoData> dataList){

        return new ArrayAdapter<TodoData>(activity, R.layout.data_in_list, dataList){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View view = convertView;
                if (view == null){
                    view = activity.getLayoutInflater().inflate(R.layout.data_in_list, parent, false);
                }else view = convertView;

                final TextView titleText = view.findViewById(R.id.data_title);
                titleText.setText(getItem(position).getTitle());

                titleText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), titleText.getText(), Toast.LENGTH_SHORT).show();
                    }
                });

                return view;
            }
        };
    }
}
