package com.appserver.userservice.service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.appServer.userService.dto.UserDTO;
import com.appserver.userservice.entity.User;
import com.appserver.userservice.errorcodes.UserServiceErrorCodes;
import com.appserver.userservice.exception.ReqDataMissingApplicationException;
import com.appserver.userservice.exception.UserApplicationException;
import com.appserver.userservice.repository.UserRepository;

@Component
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	ModelMapper	 modelMapper;
	
	public static final int DEFAULT_PAGESIZE = 10;
	
	static Logger log = LogManager.getLogger();

	@Override
	@Transactional(readOnly=false)
	public UserDTO createUser(UserDTO user) {
		try{
		this.validateMandatoryfields(user);
		User u = this.convertToEntity(user);		
		u = userRepo.save(u);
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
	public List<UserDTO> getUsers(int pageSize, int pageNum){
		if(pageSize == 0){
			pageSize = DEFAULT_PAGESIZE;
		}
		List<UserDTO> userDTOs = new ArrayList<>();
		Pageable pageable = PageRequest.of(pageNum, pageSize);
		Page<User> usersPage = userRepo.findAll(pageable);
		userDTOs = (List<UserDTO>) usersPage.get()
				.map(user -> {return modelMapper.map(user, UserDTO.class);})
				.collect(Collectors.toList());
		
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
		List<String> missingFields = new ArrayList<>();
		boolean isMissing = false;
		if(user == null){
			isMissing = true;
			throw new UserApplicationException(UserServiceErrorCodes.USER_DATA_NOT_PROVIDED);
		} 
		
		if(StringUtils.isEmpty(user.getFirstName())){
			isMissing = true;
			missingFields.add("FIRST_NAME");
		}
		if(StringUtils.isEmpty(user.getLastName())){
			isMissing = true;
			missingFields.add("LAST_NAME");
		}
		if(StringUtils.isEmpty(user.getUserId())){
			isMissing = true;
			missingFields.add("USER_ID");
		}
		if(StringUtils.isEmpty(user.getEmailId())){
			isMissing = true;
			missingFields.add("EMAIL_ID");
		}
		
		if(isMissing){
			ReqDataMissingApplicationException ex 
			= new ReqDataMissingApplicationException(UserServiceErrorCodes.USER_REQUIRED_FIELD_NOT_PROVIDED);
			missingFields.forEach(field -> ex.addMissingField(field));
			throw ex;
		}
	}	

}
