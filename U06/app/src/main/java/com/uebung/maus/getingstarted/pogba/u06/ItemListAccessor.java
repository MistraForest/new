package com.uebung.maus.getingstarted.pogba.u06;

import android.widget.ListAdapter;

import java.util.List;

public interface ItemListAccessor {


        List<TodoData> getAllItems();

        /**
         * add an item to the list
         *
         * @param item
         */
        void addItem(TodoData item);

        /**
         * get an adapter for the list
         * @return
         */
        ListAdapter getAdapter();


        /**
         * update an existing item
         *
         * @param item
         */
        void updateItem(TodoData item);

        /**
         * delete an item
         *
         * @param item
         */
        void deleteItem(TodoData item);

        /**
         * determine the item selected by the user given either the position in the
         * list or the item id
         *
         * @param itemPosition
         * //@param itemId
         * @return
         */
        TodoData getSelectedItem(int itemPosition);
}
