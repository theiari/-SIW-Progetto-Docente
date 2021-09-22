package it.uniroma3.siw.spring.controller.validator;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.Indicatore;
import it.uniroma3.siw.spring.service.IndicatoreService;


@Component
public class IndicatoreValidator implements Validator {
	@Autowired
	private IndicatoreService indicatoreService;
	
    private static final Logger logger = LoggerFactory.getLogger(IndicatoreValidator.class);

	@Override
	public void validate(Object o, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "required");
	

		if (!errors.hasErrors()) {
			logger.debug("confermato: valori non nulli");
			if (this.indicatoreService.alreadyExists((Indicatore)o)) {
				logger.debug("e' un duplicato");
				errors.reject("duplicato");
			}
		}
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return Indicatore.class.equals(aClass);
	}
}
