//package com.w77996.factory;
//
//import org.elasticsearch.client.Client;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.List;
//
//public class ESGeneralFactory {
//
//
//    public static Client getClient(String indexName){
//        if(indexName.startsWith(IndexNameConst.ORDER_INDEX_PREFIX)) {
//            return ESStatFactory.getInstance().getClient();
//        }
//        return ESFactory.getInstance().getClient();
//    }
//
//    public static List<String> getIndexByAliase(String indexAliase) {
//        if(indexAliase.startsWith(IndexNameConst.ORDER_INDEX_PREFIX)) {
//            return ESStatFactory.getInstance().getIndexByAliase(indexAliase);
//        }
//        return ESFactory.getInstance().getIndexByAliase(indexAliase);
//    }
//
//    public static boolean optimize(String indexName, Integer maxNumSegments) {
//        if(indexName.startsWith(IndexNameConst.ORDER_INDEX_PREFIX)) {
//            return ESStatFactory.getInstance().optimize(indexName, maxNumSegments);
//        }
//        return ESFactory.getInstance().optimize(indexName, maxNumSegments);
//    }
//
//    public static boolean deleteIndex(String indexName) {
//        if(indexName.startsWith(IndexNameConst.ORDER_INDEX_PREFIX)) {
//            return ESStatFactory.getInstance().deleteIndex(indexName);
//        }
//        return ESFactory.getInstance().deleteIndex(indexName);
//    }
//
//    public static boolean setReplicas(String indexName, int replicas) {
//        if(indexName.startsWith(IndexNameConst.ORDER_INDEX_PREFIX)) {
//            return ESStatFactory.getInstance().setReplicas(indexName, replicas);
//        }
//        return ESFactory.getInstance().setReplicas(indexName, replicas);
//    }
//}
