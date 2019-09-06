package com.w77996.scan;

import java.util.List;
import java.util.Map;

public class LongScanCursor extends ListScanCursor{

    private Long lastId;

    public LongScanCursor(List<Map<String, Object>> dataList, Long lastId, boolean hasNext) {
        super(dataList, hasNext);
        this.lastId = lastId;
    }

    public Long getLastId() {
        return lastId;
    }
}