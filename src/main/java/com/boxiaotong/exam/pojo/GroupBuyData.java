package com.boxiaotong.exam.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class GroupBuyData {

    @ExcelProperty("查价时间")
    private String searchDate;

    @ExcelProperty("平台名称")
    private String platform;

    @ExcelProperty("大区")
    private String province;

    @ExcelProperty("城市")
    private String city;

    @ExcelProperty("单价")
    private Double originalPrice;

    @ExcelProperty("到手价")
    private Double finalPrice;

    @ExcelProperty("差价")
    private Double discount;

    @ExcelProperty("产品日期")
    private String productDate;

    @ExcelProperty("限购件数")
    private Integer quantityLimit;

}
