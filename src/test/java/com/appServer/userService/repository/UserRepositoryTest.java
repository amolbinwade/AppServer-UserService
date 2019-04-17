package com.appServer.userService.repository;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.appServer.userService.UserServiceApplication;
import com.appServer.userService.entity.User;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {UserServiceApplication.class})
public class UserRepositoryTest {
	
	@Autowired
	UserRepository userRepo;

	@Test
	public void test() {
		User u = new User();
		u.setFirstName("Amol");
		userRepo.save(u);
		System.out.println("test done");
		
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void test2() {
		for(User u : userRepo.findAll()){
			System.out.println(u.getFirstName());
		}
		Assert.assertEquals(1, userRepo.count());
	}
	

}
