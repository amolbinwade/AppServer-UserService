package com.appServer.userService.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.appServer.userService.UserServiceApplication;
import com.appServer.userService.dto.UserDTO;
import com.appServer.userService.entity.User;
import com.appServer.userService.repository.UserRepository;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {UserServiceApplication.class})
public class UserServiceTest {
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepo;

	@Test
	public void testCreateUser() {
		UserDTO user = new UserDTO();
		user.setFirstName("Sharvi");
		user.setLastName("Binwade");
		userService.createUser(user);
		
		String fName = "";
		
		for(User u : userRepo.findAll()){
			if(u.getFirstName().equals("Sharvi")){
				fName = u.getFirstName();
			}
		}
		Assert.assertEquals("Sharvi", fName);
	}

}
