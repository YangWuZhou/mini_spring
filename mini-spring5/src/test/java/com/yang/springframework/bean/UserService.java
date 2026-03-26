package com.yang.springframework.bean;

public class UserService {
    private String name;
    private UserDao userDao;

    public void queryUserInfo(String id) {
        System.out.println("查询用户信息" + name + ": " + userDao.queryUserName(id));
    }


}
