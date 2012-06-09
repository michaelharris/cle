package cle.validation;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;


import cle.domain.Module;
@Component
public class ModuleValidator {
	public void validate(Module module, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tags", "tags.empty",
				"Cannot be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description",
				"description.empty", "Cannot be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title",
				"title.empty", "Cannot be empty");

	}
}
