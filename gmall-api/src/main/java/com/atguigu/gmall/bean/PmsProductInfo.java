package com.atguigu.gmall.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @param
 * @return
 */
@Data
@Accessors(chain = true)
public class PmsProductInfo implements Serializable {

    @TableId(value="id",type= IdType.AUTO)
    private String id;
    private String productName;
    private String description;
    private String catalog3Id;

    @TableField(exist = false)
    private List<PmsProductSaleAttr> spuSaleAttrList;
    @TableField(exist = false)
    private List<PmsProductImage> spuImageList;

}


