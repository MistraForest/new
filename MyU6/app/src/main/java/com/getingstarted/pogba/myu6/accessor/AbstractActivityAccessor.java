package com.getingstarted.pogba.myu6.accessor;

import android.app.Activity;

public abstract class AbstractActivityAccessor {

    private Activity activity;

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
