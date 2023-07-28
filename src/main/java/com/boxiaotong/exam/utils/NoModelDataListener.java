package com.boxiaotong.exam.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class NoModelDataListener extends AnalysisEventListener<Map<Integer, String>> {

    List<Map<String, Object>> list = new ArrayList<>();

    Map<Integer, String> keyMap = new HashMap<>();  // 储存字段

    List<String> keyList=new ArrayList<>();  // 储存字段名的

    /**
     * 重写invokeHeadMap方法，获取表头，如果有需要获取第一行表头就重写这个方法，不需要则不需要重写
     *
     * @param headMap Excel每行解析的数据为Map<Integer, String>类型，Integer是Excel的列索引,String为Excel的单元格值
     * @param context context能获取一些东西，比如context.readRowHolder().getRowIndex()为Excel的行索引，表头的行索引为0，0之后的都解析成数据
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
       log.info("解析到一条头数据：{}, currentRowHolder: {}", headMap.toString(), context.readRowHolder().getRowIndex());
        for (Integer i : headMap.keySet()) {
            keyList.add(headMap.get(i));
        }
        keyMap.putAll(headMap);
    }

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        HashMap<String, Object> map = new HashMap<>();

        for (Integer i: data.keySet()) {
            String s = keyMap.get(i);
            map.put(s,data.get(i));
        }
        list.add(map);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("导入数据完毕");
    }

    public List<Map<String, Object>> getList() {
        return list;
    }

    public Map<Integer, String> getKeyMap() {
        return keyMap;
    }

}

