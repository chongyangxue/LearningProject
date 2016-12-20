package com.learning.ClassLoader;

import java.util.Date;

/**
 * 
 * BootStrap加载JRE/lib/rt.jar
 * ExtClassLoader加载jre/lib/ext/*.jar
 * AppClassLoader加载CLASSPATH指定的所有jar
 * @author Sachiel
 *
 */
public class ClassLoaderTest {
	public static void main(String[] args) {
		System.out.println(ClassLoaderTest.class.getClassLoader().getClass().getName());
		System.out.println(System.class.getClassLoader());
		
		ClassLoader loader = ClassLoaderTest.class.getClassLoader();
		while(loader != null) {
			System.out.println(loader.getClass().getName());
			loader = loader.getParent();
		}
		System.out.println(loader);
		
		try{
			Class clazz = new MyClassLoader("classLoaderLib").loadClass("AttachmentClass");
			Date d = (Date)clazz.newInstance();
			System.out.println(d);
		}catch (Exception e){
			e.printStackTrace();
		}
		
	}
}
