package com.appServer.userService.Exception;

import com.appServer.userService.errorcodes.UserServiceErrorCodes;

public class UserApplicationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private UserServiceErrorCodes code;

	public UserApplicationException(UserServiceErrorCodes code){
		super(code.getValue());
		this.code = code;
	}

	public UserServiceErrorCodes getCode() {
		return code;
	}

	public void setCode(UserServiceErrorCodes code) {
		this.code = code;
	}
	
	
}
