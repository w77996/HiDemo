package com.w77996.es;

import java.util.List;

/**
 * 索引接口
 */
public interface IIndex extends IRebuild {

    /**
     * 获取表名  (全名，需带库名, 多表时使用的是表名的前缀)
     * @return
     */
    List<String> getTableNames();

    /**
     * 获取索引名  （在线索引逻辑名称）
     * @return
     */
    String getIndexName();

//    /**
//     * 批量索引数据
//     * @param indexName
//     * @param messageList
//     * @throws IndexException
//     */
//    void index(String indexName, List<KafkaMessage> messageList) throws IndexException;
}
