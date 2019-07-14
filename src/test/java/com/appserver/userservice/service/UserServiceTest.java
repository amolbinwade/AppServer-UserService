package com.appserver.userservice.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.appServer.userService.dto.AddressDTO;
import com.appServer.userService.dto.UserDTO;
import com.appserver.userservice.UserServiceApplication;
import com.appserver.userservice.constants.UserTypeEnum;
import com.appserver.userservice.entity.User;
import com.appserver.userservice.exception.ReqDataMissingApplicationException;
import com.appserver.userservice.exception.UserApplicationException;
import com.appserver.userservice.repository.UserRepository;
import com.appserver.userservice.service.UserService;

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
		user.setEmailId("kiranbinwade@gmail.com");
		user.setUserId("kiranbinwade");
		user.setUserType(UserTypeEnum.SUPER_USER.name());
		
		AddressDTO homeAddress = new AddressDTO();
		homeAddress.setCity("Pune");
		homeAddress.setLine1("First Society");
		homeAddress.setLine2("Apartment 41");
		homeAddress.setPincode("411014");
		homeAddress.setState("Maharashtra");
		homeAddress.setCountry("India");
		
		user.setHomeAddress(homeAddress);
		userService.createUser(user);
		
		String fName = "";
		
		for(User u : userRepo.findAll()){
			if(u.getFirstName().equals("Kiran")){
				fName = u.getFirstName();
			}
		}
		Assert.assertEquals("Kiran", fName);
	}
	
	@Test(expected=ReqDataMissingApplicationException.class)
	public void testCreateUserMissingFields() {
		UserDTO user = new UserDTO();
		user.setFirstName("Kiran");
		user.setUserId("kiranbinwade");
		user.setUserType(UserTypeEnum.SUPER_USER.name());
		
		AddressDTO homeAddress = new AddressDTO();
		homeAddress.setCity("Pune");
		homeAddress.setLine1("First Society");
		homeAddress.setLine2("Apartment 41");
		homeAddress.setPincode("411014");
		homeAddress.setState("Maharashtra");
		homeAddress.setCountry("India");
		
		user.setHomeAddress(homeAddress);
		try{
		userService.createUser(user);
		} catch(ReqDataMissingApplicationException ex){
			ex.getMissingFields().forEach(field -> System.out.println(field));
			throw ex;
		}		
		
	}
	
	@Test
	public void testGetUser(){
		UserDTO user = new UserDTO();
		user.setFirstName("Sharvi");
		user.setLastName("Binwade");
		user.setEmailId("kiranbinwade@gmail.com");
		user.setUserId("kiranbinwade");
		user = userService.createUser(user);
		UserDTO user2 = userService.getUserById(user.getId());
		Assert.assertEquals(user.getId(), user2.getId());
	}
	
	@Test
	public void testUpdateUser(){
		UserDTO user = new UserDTO();
		user.setFirstName("Sharvi");
		user.setLastName("Binwade");
		user.setEmailId("kiranbinwade@gmail.com");
		user.setUserId("kiranbinwade");
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
		user.setEmailId("kiranbinwade@gmail.com");
		user.setUserId("kiranbinwade");
		user = userService.createUser(user);
		
		UserDTO user2 = new UserDTO();
		user2.setFirstName("Amol");
		user2.setLastName("Binwade");
		user2.setEmailId("kiranbinwade@gmail.com");
		user2.setUserId("kiranbinwade");
		user2 = userService.createUser(user2);
		
		UserDTO user3 = new UserDTO();
		user3.setFirstName("Shweta");
		user3.setLastName("Binwade");
		user3.setEmailId("kiranbinwade@gmail.com");
		user3.setUserId("kiranbinwade");
		user3 = userService.createUser(user3);
		
		List<UserDTO> users = userService.getUsers(0, 0);
		
		Assert.assertEquals(3, users.size());
	}
	
	@Test(expected=UserApplicationException.class)
	public void testDeleteUser(){
		UserDTO user = new UserDTO();
		user.setFirstName("Sharvi");
		user.setLastName("Binwade");
		user.setEmailId("kiranbinwade@gmail.com");
		user.setUserId("kiranbinwade");
		user = userService.createUser(user);
		userService.deleteUser(user.getId());
		userService.getUserById(user.getId());
	}
	
	@Test(expected=UserApplicationException.class)
	public void testCreateUserValidation(){
		UserDTO user = new UserDTO();
		user.setFirstName("Sharvi");
		user.setLastName("");
		user = userService.createUser(user);
	}

}
