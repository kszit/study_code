package org.stu_dataValidator.hibernateValitator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class TestMain {

	public static void main(String[] a){
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		
		Person person = new Person(null, 20);
		Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);
		System.out.println(constraintViolations);
		
	}
}
