package com.aufgabe5.views.uebung4.pogba.uebung4_views_todo_aufgabe5.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.util.List;

public class MyBeanArrayAdapter<Todo_Simple> extends ArrayAdapter<Todo_Simple> {

    protected static String logger = MyBeanArrayAdapter.class.getSimpleName();

    private String[] from;

    private int[] to;

    private int layout;

    private List<Todo_Simple> objects;


    public MyBeanArrayAdapter(Context context, int layout, List<Todo_Simple> objects,
                            String[] from, int[] to) {
        super(context, layout, objects);
        this.from = from;
        this.to = to;
        this.objects = objects;
        this.layout = layout;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View listItemView, @NonNull ViewGroup parent) {
        Log.i(logger,
                "getView() has been invoked for item: " + objects.get(position)
                        + ", listItemView is: " + listItemView);

        View itemView = ((LayoutInflater) super.getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE)).inflate(layout, null);

        // we take the current object
        Todo_Simple currentObject = objects.get(position);

        for (int i = 0; i < from.length; i++) {

            try {

                // we calculate the name of the getter
                Method method = currentObject.getClass().getDeclaredMethod(
                        "get" + from[i].substring(0, 1).toUpperCase()
                                + from[i].substring(1)
                );

                Object fieldValue = method.invoke(currentObject);

                if (fieldValue != null) {
                    View fieldValueView = itemView.findViewById(to[i]);
                    if (fieldValueView instanceof TextView) {
                        ((TextView) fieldValueView).setText(String
                                .valueOf(fieldValue));
                    } else {
                        Log.e(logger,
                                "cannot handle display of value of field "
                                        + from[i]
                                        + ". It is not an instanceof TextView, but: "
                                        + fieldValueView);
                    }
                } else {
                    Log.i(logger, "will not display null value of field "
                            + from[i]);
                }

            }catch (Exception e){
                Log.e(logger, "got exception trying to process field "
                        + from[i] + ": " + e, e);
            }
        }

        return itemView;
    }
}
