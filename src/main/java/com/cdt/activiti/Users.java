package com.cdt.activiti;

import java.io.Serializable;

public class Users implements Serializable {
    @Override
    public String toString() {
        return "Users{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }

    private static final long serialVersionUID = 1L;

    public Users(int age, String name) {
        this.age = age;
        this.name = name;
    }

    private Integer age;
    private String name;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
