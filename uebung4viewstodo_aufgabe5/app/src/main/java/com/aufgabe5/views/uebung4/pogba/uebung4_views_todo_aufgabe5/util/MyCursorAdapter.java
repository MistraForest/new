package com.aufgabe5.views.uebung4.pogba.uebung4_views_todo_aufgabe5.util;

import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import com.aufgabe5.views.uebung4.pogba.uebung4_views_todo_aufgabe5.*;
import com.aufgabe5.views.uebung4.pogba.uebung4_views_todo_aufgabe5.model.CursorLoaderIMPL;
import com.aufgabe5.views.uebung4.pogba.uebung4_views_todo_aufgabe5.model.TodoListAccessor;
import com.aufgabe5.views.uebung4.pogba.uebung4_views_todo_aufgabe5.model.Todo_Simple;

public class MyCursorAdapter extends ActivityAbstractAccessor implements TodoListAccessor {

    protected static final String logger = MyCursorAdapter.class
            .getName();

    // should usually reside in the activity#
    // we have it here for abstraction reasons, since the activity should be able to swap different IDataItemListAccessor
    // implementations
    private static final int LOADER_ID = 1;

    private SimpleCursorAdapter adapter;

    private LoaderManager.LoaderCallbacks<Cursor> mCallbacks;

    private DBHandler dbHandler;

    //CursorLoaderIMPL myLoader = new CursorLoaderIMPL(getActivity(), dbHandler);

    private class MyLoaderCallbacks implements LoaderManager.LoaderCallbacks<Cursor>
    {

        @Override
        public Loader<Cursor> onCreateLoader(int aId, Bundle aArgs) {
        return new CursorLoaderIMPL(getActivity(), dbHandler);
    }

        @Override
        public void onLoadFinished(Loader<Cursor> aLoader, Cursor aCursor) {
        if(aLoader.getId() == LOADER_ID)
        {
            adapter.swapCursor(aCursor);
        }
    }

        @Override
        public void onLoaderReset(Loader<Cursor> aArg0) {
        adapter.swapCursor(null);
    }

    }


    @Override
    public void addItem(Todo_Simple item) {

        dbHandler.addTodoItemToDb(item);
        getActivity().getLoaderManager().restartLoader(LOADER_ID,null,mCallbacks);

    }

    @Override
    public  ListAdapter getAdapter() {

        dbHandler = new DBHandler(getActivity());
        dbHandler.initDatabase();

        LoaderManager loaderManager = getActivity().getLoaderManager();
        mCallbacks = new MyLoaderCallbacks();
        loaderManager.initLoader(LOADER_ID, null, mCallbacks);

        /*
         * create a cursor adapter that maps the "name" column in the db to the
         * itemName element in the view
         *
         * (i.e. using this adapter there is no need to create DataItem objects
         * for all items that are contained in the db)
         */
        this.adapter = new SimpleCursorAdapter(getActivity(), R.layout.custom_layout,
                null,
                new String[]{dbHandler.COL_NAME},
                new int[]{R.id.todo_name}, 0);

        return this.adapter;
    }

    @Override
    public void updateItem(Todo_Simple item) {
        dbHandler.updateItemInDb(item);
        getActivity().getLoaderManager().restartLoader(LOADER_ID,null,mCallbacks);
    }

    @Override
    public void deleteItem(Todo_Simple item) {
        dbHandler.removeItemFromDb(item);
        getActivity().getLoaderManager().restartLoader(LOADER_ID,null,mCallbacks);
    }

    @Override
    public Todo_Simple getSelectedItem(int itemPosition, long itemId) {
        return dbHandler.createTodoFromCursor((Cursor)this.adapter.getItem(itemPosition));
    }

    @Override
    public void close() {
        dbHandler.close();
    }
}
