package com.atguigu.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.*;
import com.atguigu.gmall.manage.mapper.*;
import com.atguigu.gmall.service.AttrService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;

@Service
public class AttrServiceImpl implements AttrService {

    @Autowired
    private AttrInfoMapper attrMapper;
    @Autowired
    private AttrValueMapper attrValueMapper;
    @Autowired
    private PmsBaseSaleAttrMapper pmsBaseSaleAttrMapper;
    @Autowired
    private PmsProductSaleAttrMapper pmsProductSaleAttrMapper;
    @Autowired
    private PmsProductSaleAttrValueMapper pmsProductSaleAttrValueMapper;

    @Override
    public List<PmsBaseAttrInfo> getAttrByCatalog3Id(String catalog3Id) {
        QueryWrapper<PmsBaseAttrInfo> query = new QueryWrapper<>();
        query.eq("catalog3_id",catalog3Id);
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = attrMapper.selectList(query);
        //封装平台属性值
        for (PmsBaseAttrInfo pmsBaseAttrInfo : pmsBaseAttrInfos) {
            QueryWrapper<PmsBaseAttrValue> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("attr_id",pmsBaseAttrInfo.getId());
            List<PmsBaseAttrValue> pmsBaseAttrValues = attrValueMapper.selectList(queryWrapper);
            pmsBaseAttrInfo.setAttrValueList(pmsBaseAttrValues);
        }
        return pmsBaseAttrInfos;
    }

    @Override
    public String save(PmsBaseAttrInfo attrInfo) {
        List<PmsBaseAttrValue> attrValueList = attrInfo.getAttrValueList();
        //平台属性id 不为空时，修改
        if(StringUtils.isNotEmpty(attrInfo.getId())){
            attrMapper.updateById(attrInfo);
            QueryWrapper<PmsBaseAttrValue> deleteQuery = new QueryWrapper<>();
            deleteQuery.eq("attr_id",attrInfo.getId());
            attrValueMapper.delete(deleteQuery);
            for (PmsBaseAttrValue value : attrValueList) {
                value.setAttrId(attrInfo.getId());
                attrValueMapper.insert(value);
            }
        }else {
            attrMapper.insert(attrInfo);
            if (CollectionUtils.isNotEmpty(attrValueList)) {
                for (PmsBaseAttrValue value : attrValueList) {
                    value.setAttrId(attrInfo.getId());
                    attrValueMapper.insert(value);
                }
            }
        }
        return "success";
    }

    @Override
    public List<PmsBaseAttrValue> getAttrValueList(String attrId) {
        QueryWrapper<PmsBaseAttrValue> query = new QueryWrapper<>();
        query.eq("attr_id",attrId);
        List<PmsBaseAttrValue> pmsBaseAttrValues = attrValueMapper.selectList(query);
        return pmsBaseAttrValues;
    }

    @Override
    public List<PmsBaseSaleAttr> baseSaleAttrList() {
        List<PmsBaseSaleAttr> PmsBaseSaleAttrs = pmsBaseSaleAttrMapper.selectByMap(new HashMap<>());
        return PmsBaseSaleAttrs;
    }

    @Override
    public List<PmsProductSaleAttr> spuSaleAttrList(String spuId) {
        QueryWrapper<PmsProductSaleAttr> wrapper = new QueryWrapper<>();
        wrapper.eq("product_id",spuId);
        List<PmsProductSaleAttr> pmsProductSaleAttrs = pmsProductSaleAttrMapper.selectList(wrapper);
        //封装销售属性的值
        for (PmsProductSaleAttr pmsProductSaleAttr : pmsProductSaleAttrs) {
            QueryWrapper<PmsProductSaleAttrValue> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("product_id",spuId);
            queryWrapper.eq("sale_attr_id",pmsProductSaleAttr.getSaleAttrId());
            List<PmsProductSaleAttrValue> pmsProductSaleAttrValues = pmsProductSaleAttrValueMapper.selectList(queryWrapper);
            pmsProductSaleAttr.setSpuSaleAttrValueList(pmsProductSaleAttrValues);
        }
        return pmsProductSaleAttrs;
    }

}
