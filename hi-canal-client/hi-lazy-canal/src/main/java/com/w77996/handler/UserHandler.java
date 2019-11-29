package com.w77996.handler;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.w77996.canal.AbstractCanalEntryHandler;
import com.w77996.canal.CanalMessageHandler;

public class UserHandler extends AbstractCanalEntryHandler {


    @Override
    public String getTableName() {
        return null;
    }

    @Override
    public String getPrimaryKey() {
        return null;
    }

    @Override
    protected void insertEntry(String tableName, String id, CanalEntry.RowData row) {

    }

    @Override
    protected void updateEntry(String tableName, String id, CanalEntry.RowData row) {

    }

    @Override
    protected void deleteEntry(String tableName, String id, CanalEntry.RowData row) {

    }
}
