package com.appServer.userService.service;


import javax.transaction.Transactional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.appServer.userService.dto.UserDTO;
import com.appServer.userService.entity.User;
import com.appServer.userService.repository.UserRepository;

@Component
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	ModelMapper	 modelMapper;
	
	static Logger log = LogManager.getLogger();

	@Override
	@Transactional
	public void createUser(UserDTO user) {
		User u = this.convertToEntity(user);		
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

}
