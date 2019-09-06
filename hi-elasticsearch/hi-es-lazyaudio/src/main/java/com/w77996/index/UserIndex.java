package com.w77996.index;

import com.w77996.es.AbstractIndex;
import com.w77996.scan.LongScanCursor;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserIndex extends AbstractIndex<LongScanCursor, List<Map<String,Object>>> {


    @Override
    protected XContentBuilder mapping() throws Exception {
        XContentBuilder mapping = XContentFactory.jsonBuilder()
                .startObject()
                .startObject("_all").field("enabled", false).endObject()
                .startObject("_source").field("enabled", false).endObject()
                .startObject("properties")
                .startObject("id").field("type", "long").endObject()
                .startObject("userName").field("type", "text").field("analyzer", "standard").endObject()
                .startObject("sex").field("type", "integer").endObject()
                .startObject("createTime").field("type", "long").endObject()
                //.startObject(FieldNameConst.PINYIN_SHORT_NAME).field("type", "text").field("analyzer", "autocomplete").field("search_analyzer", "standard").endObject()
                //.startObject(FieldNameConst.BOOK_NAME + FieldNameConst.NAME_TERM_SUFFIX).field("type", "keyword").endObject()
                //.startObject(FieldNameConst.BOOK_NAME + FieldNameConst.PINYIN_NAME_SUFFIX).field("type", "keyword").endObject()
                //.startObject(FieldNameConst.BOOK_NAME + FieldNameConst.PINYIN_SHORT_NAME_SUFFIX).field("type", "keyword").endObject()

//                .startObject(FieldNameConst.FIRST_ONLINE_TIME).field("type", "date").endObject()
//                .startObject(FieldNameConst.TYPE_ID).field("type", "integer").endObject()
//                .startObject(FieldNameConst.PLAYS).field("type", "integer").endObject()
//                .startObject(FieldNameConst.COLLECTION_COUNT).field("type", "integer").endObject()
//                .startObject(FieldNameConst.COMMENT_COUNT).field("type", "integer").endObject()
//                .startObject(FieldNameConst.LABELS).field("type", "integer").endObject()
//                .startObject(FieldNameConst.VIP_LIBRARY).field("type", "integer").endObject()
//                .startObject(FieldNameConst.SORT).field("type", "integer").endObject()
                .startObject("status").field("type", "integer").endObject()

//                .startObject(FieldNameConst.WEEKPLAYS).field("type", "integer").endObject()
//                .startObject(FieldNameConst.PRODUCT_TYPE).field("type", "integer").endObject()
//                .startObject(FieldNameConst.ANNOUNCERS).field("type", "text").field("analyzer", "standard").endObject()
//                .startObject(FieldNameConst.LAST_MODIFY).field("type", "date").endObject()
//
//                .startObject(FieldNameConst.IS_RECOMMENDED).field("type", "integer").endObject()
//
//                .startObject(FieldNameConst.CENSOR_FLAG).field("type", "integer").endObject()
//                .startObject(FieldNameConst.CONTENT_LEVEL).field("type", "integer").endObject()

                .endObject()
                .endObject();
        return mapping;
    }

    /**
     * 索引名称
     *
     * @return
     */
    @Override
    protected String getIndexIdName() {
        return "user_v1";
    }

    @Override
    protected List<Map<String, Object>> queryData(String tableName, List<String> ids) {
        return null;
    }

    @Override
    protected LongScanCursor queryData(String tableName, LongScanCursor scanCursor, int size) {
        return null;
    }

    @Override
    protected List<Map<String, Object>> transformData(String tableName, List<Map<String, Object>> dataList) throws Exception {
        return null;
    }

    @Override
    protected String getTableName(String indexId) {
        return "t_user";
    }

    @Override
    protected String type(String tableName, String id) {
        return "b";
    }

    @Override
    protected List<String> getAllTableName() {
        List<String> tableList=new ArrayList<String>();
        tableList.add("t_user");
        return tableList;
    }

    @Override
    public List<String> getTableNames() {
        return null;
    }

    @Override
    public String getIndexName() {
        return null;
    }

    @Override
    public boolean doRebuildTask() {
        return false;
    }
}
