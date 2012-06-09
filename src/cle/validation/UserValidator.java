package cle.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;


import cle.dao.UserDao;
import cle.domain.User;
@Component
public class UserValidator {
	@Autowired(required =true)
	UserDao userDao;
	
	public void validate(User user, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "firstName.empty", "Cannot be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "lastName.empty", "Cannot be empty" );
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email.empty", "Cannot be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.empty",  "Cannot be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword",
				"confirmPassword.empty",  "Cannot be empty");
		

		if (!(user.getPassword().equals(user.getConfirmPassword()))) {
			errors.rejectValue("password", "not equal", "Passwords are not equal ");
		}
		
		if(userDao.emailExists(user.getEmail()) == true){
					
			errors.rejectValue("email", "not unique", "This email address is already registered");
		}
	}

}
