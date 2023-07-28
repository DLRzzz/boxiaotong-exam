package com.boxiaotong.exam.controller;

import com.alibaba.excel.EasyExcel;
import com.boxiaotong.exam.pojo.GroupBuyData;
import com.boxiaotong.exam.service.GroupBuyService;
import com.boxiaotong.exam.utils.GroupBuyDataListener;
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
public class GroupBuyController {

    @Autowired
    private GroupBuyService groupBuyService;

    @PostMapping("/splitExcel")
    public void splitExcel(@RequestParam("file") MultipartFile file, @RequestParam("colName") String colName,
                           HttpServletResponse response) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {

        // 获取Excel文件内容
        GroupBuyDataListener groupBuyDataListener = new GroupBuyDataListener();
        EasyExcel.read(file.getInputStream(), GroupBuyData.class, groupBuyDataListener).sheet().doRead();
       
        // 配置导出文件
        String fileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf("."))
                          + ".zip";  // 最终导出的zip文件名
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

        groupBuyService.splitExcel(groupBuyDataListener, colName, response);
    }
}
