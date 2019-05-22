package com.appServer.userService.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appServer.userService.dto.UserDTO;

@RestController
@RequestMapping("/userservice")
public class UserServiceRestController {
	
	@Autowired
	UserService userService;
	
	public ResponseEntity<UserDTO> createUser(UserDTO user){
		UserDTO userDto = userService.createUser(user);
		
		return new ResponseEntity(userDto, HttpStatus.OK);
		
	}

}
