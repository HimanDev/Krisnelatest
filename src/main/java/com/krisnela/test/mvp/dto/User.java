package com.krisnela.test.mvp.dto;

import org.springframework.beans.factory.BeanClassLoaderAware;

public class User extends AbstractDto implements BeanClassLoaderAware {
	private String email,password,name;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public User() {
	}
	public User(String objectId) {
		this.objectId=objectId;
	}

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		System.out.println("Himan Loader"+classLoader.getParent());
		// TODO Auto-generated method stub
		
	}

}
