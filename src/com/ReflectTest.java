package com;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class ReflectTest{
	public String username = "hallo";
	public String password = "basketball";
	public static void main(String[] args) throws Exception {
		ReflectTest t = new ReflectTest();
		t.setFieldValue(t, "username", "password");
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void getField(Object obj) {
		Field[] fields = obj.getClass().getFields();
		for(Field field : fields) {
			if(field.getType() == String.class) {
				try {
					System.out.print("old field: " + field.get(obj));
					String oldString = (String)field.get(obj);
					String newString = oldString.replace('a', 'b');
					field.set(obj, newString);
					System.out.println("  new field: " + field.get(obj));

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void setFieldValue(Object obj, String ... str) throws Exception {
		Field field1 = obj.getClass().getField(str[0]);
		Field field2 = obj.getClass().getField(str[1]);
		field1.set(obj, "xuechongyang");
		field2.set(obj, "090017");

		Field[] fields = obj.getClass().getFields();
		for(Field field : fields) {
			System.out.println(field.get(obj));
//			Method method1 = obj.getClass().getMethod("getUsername");
//			System.out.println(method1.invoke(obj));
		}
	}
}
