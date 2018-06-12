package com.a06.uebung.maus.getingstarted.pogba.myue06app.dataaccessors;

import android.app.Activity;

/**
 * allows access to the activity object for its subclasses
 *
 * @author Mistra Forest
 *
 */
public abstract class AbstractActivityItemAccessor {

    private Activity activity;

    protected Activity getActivity() {
        return this.activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
