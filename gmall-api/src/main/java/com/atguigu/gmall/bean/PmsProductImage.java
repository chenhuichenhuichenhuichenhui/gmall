package com.atguigu.gmall.bean;

import com.baomidou.mybatisplus.annotation.IdType;
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
public class PmsProductImage implements Serializable {

    @TableId(value="id",type= IdType.AUTO)
    private String id;
    private String productId;
    private String imgName;
    private String imgUrl;
}