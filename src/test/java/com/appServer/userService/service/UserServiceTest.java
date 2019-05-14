package com.appServer.userService.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
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
	
	/*@Before
    public void beforeEachTestMethod() {
        userRepo.deleteAll();
    }*/
	
	@Test
	public void testCreateUser() {
		UserDTO user = new UserDTO();
		user.setFirstName("Kiran");
		user.setLastName("Binwade");
		userService.createUser(user);
		
		String fName = "";
		
		for(User u : userRepo.findAll()){
			if(u.getFirstName().equals("Kiran")){
				fName = u.getFirstName();
			}
		}
		Assert.assertEquals("Kiran", fName);
	}
	
	@Test
	public void testGetUser(){
		UserDTO user = new UserDTO();
		user.setFirstName("Sharvi");
		user.setLastName("Binwade");
		user = userService.createUser(user);
		UserDTO user2 = userService.getUserById(user.getId());
		Assert.assertEquals(user.getId(), user2.getId());
	}
	
	@Test
	public void testUpdateUser(){
		UserDTO user = new UserDTO();
		user.setFirstName("Sharvi");
		user.setLastName("Binwade");
		user = userService.createUser(user);
		user.setFirstName("Amol");
		UserDTO user2 = userService.updateUser(user);
		Assert.assertEquals("Amol", user2.getFirstName());
	}
	
	@Test
	public void testGetUsers(){
		UserDTO user = new UserDTO();
		user.setFirstName("Sharvi");
		user.setLastName("Binwade");
		user = userService.createUser(user);
		
		UserDTO user2 = new UserDTO();
		user2.setFirstName("Amol");
		user2.setLastName("Binwade");
		user2 = userService.createUser(user2);
		
		UserDTO user3 = new UserDTO();
		user3.setFirstName("Shweta");
		user3.setLastName("Binwade");
		user3 = userService.createUser(user3);
		
		List<UserDTO> users = userService.getUsers();
		
		Assert.assertEquals(3, users.size());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDeleteUser(){
		UserDTO user = new UserDTO();
		user.setFirstName("Sharvi");
		user.setLastName("Binwade");
		user = userService.createUser(user);
		userService.deleteUser(user.getId());
		userService.getUserById(user.getId());
	}

}
