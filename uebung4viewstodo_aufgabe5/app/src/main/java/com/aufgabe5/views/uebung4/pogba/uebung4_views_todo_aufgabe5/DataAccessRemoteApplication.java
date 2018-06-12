package com.aufgabe5.views.uebung4.pogba.uebung4_views_todo_aufgabe5;

import android.app.Activity;
import android.app.Application;
import android.util.Log;
import android.widget.Toast;
import com.aufgabe5.views.uebung4.pogba.uebung4_views_todo_aufgabe5.model.remote.HttpURLConnectionTodotemCRUDAccessor;

public class DataAccessRemoteApplication extends Application {

    protected static String logger = DataAccessRemoteApplication.class
            .getSimpleName();

    /**
     * the accessors that implement the different alternatives for accessing the
     * item list
     */
    //private HttpURLConnectionTodotemCRUDAccessor httpURLConnectionAccessor;

    public DataAccessRemoteApplication() {
        Log.i(logger,"Use HttpURLConnectionTodotemCRUDAccessor");
         new HttpURLConnectionTodotemCRUDAccessor(getRestBaseUrl() + "/dataitems");
    }
    public void reportError(Activity activity, String error) {
        Toast.makeText(activity, error, Toast.LENGTH_LONG).show();
    }

    private String getRestBaseUrl()
    {
        return getWebappBaseUrl() + "rest";
    }

    /**
     * get the baseUrl of the webapp used as data source and media resource provider
     * @return
     */
    private String getWebappBaseUrl() {
        return "http://10.0.2.2:8080/backend_Web_exploded/";//backend-1.0-SNAPSHOT/";
    }
}
