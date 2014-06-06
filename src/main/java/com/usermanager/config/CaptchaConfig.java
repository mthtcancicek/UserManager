package com.usermanager.config;

import com.usermanager.web.captcha.CaptchaChecker;
import com.usermanager.web.captcha.ReCaptchaChecker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CaptchaConfig {
	@Bean
	public CaptchaChecker captchaChecker() {
		return new ReCaptchaChecker();
	}
}
