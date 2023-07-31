package com.boxiaotong.exam.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandMap {  // 存储 brand -> series 的映射

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String brand;

    private String firstKey;

    private String secondKey;

    private String thirdKey;

    private String fourthKey;

    private String series;
}
