package com.appServer.userService.service;

import java.util.List;

import com.appServer.userService.dto.UserDTO;

public interface UserService {
	
	public UserDTO createUser(UserDTO user);
	
	public UserDTO getUserById(Long id);
	
	public UserDTO updateUser(UserDTO user);
	
	public void deleteUser(Long id);
	
	public List<UserDTO> getUsers();
}
