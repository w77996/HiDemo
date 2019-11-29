package com.w77996.canal;

import com.alibaba.otter.canal.protocol.CanalEntry;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author w77996
 */
public abstract class AbstractCanalEntryHandler {

    /**
     * 是否是多表
     * @return
     */
    public boolean isMultiTable(){
        return false;
    }

    /**
     * 表名或表名前缀
     * @return
     */
    public abstract String getTableName();

    /**
     * 表的主键
     * @return
     */
    public abstract String getPrimaryKey();

    /**
     * 获取修改前的主键
     * @param row
     * @return
     */
    public String getBeforeId(CanalEntry.RowData row) {
        String primaryKey = getPrimaryKey();
        return StringUtils.isBlank(primaryKey) ? null : getBeforeColumnValue(row, primaryKey);
    }

    /**
     * 获取修改后的主键
     * @param row
     * @return
     */
    public String getAfterId(CanalEntry.RowData row) {
        String primaryKey = getPrimaryKey();
        return StringUtils.isBlank(primaryKey) ? null : getAfterColumnValue(row, primaryKey);
    }

    protected String getBeforeColumnValue(CanalEntry.RowData row, String columnName) {
        List<CanalEntry.Column> columnList = row.getBeforeColumnsList();
        for (CanalEntry.Column column : columnList) {
            if(column.getName().equalsIgnoreCase(columnName)) {
                return column.getValue();
            }
        }
        return null;
    }

    protected String getAfterColumnValue(CanalEntry.RowData row, String columnName) {
        List<CanalEntry.Column> columnList = row.getAfterColumnsList();
        for (CanalEntry.Column column : columnList) {
            if(column.getName().equalsIgnoreCase(columnName)) {
                return column.getValue();
            }
        }
        return null;
    }

    /**
     * 插入实体
     * @param tableName 表名  （全名称：库名.表名）
     * @param id 实体ID
     * @param row 数据行信息
     */
    protected abstract void insertEntry(String tableName, String id, CanalEntry.RowData row);

    /**
     * 修改实体
     * @param tableName 表名  （全名称：库名.表名）
     * @param id 实体ID
     * @param row 数据行信息
     */
    protected abstract void updateEntry(String tableName, String id, CanalEntry.RowData row);

    /**
     * 删除实体
     * @param tableName 表名  （全名称：库名.表名）
     * @param id 实体ID
     * @param row 数据行信息
     */
    protected abstract void deleteEntry(String tableName, String id, CanalEntry.RowData row);
}
