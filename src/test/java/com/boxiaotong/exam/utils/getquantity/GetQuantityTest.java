package com.boxiaotong.exam.utils.getquantity;

import com.boxiaotong.exam.utils.LabelUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

@RunWith(Parameterized.class)
public class GetQuantityTest {
    private int expected;
    private String str;
    private static final String fileName = "src/test/java/com/boxiaotong/exam/utils/getquantity/quantity_input.txt";

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
    public static List<String[]> readLines() throws IOException {
        var lines = Files.readAllLines(Path.of(fileName));
        return lines.stream().map(line -> line.split(" ==> ")).toList();
    }
}
