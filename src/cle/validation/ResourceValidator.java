package cle.validation;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.*;

import cle.domain.Module;
import cle.domain.Resource;

@Component
public class ResourceValidator {
	public void validate(Resource resource, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tags", "tags.empty", "Cannot be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "description.empty", "Cannot be empty" );
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "title.empty", "Cannot be empty");
	
		
	}
}
