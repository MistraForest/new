package com.aufgabe5.views.uebung4.pogba.uebung4_views_todo_aufgabe5.util;

import android.app.Activity;

public abstract class ActivityAbstractAccessor {

    private Activity activity;

    protected Activity getActivity() {
        return this.activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
