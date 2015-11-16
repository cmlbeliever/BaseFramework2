package com.cml.data.dumydata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基础数据
 */
public class SimpleDataService {
    public static List<Map<String, Object>> simpleData(int size) {
        return simpleData(0, size);
    }


    public static List<Map<String, Object>> simpleData(int from, int size) {
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>(size);
        int end = from + size;
        for (int i = from; i < end; i++) {
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("text", "simple data : " + i);
            datas.add(data);
        }
        return datas;
    }
}
