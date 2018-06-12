package com.aufgabe5.views.uebung4.pogba.uebung4_views_todo_aufgabe5.util;

import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import com.aufgabe5.views.uebung4.pogba.uebung4_views_todo_aufgabe5.model.TodoListAccessor;
import com.aufgabe5.views.uebung4.pogba.uebung4_views_todo_aufgabe5.model.Todo_Simple;

import java.util.ArrayList;
import java.util.List;

public class ListAccessorIMPL extends ActivityAbstractAccessor implements TodoListAccessor {

    List<Todo_Simple> itemList = new ArrayList<>();

    ArrayAdapter<Todo_Simple> arrayAdapter ;
    @Override
    public void addItem(Todo_Simple item) {
        this.arrayAdapter.add(item);
    }

    @Override
    public ListAdapter getAdapter() {

        this.arrayAdapter = DataListView.createItemFromAdapter(getActivity(), itemList);
        return this.arrayAdapter;
    }

    @Override
    public void updateItem(Todo_Simple item) {
        lookupItem(item).updateFrom(item);
    }

    @Override
    public void deleteItem(Todo_Simple item) {

    }

    @Override
    public Todo_Simple getSelectedItem(int itemPosition, long itemId) {
        return null;
    }

    @Override
    public void close() {

    }

    private Todo_Simple lookupItem(Todo_Simple item) {
        for (Todo_Simple current : this.itemList) {
            if (current.getId() == item.getId()) {
                return current;
            }
        }
        return null;
    }
}
