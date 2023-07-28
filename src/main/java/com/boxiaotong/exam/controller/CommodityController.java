package com.boxiaotong.exam.controller;

import com.alibaba.excel.EasyExcel;
import com.boxiaotong.exam.service.CommodityService;
import com.boxiaotong.exam.utils.NoModelDataListener;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;

@RestController
public class CommodityController {
    @Autowired
    private CommodityService commodityService;

    @PostMapping("/skuSplit")
    public void skuSplit(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws IOException {
        // 获取Excel文件内容
        NoModelDataListener noModelDataListener = new NoModelDataListener();
        EasyExcel.read(file.getInputStream(), noModelDataListener).sheet().doRead();

        // 配置导出文件
        String fileName = "test_new.xlsx";
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") );

        commodityService.skuSplit(noModelDataListener, response);


    }

}
