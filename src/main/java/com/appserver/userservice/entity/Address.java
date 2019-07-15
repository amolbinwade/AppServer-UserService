package com.appserver.userservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ADDRESS")
public class Address {
	
	private Long id;
	private String line1;
	private String line2;
	private String city;
	private String state;
	private String pincode;
	private String country;
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="line1", nullable=false)
	public String getLine1() {
		return line1;
	}
	
	public void setLine1(String line1) {
		this.line1 = line1;
	}
	
	@Column(name="line2")
	public String getLine2() {
		return line2;
	}
	
	public void setLine2(String line2) {
		this.line2 = line2;
	}
	
	@Column(name="city")
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	@Column(name="state")
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	@Column(name="pincode", nullable=false)
	public String getPincode() {
		return pincode;
	}
	
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	
	@Column(name="country")
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	

}
