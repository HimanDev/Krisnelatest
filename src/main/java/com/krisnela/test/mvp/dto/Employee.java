package com.krisnela.test.mvp.dto;


import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
public class Employee extends AbstractDto {
	public Employee() {
		super();
	}

	/**
	 * 
	 */
//	@Id
//	private String objectId;
	private String name,email,address,number;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

//	@Id
//	public String getObjectId() {
//		return objectId;
//	}

//	public void setObjectId(String objectId) {
//		this.objectId = objectId;
//	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotNull
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
