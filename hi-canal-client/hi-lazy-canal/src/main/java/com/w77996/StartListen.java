package com.w77996;

import com.w77996.canal.HiCanalClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @author w77996
 */
@Component
@Slf4j
public class StartListen implements ApplicationListener<ContextRefreshedEvent> {

    private static boolean start = false;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.info("");
        if(!start) {
            start = true;
            Set<String> prefixSet = new HashSet<String>();
            prefixSet.add("basic");
            try{
                String destination = "example";
                HiCanalClient client = new HiCanalClient(destination);
                client.init();
            }catch(Throwable t){
                log.error("canal初始化异常", t);
            }
        }
    }
}
