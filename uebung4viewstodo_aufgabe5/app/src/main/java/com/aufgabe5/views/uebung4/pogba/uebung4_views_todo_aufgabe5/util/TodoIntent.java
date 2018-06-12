package com.aufgabe5.views.uebung4.pogba.uebung4_views_todo_aufgabe5.util;

import android.content.Intent;
import com.aufgabe5.views.uebung4.pogba.uebung4_views_todo_aufgabe5.model.TodoDetailAccessor;
import com.aufgabe5.views.uebung4.pogba.uebung4_views_todo_aufgabe5.TodoListActivity;
import com.aufgabe5.views.uebung4.pogba.uebung4_views_todo_aufgabe5.model.Todo_Simple;

public class TodoIntent extends ActivityAbstractAccessor implements TodoDetailAccessor {

    private Todo_Simple todoData;


    @Override
    public Todo_Simple readItem() {
        if (todoData == null){
            this.todoData = (Todo_Simple) getActivity().getIntent()
                    .getSerializableExtra(TodoListActivity.ITEM_OBJECT);
        }
        return todoData;
    }

    @Override
    public void writeItem() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(TodoListActivity.ITEM_OBJECT, this.todoData);

        getActivity().setResult(TodoListActivity.RESPONSE_ITEM_EDITED, returnIntent);
    }

    @Override
    public boolean hasItem() {
        return readItem() != null;
    }

    @Override
    public void createItem() {

    }

    @Override
    public void deleteItem() {
        // and return to the calling activity
        Intent returnIntent = new Intent();

        // set the item
        returnIntent.putExtra(TodoListActivity.ITEM_OBJECT, this.todoData);

        // set the result code
        getActivity().setResult(TodoListActivity.RESPONSE_ITEM_DELETED,
                returnIntent);
    }
}
