package com.boxiaotong.exam.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class NoModelDataListener extends AnalysisEventListener<Map<Integer, String>> {

    List<List<Object>> sheet = new ArrayList<>();

    List<List<String>> headList=new ArrayList<>();  // 储存字段名序列

    /**
     * 重写invokeHeadMap方法，获取表头，如果有需要获取第一行表头就重写这个方法，不需要则不需要重写
     *
     * @param headMap Excel每行解析的数据为Map<Integer, String>类型，Integer是Excel的列索引,String为Excel的单元格值
     * @param context context能获取一些东西，比如context.readRowHolder().getRowIndex()为Excel的行索引，表头的行索引为0，0之后的都解析成数据
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
       log.info("解析到一条头数据：{}, currentRowHolder: {}", headMap.toString(), context.readRowHolder().getRowIndex());

        for(int i = 0; i < headMap.size(); i++) {
            String colName = headMap.get(i);
            headList.add(List.of(colName));
        }
    }

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));

        List<Object> line = new ArrayList<>();
        for(int i = 0; i < data.size(); i++) {
            line.add(data.get(i));
        }
        sheet.add(line);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("导入数据完毕");
    }

    public List<List<Object>> getSheet() {
        return sheet;
    }

    public List<List<String>> getHeadList() {
        return headList;
    }

    /**
     * 获得colName的索引值
     *
     * @param colName
     * @return
     */
    public Integer getColIndex(String colName) {
        for(int i = 0; i < headList.size(); i++) {
            if(colName.equals(headList.get(i).get(0)))
                return i;
        }
        return -1;
    }
}

