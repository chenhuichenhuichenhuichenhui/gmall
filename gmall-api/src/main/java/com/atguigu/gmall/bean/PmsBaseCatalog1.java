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
public class PmsBaseCatalog1 implements Serializable {
    @TableId(value="id",type= IdType.AUTO)
    private String id;
    private String name;

    @TableField(exist = false)
    private List<PmsBaseCatalog2> catalog2s;

}

