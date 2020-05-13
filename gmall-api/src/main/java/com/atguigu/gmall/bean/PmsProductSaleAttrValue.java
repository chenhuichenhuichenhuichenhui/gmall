package com.atguigu.gmall.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
@Data
@Accessors(chain = true)
public class PmsProductSaleAttrValue implements Serializable {
    @TableId(value="id",type= IdType.AUTO)
    String id ;
    String productId;
    String saleAttrId;
    String saleAttrValueName;

    @TableField(exist = false)
    String isChecked;

}
