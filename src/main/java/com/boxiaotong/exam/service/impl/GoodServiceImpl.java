package com.boxiaotong.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaotong.exam.mapper.BrandMapMapper;
import com.boxiaotong.exam.mapper.GoodsMapper;
import com.boxiaotong.exam.pojo.BrandMap;
import com.boxiaotong.exam.pojo.Goods;
import com.boxiaotong.exam.service.GoodsService;
import com.boxiaotong.exam.utils.LabelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class GoodServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private BrandMapMapper brandMapMapper;

    public void updateAllSeries() {

        int pageId = 1;  // 当前页数
        int pageSize = 5;  // 页面大小

        List<BrandMap> mappingDict = brandMapMapper.selectList(null); // 得到所有brand映射

        while (true) {
            IPage<Goods> goodsIPage = new Page<>(pageId, pageSize);
            QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByAsc("id");
            // 将record按照ID升序排序，并返回第page页的内容
            List<Goods> goodsList = goodsMapper.selectPage(goodsIPage, queryWrapper).getRecords();

            if (goodsList.size() == 0) break;
            else pageId ++;

            List<Goods> updatedGoodsList = new ArrayList<>();  // 储存更新后的goodList

            for (int i = 0; i < goodsList.size(); i++) {
                Integer id = goodsList.get(i).getId();
                String brand = goodsList.get(i).getBrand();
                String name = goodsList.get(i).getName();

                String series = LabelUtils.getSeriesLabel(brand, name, mappingDict);

                Goods goods = new Goods(id, brand, name, series);

                updatedGoodsList.add(goods);
            }
            goodsMapper.updateAllSeries(updatedGoodsList);
        }
    }

}
