package com.atguigu.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.PmsProductImage;
import com.atguigu.gmall.bean.PmsProductInfo;
import com.atguigu.gmall.bean.PmsProductSaleAttr;
import com.atguigu.gmall.bean.PmsProductSaleAttrValue;
import com.atguigu.gmall.manage.mapper.PmsProductImageMapper;
import com.atguigu.gmall.manage.mapper.PmsProductSaleAttrMapper;
import com.atguigu.gmall.manage.mapper.PmsProductSaleAttrValueMapper;
import com.atguigu.gmall.manage.mapper.ProductInfoMapper;
import com.atguigu.gmall.service.SpuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    private ProductInfoMapper productInfoMapper;
    @Autowired
    private PmsProductSaleAttrMapper pmsProductSaleAttrMapper;
    @Autowired
    private PmsProductSaleAttrValueMapper pmsProductSaleAttrValueMapper;
    @Autowired
    private PmsProductImageMapper pmsProductImageMapper;

    @Override
    public List<PmsProductInfo> spuList(String cataLog3Id) {
        QueryWrapper<PmsProductInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cataLog3_id",cataLog3Id);
        List<PmsProductInfo> productInfos = productInfoMapper.selectList(queryWrapper);
        return productInfos;
    }

    @Override
    public String save(PmsProductInfo pmsProductInfo) {
        //保存商品信息
        productInfoMapper.insert(pmsProductInfo);
        //保存销售属性信息
        List<PmsProductSaleAttr> pmsProductSaleAttrList = pmsProductInfo.getSpuSaleAttrList();
        for (PmsProductSaleAttr pmsProductSaleAttr : pmsProductSaleAttrList) {
            pmsProductSaleAttr.setProductId(pmsProductInfo.getId());
            pmsProductSaleAttrMapper.insert(pmsProductSaleAttr);
            //保存销售属性值的信息
            List<PmsProductSaleAttrValue> pmsProductSaleAttrValueList = pmsProductSaleAttr.getSpuSaleAttrValueList();
            for (PmsProductSaleAttrValue pmsProductSaleAttrValue : pmsProductSaleAttrValueList) {
                pmsProductSaleAttrValue.setProductId(pmsProductInfo.getId());
                pmsProductSaleAttrValue.setSaleAttrId(pmsProductSaleAttr.getSaleAttrId());
                pmsProductSaleAttrValueMapper.insert(pmsProductSaleAttrValue);
            }
        }
        //保存商品图片信息
        List<PmsProductImage> pmsProductImageList = pmsProductInfo.getSpuImageList();
        for (PmsProductImage pmsProductImage : pmsProductImageList) {
            pmsProductImage.setProductId(pmsProductInfo.getId());
            pmsProductImageMapper.insert(pmsProductImage);
        }

        return "success";
    }

    @Override
    public List<PmsProductImage> spuImageList(String spuId) {
        QueryWrapper<PmsProductImage> query = new QueryWrapper<>();
        query.eq("product_id",spuId);
        List<PmsProductImage> pmsProductImages = pmsProductImageMapper.selectList(query);
        return pmsProductImages;
    }

    @Override
    public List<PmsProductSaleAttr> selectSpuSaleAttrListCheckBySku(String productId,String skuId) {
        //查询该商品得销售属性
//        QueryWrapper<PmsProductSaleAttr> query = new QueryWrapper<>();
//        query.eq("product_id",productId);
//        List<PmsProductSaleAttr> pmsProductSaleAttrs = pmsProductSaleAttrMapper.selectList(query);
//        //封装该商品每个销售属性得值
//        for (PmsProductSaleAttr pmsProductSaleAttr : pmsProductSaleAttrs) {
//            QueryWrapper<PmsProductSaleAttrValue> queryValue = new QueryWrapper<>();
//            queryValue.eq("sale_attr_id",pmsProductSaleAttr.getSaleAttrId());
//            queryValue.eq("product_id",productId);
//            List<PmsProductSaleAttrValue> pmsProductSaleAttrValues = pmsProductSaleAttrValueMapper.selectList(queryValue);
//            pmsProductSaleAttr.setSpuSaleAttrValueList(pmsProductSaleAttrValues);
//        }
        List<PmsProductSaleAttr> pmsProductSaleAttrs =  pmsProductSaleAttrMapper.selectSpuSaleAttrListCheckBySku(productId,skuId);
        return pmsProductSaleAttrs;
    }
}
