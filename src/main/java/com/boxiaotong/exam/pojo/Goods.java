package com.boxiaotong.exam.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String brand;

    private String name;

    private String series;
}
