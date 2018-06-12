package com.getingstarted.pogba.mymausueb6.accessors;


import com.getingstarted.pogba.mymausueb6.model.DataItem;

/**
 * Interface for dealing with a list of data items, used by ItemListActivity
 *
 * @author Joern Kreutel
 *
 */
public interface DataItemAccessor {

    DataItem readItem();

    void writeItem();

    boolean hasItem();

    void createItem();

    void deleteItem();
}
