package com.yang.springframework.bean;

public class UserService {
    private String name;
    private IUserDao userDao;
    private String location;
    private String company;

    public void queryUserInfo(String id) {
        System.out.println("用户名: " + userDao.queryUserName(id) + ", 别名: " + name + ", 地址: " + location + ", 公司: " + company);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IUserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
