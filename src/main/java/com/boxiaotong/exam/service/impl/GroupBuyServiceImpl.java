package com.boxiaotong.exam.service.impl;

import com.boxiaotong.exam.pojo.GroupBuyData;
import com.boxiaotong.exam.service.GroupBuyService;
import com.boxiaotong.exam.utils.ExcelUtils;
import com.boxiaotong.exam.utils.GroupBuyDataListener;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@Service
public class GroupBuyServiceImpl implements GroupBuyService {

    private Map<String, String> methodMap ;  // 中文列名与对应英文get方法名的映射
    private Set<String> colValueSet;  // 所选列的所有属性值的集合

    private List<GroupBuyData> entireExcel;  // 传入的Excel文件

    public GroupBuyServiceImpl() {
        initMethodMap();  // 默认初始化一次methodMap
    }

    @Override
    public void splitExcel(GroupBuyDataListener groupBuyDataListener, String colName, HttpServletResponse response) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
        entireExcel = groupBuyDataListener.getList();

        initColValueSet(colName);

        Map<String, List<GroupBuyData>> splitedExcel = getSplitedExcel(colName);

        //压缩已分组的Excel
        ExcelUtils.zipExcel(response, splitedExcel);
    }

    /**
     * 初始化列名与对应get方法的映射
     */
    private void initMethodMap() {
        methodMap = new HashMap<>();
        methodMap.put("查价时间", "getSearchTime");
        methodMap.put("平台名称", "getPlatform");
        methodMap.put("大区", "getProvince");
        methodMap.put("城市", "getCity");
        methodMap.put("单价", "getOriginPrice");
        methodMap.put("到手价", "getFinalPrice");
        methodMap.put("差价", "getDiscount");
        methodMap.put("产品日期", "getProductDate");
        methodMap.put("限购件数", "getQuantityLimit");
    }

    /**
     * 初始化所选列名下属性值的集合
     *
     * @param colName 列名
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private void initColValueSet(String colName) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        colValueSet = new HashSet<>(); ;  // 更新colValueSet，避免重复调用接口致使colValueSet不正确
        Class clazz = Class.forName("com.boxiaotong.exam.pojo.GroupBuyData");  // 获取实体类
        Method method = clazz.getMethod(methodMap.get(colName), null);  // 根据列名获取方法

        for (int i = 0; i < entireExcel.size(); i++) {
            String colValue = (String)method.invoke(entireExcel.get(i), null);  // 所选列的属性值
            if (!colValueSet.contains(colValue)){
                colValueSet.add(colValue);
            }
        }
//        for (String colValue : colValueSet) {
//            System.out.println(colValue);
//        }
    }

    /**
     * 获取拆分后的Excel表
     *
     * @param colName 列名
     * @return
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private Map<String, List<GroupBuyData>> getSplitedExcel(String colName) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Map<String, List<GroupBuyData>> splitedExcel = new HashMap();  // 按照列属性值拆分原有Excel
        for (String colValue : colValueSet) {
            splitedExcel.put(colValue + ".xls", new ArrayList<>());
        }

        Class clazz = Class.forName("com.boxiaotong.exam.pojo.GroupBuyData");  // 获取实体类
        Method method = clazz.getMethod(methodMap.get(colName), null);  // 根据列名获取方法

        for (int i = 0; i < entireExcel.size(); i++) {
            String colValue = (String)method.invoke(entireExcel.get(i), null);
            splitedExcel.get(colValue + ".xls").add(entireExcel.get(i));  // 根据列属性值添加到对应Excel中
        }
        System.out.println(splitedExcel.size());
        return splitedExcel;
    }
}
