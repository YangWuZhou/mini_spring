package com.yang.springframework.bean;

import lombok.Data;

@Data
public class UserService {
    private String name;
    private UserDao userDao;
    private String location;
    private String company;

    public void queryUserInfo(String id) {
        System.out.println("用户名: " + userDao.queryUserName(id) + ", 别名: " + name + ", 地址: " + location + ", 公司: " + company);
    }
}
