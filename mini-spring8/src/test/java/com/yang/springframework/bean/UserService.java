package com.yang.springframework.bean;

import com.yang.springframework.beans.factory.DisposableBean;
import com.yang.springframework.beans.factory.InitializingBean;
import lombok.Data;

@Data
public class UserService implements InitializingBean, DisposableBean {
    private String name;
    private UserDao userDao;
    private String location;
    private String company;

    public void queryUserInfo(String id) {
        System.out.println("用户名: " + userDao.queryUserName(id) + ", 别名: " + name + ", 地址: " + location + ", 公司: " + company);
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("UserService destroy");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("UserService init");
    }
}
