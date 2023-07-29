package com.boxiaotong.exam.utils.getminprice;

import com.boxiaotong.exam.utils.LabelUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(Parameterized.class)
public class GetMinPriceTest {
    private double expected;
    private double originalPrice;
    private String discount;
    private static final String fileName = "src/test/java/com/boxiaotong/exam/utils/getminprice/minprice_input.txt";

    public GetMinPriceTest(double originalPrice, String discount, double expected) {
        this.originalPrice = originalPrice;
        this.discount = discount;
        this.expected = expected;
    }

    @Test
    public void testGetMinPrice() {
        // 价格为浮点数，改为用差值判断
        assertTrue(LabelUtils.getMinPrice(originalPrice, discount) - expected < 0.01);  // 小数点两位之后舍去

        String format = "pass %s expected:%.2f actual:%.2f";
        System.out.println(String.format(format, discount, expected, LabelUtils.getMinPrice(originalPrice, discount)));
    }

    /**
     * 从文件读取数据
     * @return
     */
    @Parameterized.Parameters
    public static List<Object[]> readLines() throws IOException {
        var lines = Files.readAllLines(Path.of(fileName));

        List<Object[]> res = new ArrayList<Object[]>();
        List<Object> line = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            // 特判空行
            if (lines.get(i).trim().equals("")) continue;

            // \s* 匹配空格，并限定分成两段，避免匹配多次
            String tmp = lines.get(i).split("：\\s*", 2)[1];

            if (i % 4 == 0 || i % 4 == 2)
                line.add(Double.parseDouble(tmp));  // 储存价格
            else
                line.add(tmp);
//            System.out.println(tmp);
            if(line.size() == 3) {  // 加入每行测试数据
                res.add(line.toArray());
                line = new ArrayList<>();
            }
        }
        return res;
    }
}
