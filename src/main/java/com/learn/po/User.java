package com.learn.po;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @program: spring_template_1
 * @author: yjf
 * @create: 2019-03-22 21:54
 **/

public class User {
    private Integer id;

    private String userName;
    private  int age;

    public User(){

    }
    public User(String userName,int age){
       this.age = age;
       this.userName = userName;
    }

    public User(Integer id, String userName, int age) {
        this.id = id;
        this.userName = userName;
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
