package com.pulsa.persistence.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Entity
public class Contact {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key id;
	
	private String provider;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Customer customer;
	
	private String number;
	private String desc;
	
	public Contact(){
		//setId(0);
	}
	
	public Contact(String id){
		setId(KeyFactory.createKey(Contact.class.getSimpleName(),id));
	}
	
	public Key getId() {
		return id;
	}
	public void setId(Key id) {
		this.id = id;
	}

	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	
	
}
