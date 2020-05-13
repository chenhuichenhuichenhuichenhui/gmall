package com.atguigu.gmall.user.controller;

import com.atguigu.gmall.bean.UmsMember;
import com.atguigu.gmall.bean.UmsMemberReceiveAddress;

import com.atguigu.gmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getAllUser")
    public Object getAllUser(){
        List<UmsMember> list =  userService.findAllUser();
        return list;
    }

    @GetMapping("/getAddressByMember")
    public Object getAddressByMember(@RequestParam String memeberId){
        List<UmsMemberReceiveAddress> list =  userService.getAddressByMember(memeberId);
        return list;
    }



}
