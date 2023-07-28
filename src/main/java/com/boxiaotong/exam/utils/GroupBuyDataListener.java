package com.boxiaotong.exam.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.fastjson.JSON;
import com.boxiaotong.exam.pojo.GroupBuyData;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class GroupBuyDataListener extends AnalysisEventListener<GroupBuyData> {

    List<GroupBuyData> list = new ArrayList<GroupBuyData>();

    @Override
    public void invoke(GroupBuyData data, AnalysisContext context) throws ExcelDataConvertException {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("导入数据完毕");
    }

    public List<GroupBuyData> getList() {
        return list;
    }
}

