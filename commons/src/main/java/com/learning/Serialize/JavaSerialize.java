package com.learning.Serialize;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class JavaSerialize {

    private static final String FILE_PATH = "/opt/serialize/java";

    public static void write(Object o) {
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH));
            oos.writeObject(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void read() {
        ObjectInputStream ois;
        Object p = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(FILE_PATH));
            p = ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(p.toString());
    }
}
