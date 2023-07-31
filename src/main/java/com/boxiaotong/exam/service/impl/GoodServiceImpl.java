package com.boxiaotong.exam.service.impl;

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

    public boolean batchUpdateSeries(List idList) {
        List<Goods> goodsList = goodsMapper.selectBatchIds(idList);  // 分批获取商品信息

        List<BrandMap> mappingDict = brandMapMapper.selectList(null); // 得到所有brand映射

        List<Goods> updatedGoodsList = new ArrayList<>();  // 储存更新后的goodList

        for (int i = 0; i < goodsList.size(); i++) {
            Integer id = goodsList.get(i).getId();
            String brand = goodsList.get(i).getBrand();
            String name = goodsList.get(i).getName();

            String series = LabelUtils.getSeriesLabel(brand, name, mappingDict);

            Goods goods = new Goods(id, brand, name, series);

            updatedGoodsList.add(goods);
        }
        return goodsMapper.batchUpdateSeries(updatedGoodsList);
    }

}
