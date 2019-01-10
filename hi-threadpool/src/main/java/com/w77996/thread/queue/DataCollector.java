package com.w77996.thread.queue;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @description: 数据dateCollector
 * @author: w77996
 * @create: 2018年12月27日 17:18
 **/
public class DataCollector<T> {
    private Queue<T> datas;
    private int batchSize;
    private Flusher flusher;

    public static interface Flusher<T> {
        public void flush(List<T> datas);
    }

    public DataCollector(Flusher<T> flusher, int batchSize) {
        this.flusher = flusher;
        this.batchSize = batchSize;
        datas = new ArrayBlockingQueue<T>(batchSize);
    }

    public void collect(T data) {
        while (!datas.offer(data)) {
            List<T> copy;
            synchronized (datas) {
                copy = new ArrayList<>(datas);
                datas.clear();
            }
            flush(copy);
        }
    }

    public void finish() {
        synchronized (datas) {
            if (!datas.isEmpty()) {
                flush(new ArrayList<>(datas));
                datas.clear();
            }
        }
    }

    private void flush(List<T> datas) {
        if (datas.isEmpty()) {
            return;
        }
        flusher.flush(datas);
    }
    public static List<String> createTimeRangAsc(String date) {

        List<String> result = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(DateUtils.parseDate(date, "yyyyMMdd"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);


        for (int i = 0; i < 49; i++) {
            Date beforeD = calendar.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = sdf.format(beforeD);
            calendar.add(Calendar.MINUTE, 30);
            result.add(time);
        }

        return result;
    }


    public static void main(String[] args) {
        int i =0;
        final DataCollector<List<Object>> dataCollector = new DataCollector<>(new DataCollector.Flusher<List<Object>>() {
            @Override
            public void flush(List<List<Object>> datas) {
                                System.out.println(i);
            }
        }, 100);

        System.out.println(TimeUnit.HOURS.toMillis(3L)/1000/60);
    }
}
