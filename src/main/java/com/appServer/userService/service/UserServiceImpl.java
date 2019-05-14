package com.appServer.userService.service;


import java.util.ArrayList;
import java.util.List;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.appServer.userService.dto.UserDTO;
import com.appServer.userService.entity.User;
import com.appServer.userService.errorcodes.UserServiceErrorCodes;
import com.appServer.userService.repository.UserRepository;

@Component
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	ModelMapper	 modelMapper;
	
	static Logger log = LogManager.getLogger();

	@Override
	@Transactional(readOnly=false)
	public UserDTO createUser(UserDTO user) {
		User u = this.convertToEntity(user);		
		userRepo.save(u);
		return this.convertToDTO(u);
	}

	@Override
	@Transactional(readOnly=false)
	public UserDTO getUserById(Long id) {
		Assert.notNull(id, UserServiceErrorCodes.USER_ID_NOT_PROVIDED.getValue());
		User u = userRepo.findById(id).orElse(null);
		Assert.notNull(u, UserServiceErrorCodes.USER_ID_NOT_EXIST.getValue());
		return this.convertToDTO(u);
		
	}

	@Override
	@Transactional(readOnly=false)
	public UserDTO updateUser(UserDTO user) {
		Assert.notNull(user,UserServiceErrorCodes.USER_DATA_NOT_PROVIDED.getValue());
		User u = userRepo.findById(user.getId()).orElse(null);
		Assert.notNull(u, UserServiceErrorCodes.USER_ID_NOT_EXIST.getValue());
		modelMapper.map(user, u);
		userRepo.save(u);
		return user;
		
	}

	@Override
	@Transactional(readOnly=false)
	public void deleteUser(Long id) {
		Assert.notNull(id, UserServiceErrorCodes.USER_ID_NOT_PROVIDED.getValue());
		User u = userRepo.findById(id).orElse(null);
		if(u != null){
			userRepo.delete(u);
		}

	}
	
	@Override
	@Transactional(readOnly=false)
	public List<UserDTO> getUsers() {
		Iterable<User> users = userRepo.findAll();
		List<UserDTO> userDTOs = new ArrayList<>();
		for(User u:users){
			userDTOs.add(modelMapper.map(u, UserDTO.class));
		}
		return userDTOs;
	}
	
	private User convertToEntity(UserDTO userDTO) {
		User u;
		log.log(Level.INFO, "Parsing UserDTO");
		try{
			u = modelMapper.map(userDTO, User.class);
		} catch(Exception e){
			log.log(Level.ERROR, "Error in parsing UserDTO."); 
			throw e;
		}
		return u;
	}
	
	private UserDTO convertToDTO(User user) {
		UserDTO u;
		log.log(Level.INFO, "Converting user to UserDTO");
		try{
			u = modelMapper.map(user, UserDTO.class);
		} catch(Exception e){
			log.log(Level.ERROR, "Error in parsing UserDTO."); 
			throw e;
		}
		return u;
	}

	

}
