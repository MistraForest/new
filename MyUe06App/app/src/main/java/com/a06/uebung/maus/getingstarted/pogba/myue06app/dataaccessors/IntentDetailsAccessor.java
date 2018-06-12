package com.a06.uebung.maus.getingstarted.pogba.myue06app.dataaccessors;

import android.content.Intent;
import android.util.Log;
import com.a06.uebung.maus.getingstarted.pogba.myue06app.dataaccess.TodoList_Activity;
import com.a06.uebung.maus.getingstarted.pogba.myue06app.model.TodoItem;

public class IntentDetailsAccessor extends AbstractActivityItemAccessor implements ItemDetailsAccessor {

    protected static final String logger = IntentDetailsAccessor.class.getName();

    // the item
    private TodoItem todoItem;

    @Override
    public TodoItem readItem() {
        Log.i(logger, "readItem()...");
        if (this.todoItem == null){
            this.todoItem = (TodoItem) getActivity().getIntent().
                    getSerializableExtra(TodoList_Activity.ARG_ITEM_OBJECT);
        }
        Log.i(logger, "readItem()...Item read is: " + this.todoItem);
        return this.todoItem;
    }

    @Override
    public void writeItem() {
        Log.i(logger, "writeItem()...");
        Intent returnIntent = new Intent();

        returnIntent.putExtra(TodoList_Activity.ARG_ITEM_OBJECT, this.todoItem);

        getActivity().setResult(TodoList_Activity.RESPONSE_ITEM_EDITED, returnIntent);
        Log.i(logger, "writeItem(): Item wrote successfully");
    }

    @Override
    public boolean hasItem() {
        Log.i(logger, "hasItem()..." + (readItem() == null));
        return readItem() != null;
    }

    @Override
    public void createItem() {
        Log.i(logger, "createItem()...");
        this.todoItem = new TodoItem(-1L,"MAuS", "Wahlfach", false, true,456373838L);
    }

    @Override
    public void deleteItem() {
        Log.i(logger, "deleteItem()...");
        Intent returnIntent = new Intent();
        returnIntent.putExtra(TodoList_Activity.ARG_ITEM_OBJECT, this.todoItem);

        getActivity().setResult(TodoList_Activity.RESPONSE_ITEM_DELETED, returnIntent);
    }
}
