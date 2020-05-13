package com.atguigu.gmall.user.mapper;

import com.atguigu.gmall.bean.UmsMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<UmsMember> {
    List<UmsMember> selectAllUser();

}
