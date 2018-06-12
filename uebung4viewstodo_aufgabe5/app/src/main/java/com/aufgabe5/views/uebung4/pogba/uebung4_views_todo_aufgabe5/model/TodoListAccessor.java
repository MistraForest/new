package com.aufgabe5.views.uebung4.pogba.uebung4_views_todo_aufgabe5.model;

import android.widget.ListAdapter;

import javax.ws.rs.*;


public interface TodoListAccessor {
    /**
     * add an item to the list
     *
     * @param item
     */
     void addItem(Todo_Simple item);

    /**
     * get an adapter for the list
     * @return TodoListAccessor
     */
     ListAdapter getAdapter();

    /**
     * update an existing item
     *
     * @param item
     */
    @PUT
     void updateItem(Todo_Simple item);

    /**
     * delete an item
     *
     * @param item
     */
     void deleteItem(Todo_Simple item);

    /**
     * determine the item selected by the user given either the position in the
     * list or the item id
     *
     * @param itemPosition
     * @param itemId
     * @return Todo_Simple
     */
     Todo_Simple getSelectedItem(int itemPosition, long itemId);

    /**
     * end processing the list of items
     */
     void close();
}
