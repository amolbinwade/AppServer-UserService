package com.appServer.userService.Exception;

public class UserApplicationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserApplicationException(String msg){
		super(msg);
	}
}
