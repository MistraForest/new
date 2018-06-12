package com.aufgabe5.views.uebung4.pogba.uebung4_views_todo_aufgabe5.model;

import android.content.Context;
import android.database.Cursor;
import com.aufgabe5.views.uebung4.pogba.uebung4_views_todo_aufgabe5.util.DBHandler;

public class CursorLoaderIMPL extends AbstractCursorLoader {

    private DBHandler cursorFactory ;

    public CursorLoaderIMPL(Context context, DBHandler cursorFactory) {
        super(context);
        this.cursorFactory = cursorFactory;
    }

    @Override
    protected Cursor loadCursor() {
        return cursorFactory.getCursor();
    }



}
