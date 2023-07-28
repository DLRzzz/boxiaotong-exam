package com.boxiaotong.exam.utils.getminprice;

import com.boxiaotong.exam.utils.LabelUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.Closeable;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

@RunWith(Parameterized.class)
public class GetMinPriceTest {
    private double expected;
    private double originalPrice;
    private String discount;
    private static String fileName = "src/test/java/com/boxiaotong/exam/utils/getminprice/minprice_input.txt";

    public GetMinPriceTest(double originalPrice, String discount, double expected) {
        this.originalPrice = originalPrice;
        this.discount = discount;
        this.expected = expected;
    }

    @Test
    public void testGetQuantity() {
        assertEquals(expected, LabelUtils.getMinPrice(originalPrice, discount));

        String format = "pass %s expected:%.0f actual:%.0f";
        System.out.println(String.format(format, discount, expected, LabelUtils.getMinPrice(originalPrice, discount)));
    }

    /**
     * 从文件读取数据
     * @return
     */
    @Parameterized.Parameters
    public static List<Object[]> readLines() {
        List<Object[]> content = new ArrayList<Object[]>();

        FileReader fr = null;
        try {
            fr = new FileReader(fileName);
            LineNumberReader lr = new LineNumberReader(fr);
            String line = "";
            while((line = lr.readLine()) != null) {
                Object[] item = new Object[3];
                int cnt = 0;
                while(line != null && !line.equals("")) {
                    String tmp = line.split("：\\s*")[1];  // \s* 匹配空格
                    if(cnt != 1) item[cnt++] = Double.parseDouble(tmp);
                    else item[cnt++] = tmp;
                    line = lr.readLine();  // 换行读取
                }
                content.add(item);
            }
            lr.close();
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            close(fr);
        }
        return content;
    }

    /**
     * IO辅助函数
     * @param inout
     */
    public static void close(Closeable inout) {
        if(inout != null) {
            try {
                inout.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
}
