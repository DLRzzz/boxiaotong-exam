package com.boxiaotong.exam.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.boxiaotong.exam.pojo.GroupBuyData;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ExcelUtils {
    /**
     * 将多个Excel文件打包成zip文件
     *
     * @param response
     * @param map 多个Excel
     * @throws IOException
     */
    public static void zipExcel(HttpServletResponse response, Map<String, List<GroupBuyData>> map) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
        try {
            for (Map.Entry<String, List<GroupBuyData>> entry : map.entrySet()) {
                String k = entry.getKey();
                List<GroupBuyData> value = entry.getValue();
                //构建一个excel对象,这里注意type要是xls不能是xlsx,否则下面的写入后流会关闭,导致报错
                ExcelWriter excelWriter = EasyExcel.write().excelType(ExcelTypeEnum.XLS).build();
                //构建一个sheet页
                WriteSheet writeSheet = EasyExcel.writerSheet("").build();
                //构建excel表头信息
                WriteTable writeTable0 = EasyExcel.writerTable(0).head(GroupBuyData.class).needHead(Boolean.TRUE).build();
                //将表头和数据写入表格
                excelWriter.write(value, writeSheet, writeTable0);

                //创建压缩文件
                ZipEntry zipEntry = new ZipEntry(k);
                zipOutputStream.putNextEntry(zipEntry);
                Workbook workbook = excelWriter.writeContext().writeWorkbookHolder().getWorkbook();
                //将excel对象以流的形式写入压缩流
                workbook.write(zipOutputStream);
            }
            zipOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            zipOutputStream.close();
            outputStream.close();
        }
    }

}
