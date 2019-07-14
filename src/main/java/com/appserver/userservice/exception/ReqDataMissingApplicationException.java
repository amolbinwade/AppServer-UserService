package com.appserver.userservice.exception;

import java.util.ArrayList;
import java.util.List;

import com.appserver.userservice.errorcodes.UserServiceErrorCodes;

public class ReqDataMissingApplicationException extends UserApplicationException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5434516325741463987L;
	
	private List<String> missingFields;

	public ReqDataMissingApplicationException(UserServiceErrorCodes code) {
		super(code);		
	}
	
	public void addMissingField(String fieldName){
		if(this.missingFields == null){
			this.missingFields = new ArrayList<String>();
		}
		this.missingFields.add(fieldName);
	}
	
	public List<String> getMissingFields(){
		return this.missingFields;
	}

}
