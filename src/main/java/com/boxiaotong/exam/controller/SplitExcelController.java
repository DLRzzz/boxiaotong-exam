package com.boxiaotong.exam.controller;

import com.alibaba.excel.EasyExcel;
import com.boxiaotong.exam.service.SplitBySkuService;
import com.boxiaotong.exam.service.SplitByColNameService;
import com.boxiaotong.exam.utils.NoModelDataListener;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;

@RestController
public class SplitExcelController {
    @Autowired
    private SplitByColNameService splitByColNameService;

    @Autowired
    private SplitBySkuService splitBySkuService;

    // 根据列名拆分Excel
    @PostMapping("/splitExcel")
    public void splitExcelByColName(@RequestParam("file") MultipartFile file, @RequestParam("colName") String colName,
                           HttpServletResponse response) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {

        // 获取Excel文件内容
        NoModelDataListener noModelDataListener = new NoModelDataListener();
        EasyExcel.read(file.getInputStream(), noModelDataListener).sheet().doRead();

        // 配置导出文件
        String fileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")) + ".zip";  // 最终导出的zip文件名
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

        splitByColNameService.splitByColName(noModelDataListener, colName, response);
    }


    @PostMapping("/skuSplit")
    public void skuSplit(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws IOException {
        // 获取Excel文件内容
        NoModelDataListener noModelDataListener = new NoModelDataListener();
        EasyExcel.read(file.getInputStream(), noModelDataListener).sheet().doRead();

        // 配置导出文件
        String fileName = "result.xlsx";
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") );

        splitBySkuService.splitBySku(noModelDataListener, response);
    }
}
