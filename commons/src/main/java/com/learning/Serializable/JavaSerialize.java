package com.learning.Serializable;

import org.junit.Test;

import java.io.*;

public class JavaSerialize {

    private static final String FILE_PATH = "/opt/serialize/javaSerializeResult_1L";

    @Test
    public void write() {
        ObjectOutputStream oos = null;
        Person p = new Person(1, "Jack", 20);
        try {
            oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH));
            oos.writeObject(p);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void read() {
        ObjectInputStream ois;
        Person p = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(FILE_PATH));
            p = (Person) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(p.toString());
    }
}
