package com.getingstarted.pogba.mymausueb6.dataaccess;

import android.app.Activity;
import android.app.Application;
import android.widget.Toast;
import com.getingstarted.pogba.mymausueb6.model.DataAccessResourceCRUDHandler;
import com.getingstarted.pogba.mymausueb6.model.DataItem;

import java.util.ArrayList;

public class DataAccessApplication extends Application implements DataAccessResourceCRUDHandler {
    @Override
    public DataItem readDataItem(Long id) {
        return null;
    }

    @Override
    public ArrayList<DataItem> readAllDataItem() {
        return null;
    }

    @Override
    public void deleteDataItem(Long id) {

    }

    @Override
    public DataItem createDataItem(DataItem item) {
        return null;
    }

    @Override
    public DataItem updateDataItem(DataItem item) {
        return null;
    }

    public void reportError(Activity activity, String error) {
        Toast.makeText(activity, error, Toast.LENGTH_LONG);
    }
}
