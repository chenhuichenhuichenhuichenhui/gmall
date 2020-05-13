package com.atguigu.gmall.user.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.bean.UmsMember;
import com.atguigu.gmall.bean.UmsMemberReceiveAddress;
import com.atguigu.gmall.service.UserService;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private static Logger logger =  LoggerFactory.getLogger(UserController.class);

    @Reference
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

    public static void main(String[] args) {
        Map<String,Object> map = new HashMap<>();
        map.put("auditResult","AGREE");
        map.put("adjustCode", "KCTZD2020042600004");
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyTmFtZSI6IuW8oOiNo-elpSIsImV4cCI6MTU4ODE1NTk1OCwidXNlcklkIjoiMDAwMDAwMjEiLCJpYXQiOjE1ODgxNTQxNTgsImp0aSI6IjAwMDAwMDIxIn0.eShWkIdlkqX1fIhk2uNlRzn2BifuAToL0ZAW0Rd1sJw";
        String url = "http://127.0.0.1:8112/api/v1/stock/scStockAdjust/audit";

        String requestBody = JSON.toJSONString(map);
        doHttpPost(requestBody,url,token);

        //String result= HttpUtil.post("http://127.0.0.1:8112/api/v1/stock/scStockAdjust/audit", map);
       // System.out.println(result);

    }
    /**
     * 发送Http post请求
     *
     * @param xmlInfo
     *            json转化成的字符串
     * @param URL
     *            请求url
     * @return 返回信息
     */
    public static String doHttpPost(String xmlInfo, String URL,String token) {
        System.out.println("发起的数据:" + xmlInfo);
        byte[] xmlData = xmlInfo.getBytes();
        InputStream instr = null;
        java.io.ByteArrayOutputStream out = null;
        try {
            URL url = new URL(URL);
            URLConnection urlCon = url.openConnection();
            urlCon.setDoOutput(true);
            urlCon.setDoInput(true);
            urlCon.setUseCaches(false);
            urlCon.setRequestProperty("content-Type", "application/json");
            urlCon.setRequestProperty("token", token);
            urlCon.setRequestProperty("charset", "utf-8");
            urlCon.setRequestProperty("Content-length",
                    String.valueOf(xmlData.length));
            System.out.println(String.valueOf(xmlData.length));
            DataOutputStream printout = new DataOutputStream(
                    urlCon.getOutputStream());
            printout.write(xmlData);
            printout.flush();
            printout.close();
            instr = urlCon.getInputStream();
            byte[] bis = IOUtils.toByteArray(instr);
            String ResponseString = new String(bis, "UTF-8");
            if ((ResponseString == null) || ("".equals(ResponseString.trim()))) {
                System.out.println("返回空");
            }
            System.out.println("返回数据为:" + ResponseString);
            return ResponseString;

        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        } finally {
            try {
                out.close();
                instr.close();

            } catch (Exception ex) {
                return "0";
            }
        }
    }


}
