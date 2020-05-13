package com.atguigu.gmall.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.PmsProductImage;
import com.atguigu.gmall.bean.PmsProductInfo;
import com.atguigu.gmall.service.SpuService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class SpuController {
    @Reference
    private SpuService spuService;

    @GetMapping("/spuList")
    public List<PmsProductInfo> spuList(String catalog3Id){
        List<PmsProductInfo> productInfos = spuService.spuList(catalog3Id);
        return productInfos;
    }
    @GetMapping("/spuImageList")
    public List<PmsProductImage> spuImageList(String spuId){
        List<PmsProductImage> pmsProductImages = spuService.spuImageList(spuId);
        return pmsProductImages;
    }

    @PostMapping("/saveSpuInfo")
    public String save(@RequestBody PmsProductInfo pmsProductInfo){
        return spuService.save(pmsProductInfo);
    }
}
