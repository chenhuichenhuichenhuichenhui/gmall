package com.atguigu.gmall.item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.PmsProductSaleAttr;
import com.atguigu.gmall.bean.PmsSkuAttrValue;
import com.atguigu.gmall.bean.PmsSkuInfo;
import com.atguigu.gmall.bean.PmsSkuSaleAttrValue;
import com.atguigu.gmall.service.PmsSkuInfoService;
import com.atguigu.gmall.service.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ItemController {
    @Reference
    private PmsSkuInfoService pmsSkuInfoService;
    @Reference
    private SpuService spuService;

    @RequestMapping("{skuId}.html")
    public String detail(@PathVariable String skuId, ModelMap map){
        //查询sku的信息
        PmsSkuInfo pmsSkuInfo = pmsSkuInfoService.getSkuById(skuId);
        map.put("skuInfo",pmsSkuInfo);
        //查询该sku所属的spu对应的所有销售属性以及销售属性的值，以及当前的sku所属的销售属性以及销售属性值被选中
        List<PmsProductSaleAttr> pmsProductSaleAttrs =  spuService.selectSpuSaleAttrListCheckBySku(pmsSkuInfo.getProductId(),skuId);
        map.put("spuSaleAttrListCheckBySku",pmsProductSaleAttrs);
        //查询销售属性值与sku的hash
        Map<String,String> map2 = new HashMap<>();
        List<PmsSkuInfo> pmsSkuInfos =  pmsSkuInfoService.getSkuAndAttrValueBySkuId(pmsSkuInfo.getProductId());
        for (PmsSkuInfo skuInfo : pmsSkuInfos) {
            String skuId2 = skuInfo.getId();
            List<PmsSkuSaleAttrValue> skuSaleAttrValueList = skuInfo.getSkuSaleAttrValueList();
            String saleAttrValueId="";
            for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : skuSaleAttrValueList) {
                saleAttrValueId+=pmsSkuSaleAttrValue.getSaleAttrValueId()+"|";
            }
            map2.put(saleAttrValueId,skuId2);
        }
        map.put("",map2);
        return "item";
    }
}
