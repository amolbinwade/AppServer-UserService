package com.appserver.userservice.exception;

import com.appserver.userservice.errorcodes.UserServiceErrorCodes;

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
