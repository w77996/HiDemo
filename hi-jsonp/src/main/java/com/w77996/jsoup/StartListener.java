package com.w77996.jsoup;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @ClassName StartListener
 * @Description
 * @author wuhaihui
 * @date 2020/9/23 19:24
 */
@Component
@Slf4j
public class StartListener implements CommandLineRunner {


    @Override
    public void run(String... args) throws Exception {
//        String title;
//        String
        String url = "https://yili.58.com/ershoufang/0/";
        Document document = Jsoup.connect(url).timeout(10000).get();
        Elements e = document.select("ul[class=house-list-wrap]");
        Elements houseEl = e.select("li");
        for (Element element : houseEl) {
            String dataTrace = element.attr("data-trace");
//            log.info(dataTrace);
            String replace = dataTrace.replace("\n", "").trim();
            String substring = replace.substring(1, replace.length() - 1).trim();
            String[] dataTraceElArray = substring.split(",");
            String[] esfIdArray = dataTraceElArray[0].split(":");
            String infoId = esfIdArray[1];
            log.info(infoId);
            Elements listInfoArray = element.select("div[class=list-info]");

            Element listInfo = listInfoArray.first();
            String title = listInfo.children().get(0).getAllElements().first().selectFirst("a").text();
            Elements baseInfo1 = listInfo.children().get(1).getAllElements();
            String huxing = baseInfo1.get(1).text();
            String mianji = baseInfo1.get(2).text();
            String towards = baseInfo1.get(3).text();
            String floor = baseInfo1.get(4).text();

            Elements baseInfo2 = listInfo.children().get(2).getAllElements();
            String xiaoquName = baseInfo2.get(2).text();
            String area = baseInfo2.get(3).text();
            String location = baseInfo2.get(4).text();
//            String business = baseInfo2.get(4).text();
            Elements jjrInfo = listInfo.children().get(3).getAllElements();
//            String xiaoquName = baseInfo2.get(2).text();
            String username = jjrInfo.get(3).text();
//            String towards = listInfo.children().get(3).text();
//            String floor = listInfo.children().get(4).text();

            Elements priceInfo = element.select("div[class=price]");
            String price = priceInfo.select("p[class=sum]").text();
            String danjia = priceInfo.select("p[class=unit]").text();
            log.info(priceInfo.html());
//            DataTraceBean dataTraceBean = JSON.toJavaObject(JSON.parseArray(replace),DataTraceBean.class);
//            log.info(JSON.toJSONString(dataTraceBean));
//            log.info(element.html());
        }

    }

}