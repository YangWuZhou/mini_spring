package com.yang.springframework.bean;

public class UserService {
    private String name;
    private int age;

    public UserService(String name) {
        this.name = name;
    }

    public UserService(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void queryUserInfo() {
        System.out.println("查询用户信息" + name + ": " + age);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
