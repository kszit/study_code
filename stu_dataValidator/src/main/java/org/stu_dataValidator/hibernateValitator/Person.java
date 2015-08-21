package org.stu_dataValidator.hibernateValitator;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Person {
	@NotNull
	private String name;
	@Min(value = 1)
	private int age;
	

	@Max(100)
	public int getAge() {
		return age;
	}

	@Max(50)
	public int getAgeOther() {
		return age + 2;
	}

	@Max(50)
	public int getAgeOther(int num) {
		return 51;
	}

	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}
}