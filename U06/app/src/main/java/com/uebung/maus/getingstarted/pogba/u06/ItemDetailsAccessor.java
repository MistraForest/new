package com.uebung.maus.getingstarted.pogba.u06;

public interface ItemDetailsAccessor {

    TodoData readItem();

    void writeItem();

    boolean hasItem();

    void createItem();

    void deleteItem();
}
