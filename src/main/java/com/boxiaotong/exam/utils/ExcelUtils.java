package com.boxiaotong.exam.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
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
     * @param splitSheet 多个Excel
     * @throws IOException
     */
    public static void zipExcel(HttpServletResponse response, Map<String, List<List<Object>>> splitSheet, List<List<String>> headList) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
        try {
            for (Map.Entry<String, List<List<Object>>> entry : splitSheet.entrySet()) {
                String fileName = entry.getKey();
                List<List<Object>> sheetList = entry.getValue();

                ExcelWriter excelWriter = EasyExcel.write().excelType(ExcelTypeEnum.XLSX).build();
                //构建一个sheet页
                WriteSheet writeSheet = EasyExcel.writerSheet("").build();
                //构建excel表头信息
                WriteTable writeTable0 = EasyExcel.writerTable(0).head(headList).build();
                //将表头和数据写入表格
                excelWriter.write(sheetList, writeSheet, writeTable0);

                //创建压缩文件
                ZipEntry zipEntry = new ZipEntry(fileName);
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
