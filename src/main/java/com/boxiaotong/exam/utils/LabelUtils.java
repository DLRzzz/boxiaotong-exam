package com.boxiaotong.exam.utils;

import com.boxiaotong.exam.mapper.BrandMapMapper;
import com.boxiaotong.exam.pojo.BrandMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class LabelUtils {

    public static BrandMapMapper brandMapMapper;

    // 使用set方法注入mapper
    @Autowired
    public void setBrandMapMapper(BrandMapMapper brandMapMapper) {
        LabelUtils.brandMapMapper = brandMapMapper;
    }

    /**
     * 从商品名称中提取单量
     *
     * @param str
     * @return 单量
     */
    public static int getQuantity(String str) {

        String regex = "\\d*\\.*\\d+(l|L|升|ml|mL|Ml|ML|毫升)";

        Pattern patten = Pattern.compile(regex);
        Matcher matcher = patten.matcher(str);
        if (matcher.find()) {
            String tmp = matcher.group();
            // System.out.println(tmp);
            int start = matcher.start();
            int end = matcher.end();
            String res;
            if (tmp.matches("\\d*\\.*\\d+(l|L|升)")) {
                res = str.substring(start, end - 1);
                return (int)(Double.parseDouble(res) * 1000);
            } else {
                res = str.substring(start, end - 2);
                return Integer.parseInt(res);  // 不考虑浮点数
            }
        } else {
            return 0;
        }
    }

    /**
     * 计算最低到手价
     *
     * @param originalPrice
     * @param discount
     * @return 最低到手价
     */
    public static Double getMinPrice(Double originalPrice, String discount) {
        Double minPrice = originalPrice;  // 最优解初始化为页面价格
        // 构建 满->max(减) 优惠映射
        Map<Integer, Integer> discountMap = new LinkedHashMap<>();
        for(int i = 0, j = 0; i < discount.length(); i++) {
            if(discount.charAt(i) == '满') {
                j = i + 1;
                StringBuilder key = new StringBuilder();
                while(j < discount.length() && Character.isDigit(discount.charAt(j))) {
                    char c = discount.charAt(j);
                    key.append(c);
                    j++;
                }

                while(j < discount.length() && discount.charAt(j) != '减') j++;  // 找到 减 的位置

                if(j >= discount.length()) break;  // 找到 满 ，而未找到对应的 减

                j += 1;
                StringBuilder value = new StringBuilder();
                while(j < discount.length() && Character.isDigit(discount.charAt(j))) {
                    char c = discount.charAt(j);
                    value.append(c);
                    j++;
                }

                int t1 = Integer.parseInt(key.toString()), t2 = Integer.parseInt(value.toString());
    
                discountMap.compute(t1, (k, v) -> v == null ? t2 : Math.max(v, t2));  // 更新当前满减信息
            }


        }

        for (Map.Entry<Integer, Integer> entry : discountMap.entrySet()) {  // 遍历所有满减优惠，更新最优解
            int key = entry.getKey();
            Integer value = entry.getValue();

            int cnt = 1;  // 购买商品件数
            double curPrice = originalPrice;  // 当前单件商品价格
            while(curPrice < key){
                curPrice += originalPrice;
                cnt++;
            }
            curPrice = (curPrice - value) / cnt;
            minPrice = Math.min(minPrice, curPrice);  // 更新最优解
        }
//        System.out.println("当前最优解：" + minPrice);
        return minPrice;
    }

    /**
     * 根据商品品牌、名称查询商品系列
     * @param brand
     * @param name
     * @param mappingDict 所有的brand映射规则
     * @return
     */

    public static String getSeriesLabel(String brand, String name, List<BrandMap> mappingDict) {
        if ("其他".equals(brand)) return "其他";

        for (int i = 0; i < mappingDict.size(); i++) {

            String series = mappingDict.get(i).getSeries();

            String firstKey = mappingDict.get(i).getFirstKey();
            String secondKey = mappingDict.get(i).getSecondKey();
            String thirdKey = mappingDict.get(i).getThirdKey();
            String fourthKey = mappingDict.get(i).getFourthKey();

            if (!"".equals(firstKey) && name.contains(firstKey)) {  // 匹配第一关键字
                return series;
            } else if(null != secondKey && name.contains(secondKey) &&
                    (null == thirdKey || null != thirdKey && name.contains(thirdKey)) &&
                    (null == fourthKey || null != fourthKey && name.contains(fourthKey))) {
                // 第二、三、四个关键字至少存在一个，且非null的话需要均匹配上
                // null用 == 判断
                return series;
            }
        }
        return brand + "其他";
    }
}
