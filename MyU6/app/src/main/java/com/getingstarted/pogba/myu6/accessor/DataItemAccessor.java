package com.getingstarted.pogba.myu6.accessor;

import com.getingstarted.pogba.myu6.model.DataItem;

public interface DataItemAccessor {

    DataItem readItem();

    void writeItem();

    boolean hasItem();

    void createItem();

    void deleteItem();
}
