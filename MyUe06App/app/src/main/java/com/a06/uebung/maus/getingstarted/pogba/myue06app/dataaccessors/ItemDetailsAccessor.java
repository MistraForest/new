package com.a06.uebung.maus.getingstarted.pogba.myue06app.dataaccessors;

import com.a06.uebung.maus.getingstarted.pogba.myue06app.model.TodoItem;


/**
 * Interface for handling a single data item, used by ItemDetailsActivity
 *
 * @author Mistra Forest
 *
 */
public interface ItemDetailsAccessor {

    TodoItem readItem();

    void writeItem();

    boolean hasItem();

    void createItem();

    void deleteItem();

}
