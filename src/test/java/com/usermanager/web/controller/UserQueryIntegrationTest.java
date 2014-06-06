package com.usermanager.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import com.usermanager.core.services.UserService;
import com.usermanager.event.fixtures.UserDetailsFixture;
import com.usermanager.events.user.AllUsersEvent;
import com.usermanager.events.user.RequestAllUsersEvent;

public class UserQueryIntegrationTest {

	private MockMvc mockMvc;
	private static final String USERS_FRAGMENT = "/users :: userList";
	
	@InjectMocks
	UserController controller;
	
	@Mock
	UserService userService;	

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
	
	@SuppressWarnings("unchecked")
	@Test
	public void getHome() throws Exception {					
		mockMvc.perform(get("/"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(model().size(2))
		.andExpect(model().attribute("users", hasSize(2)))
		.andExpect(model().attribute("users", hasItems(hasProperty("name", is(UserDetailsFixture.adamWalker().getName())),
															hasProperty("name", is(UserDetailsFixture.billyJoe().getName())) )))
		.andExpect(model().attributeExists("user"));
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
