package com.w77996.scan;

import java.util.List;
import java.util.Map;

abstract class ListScanCursor implements ScanCursor<List<Map<String, Object>>> {

    private boolean hasNext;

    private List<Map<String, Object>> dataList;

    public ListScanCursor(List<Map<String, Object>> dataList, boolean hasNext){
        this.dataList = dataList;
        this.hasNext = hasNext;
    }

    @Override
    public List<Map<String, Object>> getData() {
        return dataList;
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public int size(){
        return this.dataList.size();
    }

}