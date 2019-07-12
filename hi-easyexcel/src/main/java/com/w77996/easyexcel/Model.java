package com.w77996.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;


@Data
public class Model extends BaseRowModel {

    @ExcelProperty(value = "channelid", index = 0)
    private String channelid;

    @ExcelProperty(value = "album_id", index = 1)
    private String album_id;

    @ExcelProperty(value = "track_id", index = 2)
    private String track_id;

    @ExcelProperty(value = "track_title", index = 3)
    private String track_title;

    @ExcelProperty(value = "order_num", index = 4)
    private String order_num;

    @ExcelProperty(value = "new_order_num", index = 5)
    private String new_order_num;
}
