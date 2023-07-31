package com.boxiaotong.exam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.boxiaotong.exam.pojo.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

    boolean updateAllSeries(@Param("goodsList") List<Goods> goodsList);
}
