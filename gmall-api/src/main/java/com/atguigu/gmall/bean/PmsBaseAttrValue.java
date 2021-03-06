package com.atguigu.gmall.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;


import java.io.Serializable;

/**
 * @param
 * @return
 */
@Data
@Accessors(chain = true)
public class PmsBaseAttrValue implements Serializable {

    @TableId(value="id",type= IdType.AUTO)
    private String id;
    private String valueName;
    private String attrId;
    private String isEnabled;

    @TableField(exist = false)
    private String urlParam;


}
