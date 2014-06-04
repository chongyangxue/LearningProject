package Serializable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class WriteObject {
	public static void main(String[] args) {
		ObjectOutputStream oos = null;
		Person p = new Person(1, "Jack", 20);
		try{
			oos = new ObjectOutputStream(new FileOutputStream("E:/Object.txt"));
			oos.writeObject(p);
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			if(oos != null) {
				try{
					oos.close();
				}catch (IOException e){
					e.printStackTrace();
				}
			}
		}
	}
}
