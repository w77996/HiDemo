package com.w77996.es.constance;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class Constant {

    @Value("${es.index}")
    public static  String USER_INDEX;

    @Value("${es.alias}")
    public static String USER_INDEX_ALIAS;


}
