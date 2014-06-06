package com.usermanager.web.captcha;

import javax.servlet.http.HttpServletRequest;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Google Recaptcha implementation..
 * 
 * @author MITHATCC
 *
 */
public class ReCaptchaChecker implements CaptchaChecker{

	@Override
	public boolean isValid() {
    	HttpServletRequest request = ((ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes())).getRequest();
    	
    	String remoteAddr = request.getRemoteAddr();
    	
    	String recaptchaChallengeField = request.getParameter("recaptcha_challenge_field");
    	String recaptchaResponseField = request.getParameter("recaptcha_response_field");
    	
        ReCaptchaImpl reCaptcha = new ReCaptchaImpl();        	
    	
        //any key works on localhost for development
        reCaptcha.setPrivateKey("6LepdPQSAAAAAFBKQSk5CCuMDA3aLYB-kTqbZZSO");

        ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, recaptchaChallengeField, recaptchaResponseField);
        return reCaptchaResponse.isValid();
	}
}
