package Serializable;

import java.io.Serializable;

public class Person implements Serializable {
	private static final long serialVersionUID = -7477500309047780509L;
	private int id;
	private String name;
	private int age;
	
	Person(int id, String name, int age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
