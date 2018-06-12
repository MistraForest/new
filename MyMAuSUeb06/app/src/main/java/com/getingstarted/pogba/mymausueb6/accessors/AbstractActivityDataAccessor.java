package com.getingstarted.pogba.mymausueb6.accessors;

import android.app.Activity;



/**
 * allows access to the activity object for its subclasses
 *
 * @author Joern Kreutel
 *
 */
public abstract class AbstractActivityDataAccessor {

    private Activity activity;

    public Activity getActivity() {
        return this.activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
