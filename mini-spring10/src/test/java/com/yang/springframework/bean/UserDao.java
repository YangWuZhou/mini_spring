package com.yang.springframework.bean;

import java.util.HashMap;
import java.util.Map;

public class UserDao {
    private static final Map<String, String> result = new HashMap<>();


    public void init() {
        System.out.println("UserDao init");
        result.put("001", "张三");
        result.put("002", "李四");
        result.put("003", "王五");
    }

    public void destroy() {
        System.out.println("UserDao destroy");
        result.clear();
    }

    public String queryUserName(String id) {
        return result.get(id);
    }
}
