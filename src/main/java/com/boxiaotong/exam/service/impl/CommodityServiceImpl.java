package com.boxiaotong.exam.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONArray;
import com.boxiaotong.exam.pojo.Sku;
import com.boxiaotong.exam.service.CommodityService;
import com.boxiaotong.exam.utils.NoModelDataListener;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommodityServiceImpl implements CommodityService {
    private List<List<String>> headList = new ArrayList<>();  // 拆分后的动态Excel表头
    private List<List<Object>> splitedSku = new ArrayList<>();  // 存储所有sku拆分后的Excel行
    @Override
    public void skuSplit(NoModelDataListener noModelDataListener, HttpServletResponse response) throws IOException {

        initHeadList(noModelDataListener);
        initSplitedSku(noModelDataListener);


        // 这里需要设置不关闭流
        EasyExcel.write(response.getOutputStream()).head(headList)
                .autoCloseStream(Boolean.FALSE).sheet("")
                .doWrite(splitedSku);
    }

    /**
     * 获取拆分sku后的Excel表头
     * 
     * @param noModelDataListener
     */
    private void initHeadList(NoModelDataListener noModelDataListener) {
        Map<Integer, String> keyMap = noModelDataListener.getKeyMap();  // 原Excel的表头映射
        
        for(int i = 0; i < keyMap.size(); i++) {
            if("商品sku详情".equals(keyMap.get(i))) {
                List<String> head0 = new ArrayList<>();
                List<String> head1 = new ArrayList<>();
                List<String> head2 = new ArrayList<>();
                List<String> head3 = new ArrayList<>();
                List<String> head4 = new ArrayList<>();
                head0.add("sku_id");
                head1.add("sku名称");
                head2.add("sku现价");
                head3.add("sku原价");
                head4.add("sku库存");
                headList.add(head0);
                headList.add(head1);
                headList.add(head2);
                headList.add(head3);
                headList.add(head4);
            } else {
                List<String> head = new ArrayList<>();
                head.add(keyMap.get(i));
                headList.add(head);
            }
        }

//        for(int i = 0; i < headList.size(); i++) {
//            System.out.print(headList.get(i).get(0) + "\t");
//        }
//        System.out.println();
    }

    /**
     * 获取拆分sku后的Excel表
     *
     * @param noModelDataListener
     */
    private void initSplitedSku(NoModelDataListener noModelDataListener) {
        List<Map<String, Object>> entireExcel = noModelDataListener.getList();  // 传入的Excel文件

        for (int i = 0; i < entireExcel.size(); i++) {  // 遍历excel每一行
            Map<String, Object> repedtedContent = new HashMap<>();  // 记录除了sku以外所有属性值
            String skuContent = null;  // 记录sku属性值(Json对象字符数组)

            for (String key : entireExcel.get(i).keySet()) {  // 遍历excel每一个字段名
                if ("商品sku详情".equals(key)) {
                    skuContent = (String)entireExcel.get(i).get(key);
                } else {
                    repedtedContent.put(key, entireExcel.get(i).get(key));
                }
            }

            List<Sku> skuJsonList = JSONArray.parseArray(skuContent, Sku.class);
            for (int j = 0; j < skuJsonList.size(); j++) {  // 总行数取决于JsonArray的长度
                List<Object> splitedLine = new ArrayList<>();  // 按照字段名排序的excel行
                for (int k = 0; k < headList.size(); k++) {  // 按照表头顺序构造每行
                    String colName = headList.get(k).get(0);
                    if("sku_id".equals(colName)) {
                        splitedLine.add(skuJsonList.get(j).getSku_id());
                        splitedLine.add(skuJsonList.get(j).getSku_name());
                        splitedLine.add(skuJsonList.get(j).getSku_current_price());
                        splitedLine.add(skuJsonList.get(j).getSku_original_price());
                        splitedLine.add(skuJsonList.get(j).getSku_stock());
                        k += 4;  // 指针后移4
                    } else {
                        splitedLine.add(entireExcel.get(i).get(colName));
                    }
                }
                splitedSku.add(splitedLine);
            }
        }
    }
}
