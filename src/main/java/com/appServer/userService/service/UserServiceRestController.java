package com.appServer.userService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.appServer.userService.Exception.UserApplicationException;
import com.appServer.userService.dto.UserDTO;
import com.appServer.userService.errorcodes.UserServiceErrorCodes;
import com.appserver.userservice.errorresponse.ErrorCodeResponse;

@RestController
@RequestMapping("/userservice")
public class UserServiceRestController {

	@Autowired
	UserService userService;

	@PostMapping("/users")
	public ResponseEntity<Object> createUser(UserDTO user) {
		try {
			user = userService.createUser(user);
		} catch (UserApplicationException e) {
			return this.getErrorCodeByException(e);
		} catch (Exception e){
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Object>(user, HttpStatus.OK);

	}
	
	@GetMapping("/users/{id}")
	public ResponseEntity<Object> getUserById(@PathVariable Long id) {
		UserDTO user = null;
		try{
			user = userService.getUserById(id);
		} catch (UserApplicationException e){
			return this.getErrorCodeByException(e);
		} catch (Exception e){
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Object>(user, HttpStatus.OK);
	}
	
	private ResponseEntity<Object> getErrorCodeByException(UserApplicationException e){
		
		if(e.getCode() == UserServiceErrorCodes.USER_ID_NOT_EXIST){
			return new ResponseEntity<Object>(new ErrorCodeResponse(e.getCode().getCode(), 
					e.getCode().getValue()), HttpStatus.NOT_FOUND);
		} else{
			return new ResponseEntity<Object>(new ErrorCodeResponse(e.getCode().getCode(), 
					e.getCode().getValue()), HttpStatus.BAD_REQUEST);
		}
	}

}
