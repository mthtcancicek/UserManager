package com.usermanager.web.custom.processor;
import java.util.List;

import org.thymeleaf.Arguments;
import org.thymeleaf.TemplateRepository;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.processor.element.AbstractMarkupSubstitutionElementProcessor;

/**
 * 
 * @author MITHATCC
 * Processor for Recaptcha element.
 *
 */
public class RecaptchaElementProcessor extends AbstractMarkupSubstitutionElementProcessor {

	public RecaptchaElementProcessor() {
        super("recaptcha");
    }

    public int getPrecedence() {
        return 1000;
    }

    /**
     * Markup for building recaptcha.
     */
    @Override
    protected List<Node> getMarkupSubstitutes(final Arguments arguments, final Element element) {

    	StringBuilder sbuilder = new StringBuilder();
    	sbuilder.append("<div id=\"recaptchaDiv\"/>"); //TODO Manage from one place.   	
    	sbuilder.append("<script type=\"text/javascript\" src=\"/UserManager/resources/js/Recaptcha.js\"/>"); //TODO Path..
    	String text = sbuilder.toString();
        
        final TemplateRepository templateRepository = arguments.getTemplateRepository();
        final List<Node> fragNodes = templateRepository.getFragment(arguments, text);
        
        return fragNodes;

    }

}