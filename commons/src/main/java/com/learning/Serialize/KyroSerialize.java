package com.learning.Serialize;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by xuechongyang on 16/12/23.
 */
public class KyroSerialize {
    private static final String FILE_PATH = "/opt/serialize/kyro";

    public static void write(Object o) throws Exception {
        Kryo kryo = new Kryo();
//        kryo.setReferences(true);
//        kryo.setRegistrationRequired(false);
//        kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
        Output output = new Output(new FileOutputStream(FILE_PATH));
//        kryo.writeObject(output, o);
        kryo.writeClassAndObject(output, o);
        output.close();
    }

    @Test
    public void read() throws Exception {
        Kryo kryo = new Kryo();
        Input input = new Input(new FileInputStream(FILE_PATH));
//        Object obj = kryo.readObject(input, SerialTest.class);
        Object obj = kryo.readClassAndObject(input);
        System.out.println(obj.toString());
        input.close();
    }
}
