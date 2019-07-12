package com.w77996.easyexcel;


import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Easyexcel {

    public static void main(String[] args) {

        try {

            List<Model> list = new ArrayList<>(610000);
            InputStream inputStream = new FileInputStream("C:\\Users\\w77996\\Downloads\\懒人听书章节.xlsx");
            // 解析每行结果在listener中处理
            List<Object> dataList = EasyExcelFactory.read(inputStream, new Sheet(1, 1, Model.class));
            System.out.println(dataList.size());
            for (Object o : dataList) {
                if (o instanceof Model) {
                    System.out.println(JSONObject.toJSONString(o));
                    Model m = (Model) o;
                    m.setNew_order_num("1" + String.format("%08d", Long.parseLong(m.getAlbum_id())) + String.format("%05d", Long.parseLong(m.getOrder_num())));
                    list.add(m);
                }
//                System.out.println(o);
            }
            newExcel(list);


//            ExcelReader excelReader = new ExcelReader(inputStream, Model.class, new AnalysisEventListener() {
//                @Override
//                public void invoke(Object o, AnalysisContext analysisContext) {
//                    System.out.println(o);
//                    System.out.println(JSONObject.toJSONString(o));
////                    Model model = (Model)o;
////                    list.add(model);
//                }
//
//                @Override
//                public void doAfterAllAnalysed(AnalysisContext analysisContext) {
//
//                }
//            });
//            excelReader.read();
//            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    private static void newExcel(List<Model> list) {
        System.out.println(list.size());
        try {
            File file = new File("C:\\Users\\w77996\\Downloads\\new懒人听书章节.xlsx");
            if (file.exists()) {
                file.delete();
            } else {
                file.createNewFile();
            }
            OutputStream out = new FileOutputStream("C:\\Users\\w77996\\Downloads\\new懒人听书章节.xlsx");
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            Sheet sheet1 = new Sheet(1, 0,Model.class);
            writer.write(list,sheet1);
            writer.finish();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
