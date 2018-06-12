package com.getingstarted.pogba.myu6.dataAccess;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.getingstarted.pogba.myu6.R;
import com.getingstarted.pogba.myu6.model.DataItem;

import java.util.List;

public class MyAdapterListview {


    public static ArrayAdapter<DataItem> createItemAdapter(final Activity context, List<DataItem> items){

        return new ArrayAdapter<DataItem>(context, R.layout.item_in_listview, items){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View layout = convertView == null ? context.getLayoutInflater().inflate(
                        R.layout.item_in_listview, parent, false) : convertView;

                TextView titleView = layout.findViewById(R.id.itemName);
                titleView.setText(getItem(position).getTitle());

                return layout;
            }
        };
    }
}
