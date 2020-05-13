package com.atguigu.gmall.manage.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.bean.PmsSkuAttrValue;
import com.atguigu.gmall.bean.PmsSkuImage;
import com.atguigu.gmall.bean.PmsSkuInfo;
import com.atguigu.gmall.bean.PmsSkuSaleAttrValue;
import com.atguigu.gmall.manage.mapper.PmsShuInfoMapper;
import com.atguigu.gmall.manage.mapper.PmsSkuAttrValueMapper;
import com.atguigu.gmall.manage.mapper.PmsSkuImageMapper;
import com.atguigu.gmall.manage.mapper.PmsSkuSaleAttrValueMapper;
import com.atguigu.gmall.service.PmsSkuInfoService;
import com.atguigu.gmall.util.RedisUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.UUID;

@Service
public class PmsSkuInfoServiceImpl implements PmsSkuInfoService {
    @Autowired
    private PmsShuInfoMapper pmsShuInfoMapper;
    @Autowired
    private PmsSkuAttrValueMapper pmsSkuAttrValueMapper;
    @Autowired
    private PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;
    @Autowired
    private PmsSkuImageMapper pmsSkuImageMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void saveSkuInfo(PmsSkuInfo pmsSkuInfo) {
        pmsShuInfoMapper.insert(pmsSkuInfo);
        //报存shu关联得平台属性
        List<PmsSkuAttrValue> pmsSkuAttrValueList = pmsSkuInfo.getSkuAttrValueList();
        for (PmsSkuAttrValue pmsSkuAttrValue : pmsSkuAttrValueList) {
            pmsSkuAttrValue.setSkuId(pmsSkuInfo.getId());
            pmsSkuAttrValueMapper.insert(pmsSkuAttrValue);
        }
        //保存sku关联的销售属性值
        List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValueList = pmsSkuInfo.getSkuSaleAttrValueList();
        for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : pmsSkuSaleAttrValueList) {
            pmsSkuSaleAttrValue.setSkuId(pmsSkuInfo.getId());
            pmsSkuSaleAttrValueMapper.insert(pmsSkuSaleAttrValue);
        }
        //保存sku关联的图片
        List<PmsSkuImage> pmsSkuImageList = pmsSkuInfo.getSkuImageList();
        for (PmsSkuImage pmsSkuImage : pmsSkuImageList) {
            pmsSkuImage.setSkuId(pmsSkuInfo.getId());
            pmsSkuImageMapper.insert(pmsSkuImage);
        }

    }
    public PmsSkuInfo PmsSkuInfoFromDB(String skuId) {
        //sku商品的信息
        PmsSkuInfo pmsSkuInfo = pmsShuInfoMapper.selectById(skuId);
        //sku商品的图片信息
        QueryWrapper<PmsSkuImage> query = new QueryWrapper<>();
        query.eq("sku_id",skuId);
        List<PmsSkuImage> pmsSkuImages = pmsSkuImageMapper.selectList(query);
        pmsSkuInfo.setSkuImageList(pmsSkuImages);
        return pmsSkuInfo;
    }

    @Override
    public PmsSkuInfo getSkuById(String skuId) {

        PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();

        //连接缓存
        Jedis jedis = redisUtil.getJedis();

        //查询缓存
        String skuJson = jedis.get("sku:" + skuId + ":info");
        if(StringUtils.isNotBlank(skuJson)){
            //缓存中有数据
            pmsSkuInfo = JSON.parseObject(skuJson,PmsSkuInfo.class);
        }else{
            //如果缓存没有，查询mysql

            //设置分布式锁
            String token = UUID.randomUUID().toString();
            String OK = jedis.set("sku:" + skuId + ":lock", token, "nx", "px", 10*1000);
            if(StringUtils.isNotBlank(OK)&& "OK".equals(OK)){
                //没有其他线程访问数据库
                pmsSkuInfo = PmsSkuInfoFromDB(skuId);
                //mysql查询结果存入redis
                if(pmsSkuInfo!=null) {
                    //如果数据库数据存在
                    jedis.set("sku:" + skuId + ":info", JSON.toJSONString(pmsSkuInfo));
                }else{
                    //数据库中不存在该sku
                    //为了防止缓存穿透 将null或者空字符串值设置给redis
                    jedis.setex("sku:" + skuId + ":info",60*3,"");
                }
                //在访问mygsql后，将mysql的分布式锁释放掉 进行验证是删除的自己set的锁
                String val = jedis.get("sku:" + skuId + ":lock");
                if(StringUtils.isNotBlank(val) && token.equals(val)){
                    //jedis.eval("lua");//可与用lua脚本，在查询到key的同时删除该key，防止高并发下的意外的发生
                    jedis.del("sku:" + skuId + ":lock");
                }
            }else{
                //有其他线程在访问数据库
                // 自旋3秒，重新访问getSkuById
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return getSkuById(skuId);
            }

        }
        jedis.close();
        return pmsSkuInfo;
    }

    @Override
    public List<PmsSkuInfo> getSkuAndAttrValueBySkuId(String productId) {
        List<PmsSkuInfo> pmsSkuInfos = pmsShuInfoMapper.selectSkuAndAttrValueBySkuId(productId);
        return pmsSkuInfos;
    }
}
