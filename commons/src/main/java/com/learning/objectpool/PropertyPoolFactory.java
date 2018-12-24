package com.learning.objectpool;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * @author xuechongyang
 */
public class PropertyPoolFactory extends BasePooledObjectFactory<Property> {

    private static GenericObjectPool<Property> pool = null;

    /**
     * 取得对象池工厂实例
     */
    public synchronized static GenericObjectPool<Property> getInstance() {
        if (pool == null) {
            GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
            poolConfig.setMaxIdle(-1);
            poolConfig.setMaxTotal(1000);
            poolConfig.setMinIdle(100);
            poolConfig.setLifo(false);
            pool = new GenericObjectPool<>(new PropertyPoolFactory(), poolConfig);
        }
        return pool;
    }

    public static Property borrowObject() throws Exception {
        return PropertyPoolFactory.getInstance().borrowObject();
    }

    public static void returnObject(Property property) throws Exception {
        PropertyPoolFactory.getInstance().returnObject(property);
    }

    public static void close() throws Exception {
        PropertyPoolFactory.getInstance().close();
    }

    public static void clear() throws Exception {
        PropertyPoolFactory.getInstance().clear();
    }

    @Override
    public Property create() throws Exception {
        return new Property();
    }

    @Override
    public PooledObject<Property> wrap(Property obj) {
        return new DefaultPooledObject<>(obj);
    }

    public static void main(String[] args) throws Exception {
        Property property = PropertyPoolFactory.borrowObject();
        property.setKey("key");
        property.setValue("value2");
        System.out.println(property.toString());
        PropertyPoolFactory.returnObject(property);
    }

}
