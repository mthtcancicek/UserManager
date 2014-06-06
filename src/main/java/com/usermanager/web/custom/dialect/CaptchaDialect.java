package com.usermanager.web.custom.dialect;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.IProcessor;

import com.usermanager.web.custom.processor.RecaptchaElementProcessor;
/**
 * Captcha Dialect
 * @author MITHATCC
 *
 */
public class CaptchaDialect extends AbstractDialect {

	//
	// All of this dialect's attributes and/or tags
	// will start with 'capctha:'
	//
	public String getPrefix() {
		return "captcha";
	}
	
	//
	// The processors.
	//
	@Override
	public Set<IProcessor> getProcessors() {
		final Set<IProcessor> processors = new HashSet<IProcessor>();
		processors.add(new RecaptchaElementProcessor());
		return processors;
	}
}