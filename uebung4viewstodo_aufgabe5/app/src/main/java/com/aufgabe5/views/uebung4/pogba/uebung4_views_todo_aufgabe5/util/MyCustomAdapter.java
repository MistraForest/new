package com.aufgabe5.views.uebung4.pogba.uebung4_views_todo_aufgabe5.util;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.aufgabe5.views.uebung4.pogba.uebung4_views_todo_aufgabe5.R;
import com.aufgabe5.views.uebung4.pogba.uebung4_views_todo_aufgabe5.model.Todo_Simple;

import java.util.*;

public class MyCustomAdapter {

    private static Map<Integer, Boolean> checkStates = new HashMap<>();
    private static String logger = MyCustomAdapter.class.getName();


    private static class ViewHolder {
        ImageView imageView;
        //Switch isDone;
        CheckBox checkBox;
        TextView textView;
    }

    public static ArrayAdapter<Todo_Simple> createDataFromAdapter(final Context context, final int resource, final List<Todo_Simple> todos){

        return new ArrayAdapter<Todo_Simple>(context, resource, todos){

            private int countImageClick = 0;
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {

                final Todo_Simple todo = todos.get(position);
                Log.i(logger, "Todo: "+ todo);

                final ViewHolder holder;
                Log.i(logger, "getView() "+ convertView+ " on Position " +position+ " TODO: "+todo);
                View view = convertView;

                if (view == null){
                    holder = new ViewHolder();
                    LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view = layoutInflater.inflate(resource, parent, false);

                    holder.imageView = view.findViewById(R.id.todo_image);
                    holder.textView = view.findViewById(R.id.todo_name);
                    holder.checkBox = view.findViewById(R.id.check_it);
                    //holder.isDone = view.findViewById(R.id.status_id);

                   holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            int pos = (int) buttonView.getTag();
                            checkStates.put(pos, isChecked);
                            sortTodoListByStatus();
                            if (isChecked){
                                todo.setIsDone(isChecked);
                            }else {
                                todo.setIsDone(!isChecked);
                            }

                        }

                    });

                    view.setTag(holder);
                    Log.i(logger, "getView:View ist "+view);
                } else{
                    holder = (ViewHolder)view.getTag();

                }

                holder.imageView.setImageResource(R.mipmap.star_grey);

                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                     //   int pos = (int) v.getTag();

                        countImageClick++;
                        if (countImageClick % 2 == 1){
                            todo.setIsFavourite(true);
                            holder.imageView.setImageResource(R.mipmap.star_yellow);
                           // checkStates.put(pos, v.isActivated());
                        }
                        else{
                            todo.setIsFavourite(false);
                            holder.imageView.setImageResource(R.mipmap.star_grey);
                            //checkStates.put(pos, v.isActivated());
                        }
                    }
                });


                //holder.checkBox.setActivated(true);
                holder.textView.setText(todo.getName());

                holder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(),"Item = "+todo.getName()+" | Done:"+todo.getIsDone()+
                                                    " ,Favourite:"+todo.getIsFavourite()+
                                                    " ,Date:"+ todo.getDueDate(),Toast.LENGTH_SHORT).show();
                    }
                });


                holder.checkBox.setTag(position);
                holder.checkBox.setChecked(checkStates.containsKey(position) ? checkStates.get(position) : false);


                return view;
            }

            private void sortTodoListByStatus() {
                Collections.sort(todos, new Comparator<Todo_Simple>() {
                    @Override
                    public int compare(Todo_Simple o1, Todo_Simple o2) {
                        return Boolean.compare(o1.getIsDone(), o2.getIsDone());
                    }
                });
            }
            private void sortTodoListByDate() {
                Collections.sort(todos, new Comparator<Todo_Simple>() {
                    @Override
                    public int compare(Todo_Simple o1, Todo_Simple o2) {
                        return Long.compare(o1.getDueDate(), o2.getDueDate());
                    }
                });
            }
        };
    }
    private static void sortingCheckedState() {
        List list = new LinkedList(checkStates.entrySet());

        Collections.sort(list, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return ((Comparable)((Map.Entry)(o1)).getValue())
                        .compareTo(((Map.Entry)(o2)).getValue());
            }
        });

        //HashMap sortedHashMap = new LinkedHashMap();
                for (Iterator iterator = list.iterator(); iterator.hasNext();){
                    Map.Entry entry = (Map.Entry) iterator.next();
                    checkStates.put((Integer) entry.getKey(), (boolean)entry.getValue());
                }
    }


}
