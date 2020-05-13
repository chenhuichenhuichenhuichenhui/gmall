package com.atguigu.gmall.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PmsBaseCatalog3 implements Serializable {
    @TableId(value="id",type= IdType.AUTO)
    private String id;
    private String name;
    private String catalog2Id;
    @TableField(exist = false)
    private List<PmsBaseAttrInfo> catalog3List;
}
