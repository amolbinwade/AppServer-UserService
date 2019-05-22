package com.appServer.userService.errorcodes;

public enum UserServiceErrorCodes {

USER_ID_NOT_PROVIDED(101, "User ID is null or blank."), 
USER_ID_NOT_EXIST(102, "User ID not exist."), 
USER_DATA_NOT_PROVIDED(103, "User data not provided"),
USER_REQUIRED_FIELD_PROVIDED(103, "User's required field(s) not provided");
	
	private int code;
	private String desc;
	
	private UserServiceErrorCodes(int code, String desc){
		this.code = code;
		this.desc = desc;
	}
	
	public String getValue(){
		return this.desc;
	}
	
	public int getCode(){
		return this.code;
	}
}
