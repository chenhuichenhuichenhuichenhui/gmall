package com.atguigu.gmall.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @param
 * @return
 */
@Data
public class PmsBaseSaleAttr implements Serializable {
    @TableId(value="id",type= IdType.AUTO)
    String id ;
    String name;
}