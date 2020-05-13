package com.atguigu.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.PmsBaseCatalog1;
import com.atguigu.gmall.bean.PmsBaseCatalog2;
import com.atguigu.gmall.bean.PmsBaseCatalog3;
import com.atguigu.gmall.manage.mapper.Catalog1Mapper;
import com.atguigu.gmall.manage.mapper.Catalog2Mapper;
import com.atguigu.gmall.manage.mapper.Catalog3Mapper;
import com.atguigu.gmall.service.CatalogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;

@Service
public class CatalogServiceImpl implements CatalogService {
    @Autowired
    private Catalog1Mapper catalog1Mapper;
    @Autowired
    private Catalog2Mapper catalog2Mapper;
    @Autowired
    private Catalog3Mapper catalog3Mapper;


    @Override
    public List<PmsBaseCatalog1> getCatalog1() {
        List<PmsBaseCatalog1> catalog1s = catalog1Mapper.selectByMap(new HashMap<>());
        return catalog1s;
    }
    @Override
    public List<PmsBaseCatalog2> getCatalog2(String catalog1Id) {
        QueryWrapper<PmsBaseCatalog2> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("catalog1_id",catalog1Id);
        List<PmsBaseCatalog2> catalog2s = catalog2Mapper.selectList(queryWrapper);
        return catalog2s;
    }
    @Override
    public List<PmsBaseCatalog3> getCatalog3(String catalog2Id) {
        QueryWrapper<PmsBaseCatalog3> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("catalog2_id",catalog2Id);
        List<PmsBaseCatalog3> catalog3s = catalog3Mapper.selectList(queryWrapper);
        return catalog3s;
    }
}
