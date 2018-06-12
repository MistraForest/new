package com.a06.uebung.maus.getingstarted.pogba.myue06app.dataaccessors;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.a06.uebung.maus.getingstarted.pogba.myue06app.R;
import com.a06.uebung.maus.getingstarted.pogba.myue06app.model.TodoItem;

import java.util.List;

public class ListviewAdapter extends ArrayAdapter<TodoItem> {

    private static final String logger = ListviewAdapter.class.getName();

    private Activity context;
    private  List<TodoItem> itemList;


    static class MyViewHolder{
        protected TextView titleView;
    }

    public ListviewAdapter(final Activity aContext, List<TodoItem> items) {
        super(aContext, R.layout.todo_list, items);
        this. context = aContext;
        this.itemList = items;
       // setNotifyOnChange(true);
    }

    public void addItem(TodoItem item){
        itemList.add(item);
        notifyDataSetChanged();
    }


    @Override
    public View getView(int itemPosition, View listItemView, ViewGroup parent) {

        View myLayout = listItemView == null ? context.getLayoutInflater().inflate(
                R.layout.todo_list, parent, false)
                                             : listItemView;

        final  MyViewHolder holder = new MyViewHolder();
        holder.titleView = myLayout.findViewById(R.id.itemTitle);
        Log.i(logger, "getting the item " +getItem(itemPosition).toString()+" from the view");
        //;
        //addItem(getItem(itemPosition));
        //holder.titleView.setText(itemList.get(itemPosition).getTitle());
        holder.titleView.setText(getItem(itemPosition).getTitle());

        myLayout.setTag(holder);

        Log.i(logger, "the item " +getItem(itemPosition).getTitle()+" has been display in the list view");

        return myLayout;
    }
}
