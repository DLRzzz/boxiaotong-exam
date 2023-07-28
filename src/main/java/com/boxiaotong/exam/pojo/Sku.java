package com.boxiaotong.exam.pojo;

import lombok.Data;

@Data
public class Sku {
    private String sku_id;

    private String sku_name;

    private String sku_current_price;

    private String sku_original_price;

    private String sku_stock;
}
