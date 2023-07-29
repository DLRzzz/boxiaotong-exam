package com.boxiaotong.exam.utils;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class LabelUtils {

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
            if (tmp.matches("\\d*\\.*\\d+l") || tmp.matches("\\d*\\.*\\d+L")
                    || tmp.matches("\\d*\\.*\\d+升")) {
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
                // System.out.println(Integer.parseInt(key.toString()));

                while(discount.charAt(j) != '减') j++;  // 找到 减 的位置
                j += 1;
                StringBuilder value = new StringBuilder();
                while(j < discount.length() && Character.isDigit(discount.charAt(j))) {
                    char c = discount.charAt(j);
                    value.append(c);
                    j++;
                }
//                 System.out.println(Integer.parseInt(value.toString()));
                int t1 = Integer.parseInt(key.toString()), t2 = Integer.parseInt(value.toString());
                if(discountMap.containsKey(t1)) {
                    discountMap.put(t1, Math.max(t2, discountMap.get(t1)));
                }
                else
                    discountMap.put(t1, t2);  // 加入当前满减信息
            }
        }

        Set<Map.Entry<Integer, Integer>> discountset = discountMap.entrySet();
        Iterator<Map.Entry<Integer, Integer>> iterator = discountset.iterator();

        while(iterator.hasNext()) {  // 遍历所有满减优惠，更新最优解

            Map.Entry<Integer, Integer> entry = iterator.next();
            int key = (Integer) entry.getKey();  // 满
            int value = (Integer) entry.getValue();  // 减

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
}
