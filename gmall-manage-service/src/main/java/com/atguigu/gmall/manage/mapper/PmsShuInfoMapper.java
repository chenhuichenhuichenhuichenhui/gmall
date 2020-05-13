package com.atguigu.gmall.manage.mapper;

import com.atguigu.gmall.bean.PmsSkuInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PmsShuInfoMapper extends BaseMapper<PmsSkuInfo> {
    List<PmsSkuInfo> selectSkuAndAttrValueBySkuId(String productId);

}
