package com.pulsa.persistence.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import java.util.List;
import java.util.ArrayList;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pulsa.util.helper.SessionHelper;

@Entity
public class UserInfo {
	@Id
	private String username;
	private String passwordApp;
	private String password;
	private String name;
	private String email;
	
	public UserInfo(){
		setUsername(SessionHelper.getUsername());
	}
	
	public UserInfo(String username){
		setUsername(username);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}



	public String getPasswordApp() {
		return passwordApp;
	}
	
	public String getMaskedPassword() {
		int i=0;
		String s="";
		while(i<passwordApp.length()){
			if(i<2){
				s+=passwordApp.charAt(i++);
			}else{
				s+="*";
				i++;
			}
		}
		return s;
	}

	public void setPasswordApp(String passwordApp) {
		this.passwordApp = passwordApp;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
