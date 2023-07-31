package com.boxiaotong.exam.utils.getserieslabel;

import com.boxiaotong.exam.mapper.BrandMapMapper;
import com.boxiaotong.exam.pojo.BrandMap;
import com.boxiaotong.exam.service.GoodsService;
import com.boxiaotong.exam.utils.LabelUtils;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class getSeriesLabelTest {
    @Autowired
    private BrandMapMapper brandMapMapper;

    @Autowired
    private GoodsService goodsService;

    @Test
    public void test() {
        List<BrandMap> mappingDict = brandMapMapper.selectList(null); // 得到所有brand映射

        TestCase.assertEquals("其他", LabelUtils.getSeriesLabel("其他", "菠萝啤整箱装 24罐*320ml零酒精果啤果味汽水碳酸饮料夏日饮品" ,mappingDict));
        assertEquals("清爽", LabelUtils.getSeriesLabel("雪花", "SNOW雪花纯生啤酒8度500ml*12罐匠心营造易拉罐装整箱黄啤酒 500mL*12瓶" ,mappingDict));
        assertEquals("清爽", LabelUtils.getSeriesLabel("雪花", "雪花啤酒8°清爽啤酒330ml*24听 罐装整箱麦芽酿制 武汉满百包邮" ,mappingDict));
        assertEquals("淡爽", LabelUtils.getSeriesLabel("雪花", "雪花（SNOW）啤酒  淡爽  500ml*12听  整箱装  送礼自饮佳品" ,mappingDict));
        assertEquals("花生巧克力牛奶世涛", LabelUtils.getSeriesLabel("迷失海岸", "进口精酿啤酒迷失海岸花生酱牛奶世涛卡斯四料特浓巧克力组合装" ,mappingDict));
        assertEquals("花生巧克力牛奶世涛", LabelUtils.getSeriesLabel("迷失海岸", "进口精酿啤酒迷失海岸花生酱牛奶世涛卡斯四料特浓巧克力组合装" ,mappingDict));
        assertEquals("海妖精酿其他", LabelUtils.getSeriesLabel("海妖精酿", "海妖精酿啤酒瓶装比利时小麦白啤330ml12瓶包邮" ,mappingDict));
    }

    @Test
    public void testBatchUpdateSeries() {
        goodsService.batchUpdateSeries(List.of(1, 2, 3, 4, 5, 6, 7));
    }
}
