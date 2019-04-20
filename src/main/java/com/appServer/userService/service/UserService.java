package com.appServer.userService.service;

import com.appServer.userService.dto.UserDTO;

public interface UserService {
	
	public void createUser(UserDTO user);
	
	public UserDTO getUserById(Long id);
	
	public void updateUser(UserDTO user);
	
	public void deleteUser(Long id);
}
