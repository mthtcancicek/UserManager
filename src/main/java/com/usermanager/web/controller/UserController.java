package com.usermanager.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.usermanager.core.services.UserService;
import com.usermanager.events.user.AllUsersEvent;
import com.usermanager.events.user.DeleteUserEvent;
import com.usermanager.events.user.RequestAllUsersEvent;
import com.usermanager.events.user.SaveUserEvent;
import com.usermanager.events.user.UpdateUserEvent;
import com.usermanager.events.user.UserDetails;
import com.usermanager.web.domain.User;
import com.usermanager.web.validation.CaptchaValidator;
import com.usermanager.web.validation.group.Save;
import com.usermanager.web.validation.group.Update;

/**
 * Simple User controller that supports Adding, Updating, Deleting operations and getting all user list.
 * 
 * Also does captcha validation and supports partial rendering for AJAX requests which reduces number of request/responses 
 * and data transfer between client and server.
 *  
 * @author MITHATCC
 *
 */
@Controller
@RequestMapping("/")
public class UserController {

	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
	private static final String HOME_VIEW = "/home";
	private static final String USERS_FRAGMENT = "/users :: userList";
	private static final String USER_FORM_FRAGMENT = "/home :: userModalForm";
	private static final String USER_UPDATE_FORM_FRAGMENT = "/home :: divUpdateForm"; //TODO manage all from one place.

	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CaptchaValidator captchaValidator;
	
	@RequestMapping(method = RequestMethod.GET)
	public String getCurrentUsers(Model model,User user) {
		LOG.debug("UserManager CurrentUsers to home view");
		model.addAttribute("users",getUsers(userService.requestAllUsers(new RequestAllUsersEvent())));
		return HOME_VIEW;
	}
	
    @RequestMapping(value = "/users", method = RequestMethod.GET) 	
    public String getCurrentUsersFragment(Model model) {
    	//TODO debug  	
    	LOG.debug("UserManager CurrentUsers to home view");
		model.addAttribute("users",getUsers(userService.requestAllUsers(new RequestAllUsersEvent())));
		return USERS_FRAGMENT;
    }
			
	private List<User> getUsers(AllUsersEvent requestAllUsers) {
		List<User> users = new ArrayList<User>();
		
		for (UserDetails userDetails : requestAllUsers.getUserDetails() ) {
			users.add(User.fromUserDetails(userDetails));
		}

		return users;
	}
	
	/**
	 * AJAX Response for partial rendering support.
	 * @param response
	 * @param renderDOMElement
	 * @param valid
	 */
	private void setAjaxResponse(HttpServletResponse response, String renderDOMElement, boolean valid) {
		response.setHeader("partial-render-div",renderDOMElement);
		response.setHeader("ajax-form-is-valid",String.valueOf(valid));
	}
	
	
	@RequestMapping(value="/user/save",method=RequestMethod.POST)
	public String validateUserSaveForm(Model model, @ModelAttribute("user") @Validated(Save.class) User user ,BindingResult result, HttpServletResponse response ){
		if(!result.hasErrors()){
			
			captchaValidator.validate(user, result);
			
			if(!result.hasErrors()){
				setAjaxResponse(response,"#userList",true);
				
				userService.saveUser(new SaveUserEvent(user.toUserDetails()));
				
				model.addAttribute("users",getUsers(userService.requestAllUsers(new RequestAllUsersEvent())));
				
				return USERS_FRAGMENT;		
			}
		}
		setAjaxResponse(response,"#userModalForm",false);
		
		return USER_FORM_FRAGMENT;
	}
	
	@RequestMapping(value="/user/update",method=RequestMethod.POST)
	public String updateUser(Model model, @ModelAttribute("user") @Validated(Update.class) User user ,BindingResult result, HttpServletResponse response){
		if(!result.hasErrors()){
			
			userService.updateUser(new UpdateUserEvent(user.toUserDetails()));
			
			model.addAttribute("users",getUsers(userService.requestAllUsers(new RequestAllUsersEvent())));
			
			setAjaxResponse(response,"#userList",true);
			
			return USERS_FRAGMENT;
			
		}else{
			
			setAjaxResponse(response,"#divUpdateForm",false);
			
			return USER_UPDATE_FORM_FRAGMENT;
		}
	}	
	
	@RequestMapping(value = "/user/delete", method=RequestMethod.POST)
	public String remove(Model model, @ModelAttribute("user") User user, HttpServletResponse response) {
	  	//TODO Validation
    	DeleteUserEvent deleteUserEvent = new DeleteUserEvent(user.getId());
		userService.deleteUser(deleteUserEvent);
		
		setAjaxResponse(response,"#userList",true);
		
		model.addAttribute("users",getUsers(userService.requestAllUsers(new RequestAllUsersEvent())));
		
		return USERS_FRAGMENT;
	}
}
