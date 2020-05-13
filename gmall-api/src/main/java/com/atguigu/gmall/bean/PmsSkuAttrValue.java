package com.atguigu.gmall.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
@Data
@Accessors(chain = true)
public class PmsSkuAttrValue implements Serializable {

    @TableId(value="id",type= IdType.AUTO)
    String id;
    String attrId;
    String valueId;
    String skuId;
}
