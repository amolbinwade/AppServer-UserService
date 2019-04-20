package com.appServer.userService.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.appServer.userService.dto.UserDTO;
import com.appServer.userService.entity.User;
import com.appServer.userService.repository.UserRepository;

@Component
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepo;

	@Override
	@Transactional
	public void createUser(UserDTO user) {
		User u = new User();
		u.setFirstName(user.getFirstName());
		u.setLastName(user.getLastName());
		u.setMiddleName(user.getMiddleName());
		
		userRepo.save(u);
	}

	@Override
	public UserDTO getUserById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateUser(UserDTO user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteUser(Long id) {
		// TODO Auto-generated method stub

	}

}
