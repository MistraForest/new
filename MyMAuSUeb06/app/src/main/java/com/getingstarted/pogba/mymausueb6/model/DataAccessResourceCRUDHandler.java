package com.getingstarted.pogba.mymausueb6.model;

import java.util.ArrayList;

public interface DataAccessResourceCRUDHandler {

    DataItem readDataItem(Long id);

    ArrayList<DataItem> readAllDataItem();

    void deleteDataItem(Long id);

    DataItem createDataItem(DataItem item);

    DataItem updateDataItem(DataItem item);
}
