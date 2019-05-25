package com.appserver.userservice.service;


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
import org.springframework.util.StringUtils;

import com.appServer.userService.dto.UserDTO;
import com.appserver.userservice.entity.User;
import com.appserver.userservice.errorcodes.UserServiceErrorCodes;
import com.appserver.userservice.exception.UserApplicationException;
import com.appserver.userservice.repository.UserRepository;

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
		try{
		this.validateMandatoryfields(user);
		User u = this.convertToEntity(user);		
		userRepo.save(u);
		user = this.convertToDTO(u);
		} catch(UserApplicationException e){
			log.error("Error in creating User", e);
			throw e;
		} catch(Exception e){
			log.error("Error in creating user", e.getMessage());
			throw e;
		}
		return user;
	}	

	@Override
	@Transactional(readOnly=false)
	public UserDTO getUserById(Long id) {
		if(id == null){
			throw new UserApplicationException(UserServiceErrorCodes.USER_ID_NOT_PROVIDED);
		}
		User u = userRepo.findById(id).orElse(null);
		if(u == null){
			throw new UserApplicationException(UserServiceErrorCodes.USER_ID_NOT_EXIST);
		}
		return this.convertToDTO(u);
		
	}

	@Override
	@Transactional(readOnly=false)
	public UserDTO updateUser(UserDTO user) {
		if(user == null || user.getId()==null){
			throw new UserApplicationException(UserServiceErrorCodes.USER_ID_NOT_PROVIDED);
		}
		User u = userRepo.findById(user.getId()).orElse(null);
		if(u == null){
			throw new UserApplicationException(UserServiceErrorCodes.USER_ID_NOT_EXIST);
		}
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
	
	private void validateMandatoryfields(UserDTO user) {
		if(user == null 
				|| StringUtils.isEmpty(user.getFirstName())
				|| StringUtils.isEmpty(user.getLastName())){
			throw new UserApplicationException(UserServiceErrorCodes.USER_REQUIRED_FIELD_NOT_PROVIDED);
		}
	}

}