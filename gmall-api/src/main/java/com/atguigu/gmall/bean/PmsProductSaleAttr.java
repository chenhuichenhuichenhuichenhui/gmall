package com.atguigu.gmall.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
@Data
@Accessors(chain = true)
public class PmsProductSaleAttr implements Serializable {

    @TableId(value="id",type= IdType.AUTO)
    String id ;
    String productId;
    String saleAttrId;
    String saleAttrName;
    @TableField(exist = false)
    List<PmsProductSaleAttrValue> spuSaleAttrValueList;

}
