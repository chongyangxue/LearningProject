package Serializable;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class ReadObject {
	public static void main(String[] args) {
		ObjectInputStream ois = null;
		Person p = null;
		try{
			ois = new ObjectInputStream(new FileInputStream("E:/Object.txt"));
			p = (Person)ois.readObject();
		}catch (Exception e){
			e.printStackTrace();
		}
		System.out.println(p.getId() + "_" + p.getName() + "_" + p.getAge());
		
	}
}
