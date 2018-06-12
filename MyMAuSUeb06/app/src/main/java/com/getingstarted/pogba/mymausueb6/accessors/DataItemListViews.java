package com.getingstarted.pogba.mymausueb6.accessors;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.getingstarted.pogba.mymausueb6.R;
import com.getingstarted.pogba.mymausueb6.model.DataItem;

import java.util.List;

public class DataItemListViews  {
    public static ArrayAdapter<DataItem> createDataItemArrayAdapter(final Activity aContext, final List<DataItem> aItems) {

        return new ArrayAdapter<DataItem>(aContext, R.layout.item_in_listview, aItems){

            @Override
            public View getView(int position, View listItemView, ViewGroup parent) {

                View layout = listItemView == null ? aContext.getLayoutInflater().inflate(
                        R.layout.item_in_listview, parent, false
                ) : listItemView;

                // getActivity().getLayoutInflater().inflate(
                // R.layout.item_in_listview, listView, false);
                final TextView itemView =  layout.findViewById(R.id.itemName);
                itemView.setText(getItem(position).getName());


                final TextView typeView = layout.findViewById(R.id.itemType);
                typeView.setText(getItem(position).getType().toString());

                typeView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), typeView.getText(), Toast.LENGTH_LONG).show();
                    }
                });



                return layout;
            }
        };
    }
}
