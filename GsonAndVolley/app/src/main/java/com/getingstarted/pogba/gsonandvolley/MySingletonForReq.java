package com.getingstarted.pogba.gsonandvolley;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class  MySingletonForReq {

    private static MySingletonForReq mInstance;
    private RequestQueue mRequestQueue;
    private static Context mContext;

    public MySingletonForReq(Context ctxt) {
        mContext = ctxt;
        mRequestQueue = getRequestQueue();

    }

    private RequestQueue getRequestQueue() {
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public static synchronized MySingletonForReq getIstance(Context context){
        if (mInstance == null){
            mInstance = new MySingletonForReq(context);
        }
        return mInstance;
    }

    public <T> void addToRequestQueue(Request request){
        getRequestQueue().add(request);
    }
}
