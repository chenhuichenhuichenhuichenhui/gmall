package com.atguigu.gmall.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class UmsMember implements Serializable {
    private String id;
    private String memberLevelId;
    private String username;
    private String password;
    private String nickname;
    private String phone;
    private String status;
    private String createTime;
    private String icon;
    private String gender;
    private String birthday;
    private String city;
    private String job;
    private String personalizedSignature;
    private String sourceType;
    private String integration;
    private String growth;
    private String luckeyCount;
    private String historyIntegration;


}
