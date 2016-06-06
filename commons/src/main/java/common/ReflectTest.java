package common;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

public class ReflectTest {
    public String username = "hallo";

    public String password = "basketball";

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
        for (Field field : fields) {
            if (field.getType() == String.class) {
                try {
                    System.out.print("old field: " + field.get(obj));
                    String oldString = (String) field.get(obj);
                    String newString = oldString.replace('a', 'b');
                    field.set(obj, newString);
                    System.out.println("  new field: " + field.get(obj));

                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setFieldValue(Object obj, String... str) throws Exception {
        Field field1 = obj.getClass().getField(str[0]);
        Field field2 = obj.getClass().getField(str[1]);
        field1.set(obj, "xuechongyang");
        field2.set(obj, "090017");

        Field[] fields = obj.getClass().getFields();
        for (Field field : fields) {
            System.out.println(field.get(obj));
            //			Method method1 = obj.getClass().getMethod("getUsername");
            //			System.out.println(method1.invoke(obj));
        }
    }
    
/*    public void getGenericType(List<Object> list) {
        ParameterizedType parameterizedType = (ParameterizedType)list.getClass().getGenericSuperclass(); 

        Class clazz = (Class)(parameterizedType.getActualTypeArguments()[0]); 
        
        List.class.getGenericSuperclass();
        Type fc = list.get(index)getGenericType(); // 关键的地方，如果是List类型，得到其Generic的类型  
        if(fc == null) continue;  

        if(fc instanceof ParameterizedType) // 【3】如果是泛型参数的类型   
       {   
              ParameterizedType pt = (ParameterizedType) fc;  

              Class genericClazz = (Class)pt.getActualTypeArguments()[0]; //【4】 得到泛型里的class类型对象。  

              m.put(f.getName(), genericClazz);  

              Map<String, Class> m1 = prepareMap(genericClazz);  

              m.putAll(m1);   
        }   
    }*/
    
    public <T> void getTopN(List<T> list, String fieldName) {
        try {
            Class<? extends Object> clazz = list.get(0).getClass();
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            System.out.println((String) field.get(list.get(0))); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    public static void main(String[] args) throws Exception {
        ReflectTest t = new ReflectTest();
        t.setFieldValue(t, "username", "password");
        
        List<User> list= Lists.newArrayList();
        list.add(new User(1, "chongyang", "152", new Date()));
        t.getTopN(list, "username");
    }
}
