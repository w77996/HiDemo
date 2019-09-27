package com.w77996.mahout.controller;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.GenericBooleanPrefDataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/")
public class MahoutController {

    @GetMapping
    public List<RecommendedItem> mahout(@RequestParam String userId,@RequestParam int type){
//        ClassPathResource classPathResource = new ClassPathResource("csv/book_cvs_file.csv");
        //数据模型
        DataModel model = null;
        List<RecommendedItem> list = Lists.newArrayList();
        File cvsFile = null;
        try {
            long startTime = System.currentTimeMillis();
//            File bookCsvFile = ResourceUtils.getFile("classpath:csv/book_cvs_file.csv");
            if (type == 1) {
                cvsFile = new File("E:\\code\\python\\to_csv\\book_cvs_file.csv");
            }else if(type == 2){
                cvsFile = new File("E:\\code\\python\\to_csv\\read_cvs_file.csv");
            }else if(type == 3){
                cvsFile = new File("E:\\code\\python\\to_csv\\album_cvs_file.csv");
            }
//            File bookCsvFile = classPathResource.getFile();
//            log.info(bookCsvFile.getAbsolutePath());
            model = new GenericBooleanPrefDataModel(
                    GenericBooleanPrefDataModel
                            .toDataMap(new FileDataModel(cvsFile)));
//            model = new FileDataModel(bookCsvFile);
            //用户相识度算法
            ItemSimilarity item=new LogLikelihoodSimilarity(model);
            //物品推荐算法
            Recommender r=new GenericItemBasedRecommender(model,item);
            LongPrimitiveIterator iter =model.getUserIDs();
//            while(iter.hasNext()){
//                long uid=iter.nextLong();
//                List<RecommendedItem> list = r.recommend(uid, 1);
//                System.out.printf("uid:%s",uid);
//                for (RecommendedItem ritem : list) {
//                    System.out.printf("(%s,%f)", ritem.getItemID(), ritem.getValue());
//                }
//                System.out.println();
//            }
             list = r.recommend(Long.parseLong(userId), 10);
            list.forEach(System.out::println);
            long endTime = System.currentTimeMillis();
            log.info((endTime-startTime) +"");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TasteException e) {
            e.printStackTrace();
        }

        return list;
    }
    @GetMapping("/user")
    public List<RecommendedItem> mahoutUser(@RequestParam String userId,@RequestParam int type){
        ClassPathResource classPathResource = new ClassPathResource("csv/book_cvs_file.csv");
        //数据模型
        DataModel model = null;
        List<RecommendedItem> list = Lists.newArrayList();
        File cvsFile = null;
        try {
            long startTime = System.currentTimeMillis();
//            File bookCsvFile = ResourceUtils.getFile("classpath:csv/book_cvs_file.csv");
            if (type == 1) {
                cvsFile = new File("E:\\code\\python\\to_csv\\book_cvs_file.csv");
            }else if(type == 2){
                cvsFile = new File("E:\\code\\python\\to_csv\\read_cvs_file.csv");
            }else if(type == 3){
                cvsFile = new File("E:\\code\\python\\to_csv\\album_cvs_file.csv");
            }
//            File bookCsvFile = classPathResource.getFile();
//            log.info(bookCsvFile.getAbsolutePath());
            model = new FileDataModel(cvsFile);
//            model = new FileDataModel(bookCsvFile);
            //用户相识度算法
            UserSimilarity userSimilarity=new LogLikelihoodSimilarity(model);
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(20,userSimilarity, model);
            //物品推荐算法
            Recommender r=new GenericUserBasedRecommender(model, neighborhood, userSimilarity);
            LongPrimitiveIterator iter =model.getUserIDs();

            list = r.recommend(Long.parseLong(userId), 10);
            list.forEach(System.out::println);
            long endTime = System.currentTimeMillis();
            log.info((endTime-startTime) +"");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TasteException e) {
            e.printStackTrace();
        }
        return list;
    }
}
