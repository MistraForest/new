package com.aufgabe5.views.uebung4.pogba.uebung4_views_todo_aufgabe5.util;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.aufgabe5.views.uebung4.pogba.uebung4_views_todo_aufgabe5.R;
import com.aufgabe5.views.uebung4.pogba.uebung4_views_todo_aufgabe5.model.Todo_Simple;

import java.util.*;

public class DataListView {
    public static ArrayAdapter<Todo_Simple> createItemFromAdapter(final Activity activity, final List<Todo_Simple> itemList) {
        return new ArrayAdapter<Todo_Simple>(activity, R.layout.custom_layout, itemList){

            private int countImageClick = 0;
            private  Map<Integer, Boolean> checkStates = new HashMap<>();


            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {



                View view = convertView == null ? activity.getLayoutInflater().inflate(
                        R.layout.custom_layout,parent, false
                ): convertView;

                final Todo_Simple todo = getItem(position);

                final TextView textView = view.findViewById(R.id.todo_name);
                final ImageView imageView = view.findViewById(R.id.todo_image);
                final CheckBox checkBox = view.findViewById(R.id.check_it);

                textView.setText(todo.getName());
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(),"Item = "+todo.getName()+" | Done:"+todo.getIsDone()+
                                " ,Favourite:"+todo.getIsFavourite()+
                                " ,Date:"+ todo.getDueDate(),Toast.LENGTH_SHORT).show();
                    }
                });

                imageView.setImageResource(R.mipmap.star_grey);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        int pos = (int) buttonView.getTag();
                        checkStates.put(pos, isChecked);
                        todo.setIsDone(isChecked);
                    }
                });

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        countImageClick++;
                        if (countImageClick % 2 == 1){
                            todo.setIsFavourite(true);
                            imageView.setImageResource(R.mipmap.star_yellow);
                        }
                        else{
                            todo.setIsFavourite(false);
                            imageView.setImageResource(R.mipmap.star_grey);
                        }
                    }
                });

                checkBox.setTag(position);
                checkBox.setChecked(checkStates.containsKey(position) ? checkStates.get(position) : false);
                sortTodoListByDate();

                return view;
            }
            private void sortTodoListByDate() {
                Collections.sort(itemList, new Comparator<Todo_Simple>() {
                    @Override
                    public int compare(Todo_Simple o1, Todo_Simple o2) {
                        return Long.compare(o1.getDueDate(), o2.getDueDate());
                    }
                });
            }
        };
    }
}
