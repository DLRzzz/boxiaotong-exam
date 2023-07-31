package com.boxiaotong.exam.service.impl;

import com.boxiaotong.exam.service.SplitByColNameService;
import com.boxiaotong.exam.utils.ExcelUtils;
import com.boxiaotong.exam.utils.NoModelDataListener;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SplitByColNameServiceImpl implements SplitByColNameService {

    @Override
    public void splitByColName(NoModelDataListener noModelDataListener, String colName, HttpServletResponse response) throws IOException {
        List<List<Object>> sheet = noModelDataListener.getSheet();  // Excel的表单内容
        List<List<String>> headList = noModelDataListener.getHeadList();  // Excel的表头序列
        Integer colIndex = noModelDataListener.getColIndex(colName);  // 目标列的索引值

        Map<String, List<List<Object>>> splitSheet = getSplitSheet(sheet, colIndex);  // 拆分后的所有表单信息

        ExcelUtils.zipExcel(response, splitSheet, headList);  // 压缩已分组的Excel
    }

    /**
     * 获取按列拆分后的Excel表单
     *
     * @param sheet
     * @param colIndex
     * @return
     */
    private Map<String, List<List<Object>>> getSplitSheet(List<List<Object>> sheet, Integer colIndex) {

        return sheet.stream().collect(Collectors.groupingBy(e -> "%s.xlsx".formatted(e.get(colIndex))));

//        Map<String, List<List<Object>>> splitSheet = new HashMap();  // 按照列属性值拆分的Excel表单
//
//        for (int i = 0; i < sheet.size(); i++) {
//            String colValue = (String)sheet.get(i).get(colIndex);
//            splitSheet.compute(colValue + ".xlsx", (k, v) -> v == null ? new ArrayList<>() : v)
//                    .add(sheet.get(i));  // 根据列属性值添加到对应Excel中
//        }

//        return splitSheet;
    }
}
