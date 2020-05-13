package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.PmsBaseAttrInfo;
import com.atguigu.gmall.bean.PmsBaseAttrValue;
import com.atguigu.gmall.bean.PmsBaseSaleAttr;
import com.atguigu.gmall.bean.PmsProductSaleAttr;

import java.util.List;

public interface AttrService {
    List<PmsBaseAttrInfo> getAttrByCatalog3Id(String catalog3Id);

    String save(PmsBaseAttrInfo attrInfo);

    List<PmsBaseAttrValue> getAttrValueList(String attrId);

    List<PmsBaseSaleAttr> baseSaleAttrList();

    List<PmsProductSaleAttr> spuSaleAttrList(String spuId);
}
