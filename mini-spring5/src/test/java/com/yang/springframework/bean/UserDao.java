package com.yang.springframework.bean;

import java.util.HashMap;
import java.util.Map;

public class UserDao {
    private static Map<String, String> result = new HashMap<>();

    static {
        result.put("001", "张三");
        result.put("002", "李四");
        result.put("003", "王五");
    }

    public String queryUserName(String id) {
        return result.get(id);
    }
}
