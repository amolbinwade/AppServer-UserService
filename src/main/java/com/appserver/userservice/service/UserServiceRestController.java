package com.appserver.userservice.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.appServer.userService.dto.UserDTO;
import com.appserver.userservice.errorcodes.UserServiceErrorCodes;
import com.appserver.userservice.errorresponse.ErrorCodeResponse;
import com.appserver.userservice.exception.ReqDataMissingApplicationException;
import com.appserver.userservice.exception.UserApplicationException;

@RestController
@RequestMapping("/userservice")
public class UserServiceRestController {

	@Autowired
	UserService userService;

	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@RequestBody UserDTO user) {
		try {
			user = userService.createUser(user);
		} catch (UserApplicationException e) {
			return this.getResponseEntityForAppException(e);
		} catch (Exception e){
			return this.getResponseEntityForNonAppException(e);
		}
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(
				"/{id}").buildAndExpand(user.getId()).toUri();

		return ResponseEntity.created(location).body(user);

	}	

	@PutMapping("/users")
	public ResponseEntity<Object> updateUser(@RequestBody UserDTO user) {
		try {
			user = userService.updateUser(user);
		} catch (UserApplicationException e) {
			return this.getResponseEntityForAppException(e);
		} catch (Exception e){
			return this.getResponseEntityForNonAppException(e);
		}

		return ResponseEntity.ok(user);

	}
	
	@GetMapping("/users/{id}")
	public ResponseEntity<Object> getUserById(@PathVariable Long id) {
		UserDTO user = null;
		try{
			user = userService.getUserById(id);
		} catch (UserApplicationException e){
			return this.getResponseEntityForAppException(e);
		} catch (Exception e){
			return this.getResponseEntityForNonAppException(e);
		}
		return ResponseEntity.ok(user);
	}
	
	/*@GetMapping("/users")
	public ResponseEntity<Object> getUsersPage(@RequestParam(name="pagesize") int pageSize, 
			@RequestParam(name="pagenum") int pageNum) {
		List<UserDTO> userList = new ArrayList<>();
		try{
			user = userService.getUserById(id);
		} catch (UserApplicationException e){
			return this.getResponseEntityForAppException(e);
		} catch (Exception e){
			return this.getResponseEntityForNonAppException(e);
		}
		return ResponseEntity.ok(user);
	}*/
	
	private ResponseEntity<Object> getResponseEntityForAppException(UserApplicationException e){
		
		ResponseEntity<Object> res = null;
		switch(e.getCode()){
			case USER_ID_NOT_EXIST:
				res = new ResponseEntity<Object>(new ErrorCodeResponse(e.getCode().getCode(), 
						e.getCode().getValue()), HttpStatus.NOT_FOUND);
				break;
			case USER_DATA_NOT_PROVIDED:
				res = new ResponseEntity<Object>(new ErrorCodeResponse(e.getCode().getCode(), 
						e.getCode().getValue()), HttpStatus.NOT_FOUND);
				break;
			case USER_REQUIRED_FIELD_NOT_PROVIDED:
				res = new ResponseEntity<Object>(new ErrorCodeResponse(e.getCode().getCode(), 
						e.getCode().getValue()+" : "+e.toString()), HttpStatus.NOT_FOUND);
				break;
			default:
				res = new ResponseEntity<Object>(new ErrorCodeResponse(e.getCode().getCode(), 
						e.getCode().getValue()), HttpStatus.BAD_REQUEST);
				break;
		}
		return res;
				
	}
	
	private ResponseEntity<Object> getResponseEntityForNonAppException(Exception e) {
		return new ResponseEntity<Object>(new ErrorCodeResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), 
				e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
