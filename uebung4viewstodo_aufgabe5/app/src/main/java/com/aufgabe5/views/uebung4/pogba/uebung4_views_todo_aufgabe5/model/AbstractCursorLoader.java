package com.aufgabe5.views.uebung4.pogba.uebung4_views_todo_aufgabe5.model;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;

public abstract class AbstractCursorLoader
        extends AsyncTaskLoader<Cursor> {

    private Cursor mCursor;

    // template method pattern
    protected abstract Cursor loadCursor();

    public AbstractCursorLoader(Context context) {
        super(context);
    }

    @Override
    public Cursor loadInBackground() {
        final Cursor cursor = loadCursor();
        if (cursor != null){
            cursor.getCount();
        }
        return cursor;
    }

    @Override
    public void onCanceled(Cursor cursor) {
        if (cursor != null && !cursor.isClosed()){
            cursor.close();
        }
    }

    @Override
    public void deliverResult(Cursor data) {
        Cursor oldCursor = mCursor;
        mCursor = data;
        if (isStarted())
        {
            super.deliverResult(data);
        }

        if (oldCursor != null && oldCursor != data && !oldCursor.isClosed())
        {
            oldCursor.close();
        }
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
    }

    @Override
    protected void onStartLoading() {
        if (mCursor != null){
            deliverResult(mCursor);
        }
        if (takeContentChanged() || mCursor == null){
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
        if (mCursor != null && !mCursor.isClosed()){
            mCursor.close();
        }
    }

}
