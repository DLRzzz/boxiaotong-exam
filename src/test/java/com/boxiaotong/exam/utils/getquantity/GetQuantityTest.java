package com.boxiaotong.exam.utils.getquantity;

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
public class GetQuantityTest {
    private int expected;
    private String str;
    private static String fileName = "src/test/java/com/boxiaotong/exam/utils/getquantity/quantity_input.txt";

    public GetQuantityTest(String str, String expected) {
        this.str = str;
        this.expected = Integer.parseInt(expected);
    }

    @Test
    public void testGetQuantity() {
        assertEquals(expected, LabelUtils.getQuantity(str));

        String format = "pass %s expected:%d actual:%d";
        System.out.println(String.format(format, str, expected, LabelUtils.getQuantity(str)));
    }

    @Parameterized.Parameters
    public static List<String[]> readLines() {
        List<String[]> content = new ArrayList<String[]>();

        FileReader fr = null;
        try {
            fr = new FileReader(fileName);
            LineNumberReader lr = new LineNumberReader(fr);
            String line = "";
            while((line = lr.readLine()) != null) {
                String[] tmp = line.split(" ==> ");
                content.add(tmp);
            }
            lr.close();
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            close(fr);
        }
        return content;
    }

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
