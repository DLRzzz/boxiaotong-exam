package com.boxiaotong.exam.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONArray;
import com.boxiaotong.exam.pojo.Sku;
import com.boxiaotong.exam.service.SplitBySkuService;
import com.boxiaotong.exam.utils.NoModelDataListener;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SplitBySkuServiceImpl implements SplitBySkuService {

    @Override
    public void splitBySku(NoModelDataListener noModelDataListener, HttpServletResponse response) throws IOException {
        List<List<Object>> sheet = noModelDataListener.getSheet();  // Excel的表单内容
        List<List<String>> headList = noModelDataListener.getHeadList();  // Excel的表头序列
        Integer skuColIndex = noModelDataListener.getColIndex("商品sku详情");  // 目标列的索引值

        List<List<String>> updatedHeadList = updateHeadList(headList);
        List<List<Object>> splitSheet = getSplitSheet(sheet, skuColIndex, headList);

        EasyExcel.write(response.getOutputStream()).head(updatedHeadList)
                .sheet("").doWrite(splitSheet);
    }

    /**
     * 更新sku分裂后的表头
     *
     * @param headList
     * @return 
     */
    private List<List<String>> updateHeadList(List<List<String>> headList) {
        List<List<String>> updatedHeadList = new ArrayList<>();

        for(int i = 0; i < headList.size(); i++) {
            String colName = headList.get(i).get(0);
            if("商品sku详情".equals(colName)) {
                updatedHeadList.add(List.of("sku_id"));
                updatedHeadList.add(List.of("sku名称"));
                updatedHeadList.add(List.of("sku现价"));
                updatedHeadList.add(List.of("sku原价"));
                updatedHeadList.add(List.of("sku库存"));
            } else {
                updatedHeadList.add(List.of(colName));
            }
        }
        return updatedHeadList;
    }

    /**
     * 获取sku分裂后的Excel表单
     *
     * @param sheet 原表单
     * @param skuColIndex
     * @param headList 原表头
     * @return
     */
    private List<List<Object>> getSplitSheet(List<List<Object>> sheet, Integer skuColIndex, List<List<String>> headList) {

        List<List<Object>> splitSheet = new ArrayList<>();  // 按照sku拆分的Excel表单

        for (int i = 0; i < sheet.size(); i++) {  // 遍历excel表单的每一行

            String skuContent = (String) sheet.get(i).get(skuColIndex);  // 记录sku属性值(Json对象字符数组)
            List<Sku> skuJsonList = JSONArray.parseArray(skuContent, Sku.class);

            for (int j = 0; j < skuJsonList.size(); j++) {  // 总行数取决于JsonArray.size()
                List<Object> splitLine = new ArrayList<>();  // 按照字段名排序的excel行

                for (int k = 0; k < headList.size(); k++) {  // 按照表头顺序构造每行
                    System.out.println(k);
                    if (k == skuColIndex) {
                        splitLine.add(skuJsonList.get(j).getSku_id());
                        splitLine.add(skuJsonList.get(j).getSku_name());
                        splitLine.add(skuJsonList.get(j).getSku_current_price());
                        splitLine.add(skuJsonList.get(j).getSku_original_price());
                        splitLine.add(skuJsonList.get(j).getSku_stock());
                    } else {
                        splitLine.add(sheet.get(i).get(k));
                    }
                }
                splitSheet.add(splitLine);
            }
        }
        return splitSheet;
    }
}
