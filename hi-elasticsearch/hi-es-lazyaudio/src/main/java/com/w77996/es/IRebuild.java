package com.w77996.es;

public interface IRebuild {

    /**
     * 是否执行重建任务
     * @return
     */
    boolean doRebuildTask();

    /**
     * 创建索引
     * @param indexName
     * @return
     */
    boolean createIndex(String indexName);

    /**
     * 索引全量数据
     *	      当重新索引数据失败时，抛出异常
     * @param indexName
     * @throws Exception
     */
    void rebuild(String indexName) throws Exception;
}
