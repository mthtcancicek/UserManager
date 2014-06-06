package com.usermanager.web.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.usermanager.web.captcha.CaptchaChecker;
public class CaptchaValidator implements Validator {

	@Autowired
	CaptchaChecker captchaChecker;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return CaptchaChecker.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object obj, Errors e) {
        
        //No need to check captcha if form is already not valid
        if (e.hasErrors()) {
        	return;
        }
        else {            
    		if (!captchaChecker.isValid()) {
            	e.reject("5001");    			
    		}

        }
	}

}
