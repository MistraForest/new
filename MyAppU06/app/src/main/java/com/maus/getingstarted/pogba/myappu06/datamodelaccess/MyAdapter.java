package com.maus.getingstarted.pogba.myappu06.datamodelaccess;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.maus.getingstarted.pogba.myappu06.R;
import com.maus.getingstarted.pogba.myappu06.model.TodoItem;

import java.util.List;

public class MyAdapter extends ArrayAdapter<TodoItem> {

    public static final String logger = MyAdapter.class.getName();

    private Activity context;
    private List<TodoItem> itemList;


    public MyAdapter(final Activity context, List<TodoItem> objects) {
        super(context, R.layout.list_view, objects);
        this.context = context;
        this.itemList = objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View layout = convertView == null ? this.context.getLayoutInflater().
                inflate(R.layout.list_view, parent, false) : convertView;

        final MyViewHolder viewHolder = new MyViewHolder();
        viewHolder.titleView = layout.findViewById(R.id.itemTitle);

        Log.i(logger, "attach the item " +getItem(position).toString()+" to the view");
        viewHolder.titleView.setText(getItem(position).getTitle());

        layout.setTag(viewHolder);

        return  layout;
    }

    static class MyViewHolder{
        protected TextView titleView;
    }


}
