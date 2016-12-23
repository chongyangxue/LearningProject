package com.learning.Serialize.test;

import java.io.Serializable;

public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String id;

    private Integer age = null;


    public Person() {} //json序列化必须要？

    Person(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName2() {
        return name;
    }

    public void setName2(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("id=%s, name=%s", id, name);
    }


}
