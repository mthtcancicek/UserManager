package com.usermanager.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static com.usermanager.event.fixtures.UserDetailsFixture.billyJoe;

import com.usermanager.core.services.UserService;
import com.usermanager.event.fixtures.UserDetailsFixture;
import com.usermanager.events.user.AllUsersEvent;
import com.usermanager.events.user.DeleteUserEvent;
import com.usermanager.events.user.RequestAllUsersEvent;
import com.usermanager.events.user.SaveUserEvent;
import com.usermanager.events.user.UpdateUserEvent;
import com.usermanager.web.validation.CaptchaValidator;

public class UserCommandIntegrationTest {

	private MockMvc mockMvc;
	private static final String USERS_FRAGMENT = "/users :: userList";
	private static final String USER_FORM_FRAGMENT = "/home :: userModalForm";
	private static final String MOCK_RECAPTCHA_CHALLENGE_FIELD = "123456";
	private static final String MOCK_RECAPTCHA_RESPONSE_FIELD = "123456";
	private static final String USER_UPDATE_FORM_FRAGMENT = "/home :: divUpdateForm";
	
	@InjectMocks
	UserController controller;
	
	@Mock
	UserService userService;	
	
	@Mock
	CaptchaValidator captchaValidator;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		mockMvc = standaloneSetup(controller)
				.setViewResolvers(viewResolver())
				.build();
		
		when(userService.requestAllUsers(any(RequestAllUsersEvent.class))).thenReturn(
				new AllUsersEvent(Arrays.asList(
						UserDetailsFixture.adamWalker()	, UserDetailsFixture.billyJoe()
								  ))); 						
	}
	
	private InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
	
	@Test
	public void userValidationForSave() throws Exception {	
		
		mockMvc.perform(
				post("/user/save")) //No form inputs posted to test validation
				.andDo(print())
				.andExpect(view().name(USER_FORM_FRAGMENT))
				.andExpect(status().isOk())				
				.andExpect(model().size(1))
				.andExpect(model().errorCount(3))
				.andExpect(model().attributeHasFieldErrors("user", "userName"))
				.andExpect(model().attributeHasFieldErrors("user", "name"))
				.andExpect(model().attributeHasFieldErrors("user", "surname"))
				;		
	}	

	@Test
	public void saveUser() throws Exception {		
		
		mockMvc.perform(
				post("/user/save").param("userName", billyJoe().getUserName())
									.param("name", billyJoe().getName())
									.param("surname", billyJoe().getSurname())
									.param("recaptcha_challenge_field", MOCK_RECAPTCHA_CHALLENGE_FIELD)
									.param("recaptcha_response_field", MOCK_RECAPTCHA_RESPONSE_FIELD)
									)
				.andDo(print())
				.andExpect(view().name(USERS_FRAGMENT))
				.andExpect(status().isOk())
				.andExpect(model().size(2))
				.andExpect(model().attributeExists("user"))
				.andExpect(model().attributeExists("users"))
				;
		
		ArgumentCaptor<SaveUserEvent> argument = ArgumentCaptor.forClass(SaveUserEvent.class);
		verify(userService).saveUser(argument.capture());
		assertEquals(billyJoe().getUserName(), argument.getValue().getDetails().getUserName());
		assertEquals(billyJoe().getName(), argument.getValue().getDetails().getName());
		assertEquals(billyJoe().getSurname(), argument.getValue().getDetails().getSurname());
	}
	
	@Test
	public void userValidationForUpdate() throws Exception {	
		
		mockMvc.perform(
				post("/user/update")) //No form inputs posted to test validation
				.andDo(print())
				.andExpect(view().name(USER_UPDATE_FORM_FRAGMENT))
				.andExpect(status().isOk())				
				.andExpect(model().size(1))
				.andExpect(model().errorCount(3))
				.andExpect(model().attributeHasFieldErrors("user", "userName"))
				.andExpect(model().attributeHasFieldErrors("user", "name"))
				.andExpect(model().attributeHasFieldErrors("user", "surname"))
				;		
	}
	
	@Test
	public void updateUser() throws Exception {		
		
		mockMvc.perform(
				post("/user/update").param("userName", billyJoe().getUserName())
									.param("name", billyJoe().getName())
									.param("surname", billyJoe().getSurname())
									.param("recaptcha_challenge_field", MOCK_RECAPTCHA_CHALLENGE_FIELD)
									.param("recaptcha_response_field", MOCK_RECAPTCHA_RESPONSE_FIELD)
									)
				.andDo(print())
				.andExpect(view().name(USERS_FRAGMENT))
				.andExpect(status().isOk())
				.andExpect(model().size(2))
				.andExpect(model().attributeExists("user"))
				.andExpect(model().attributeExists("users"))
				;
		
		ArgumentCaptor<UpdateUserEvent> argument = ArgumentCaptor.forClass(UpdateUserEvent.class);
		verify(userService).updateUser(argument.capture());
		assertEquals(billyJoe().getUserName(), argument.getValue().getDetails().getUserName());
		assertEquals(billyJoe().getName(), argument.getValue().getDetails().getName());
		assertEquals(billyJoe().getSurname(), argument.getValue().getDetails().getSurname());
	}	
	
	@Test
	public void deleteUser() throws Exception {		
		
		mockMvc.perform(
				post("/user/delete").param("id", billyJoe().getId()))
				.andDo(print())
				.andExpect(view().name(USERS_FRAGMENT))
				.andExpect(status().isOk())
				.andExpect(model().size(2))
				.andExpect(model().attributeExists("user"))
				.andExpect(model().attributeExists("users"))
				;
		
		ArgumentCaptor<DeleteUserEvent> argument = ArgumentCaptor.forClass(DeleteUserEvent.class);
		verify(userService).deleteUser(argument.capture());
		assertEquals(billyJoe().getId(), argument.getValue().getId());
	}		
	
	@SuppressWarnings("unchecked")
	@Test
	public void getUsersFragment() throws Exception {
		mockMvc.perform(get("/users"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(view().name(USERS_FRAGMENT))
		.andExpect(model().size(1))
		.andExpect(model().attribute("users", hasSize(2)))
		.andExpect(model().attribute("users", hasItems(hasProperty("name", is(UserDetailsFixture.adamWalker().getName())),
															hasProperty("name", is(UserDetailsFixture.billyJoe().getName())) )));
	}

}
