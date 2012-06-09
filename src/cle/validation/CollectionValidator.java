package cle.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import cle.domain.Collection;
@Component
public class CollectionValidator {
	public void validate(Collection collection, Errors errors) {
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "user", "user.empty", "Cannot be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "description.empty", "Cannot be empty" );
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "title.empty", "Cannot be empty");
	
		

	

	}
}
