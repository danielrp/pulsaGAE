package com.pulsa.persistence.model;

import javax.persistence.Basic;
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
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key id;
	private String name;
	private String desc;
	private String username;

	@Basic	
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private List<Contact> contacts = new ArrayList<Contact>();

	public Customer() {
		// setId(KeyFactory.createKey(Customer.class.getSimpleName(), 0));
		contacts.add(new Contact());
		setUsername(SessionHelper.getUsername());
	}

	public Customer(String id) {
		if (id == null) {
			setId(KeyFactory.createKey(Customer.class.getSimpleName(), 0));
		} else if (id.equals("")) {
			setId(KeyFactory.createKey(Customer.class.getSimpleName(), 0));
		} else {
			setId(KeyFactory.createKey(Customer.class.getName(), id));
		}
	}

	public void checkContacts() {
		List<Contact> contacts2 = new ArrayList<Contact>();
		for (int i = 0; i < contacts.size(); i++) {
			Contact c = contacts.get(i);
			c.setCustomer(this);
			if (c != null) {
				if (c.getNumber() != null) {
					if (!c.getNumber().equals("")) {
						contacts2.add(c);
					}
				}
			}
		}
		this.contacts = contacts2;
	}
	
	public boolean isNumberExist(String num) {
		for (int i = 0; i < getContacts().size(); i++) {
			Contact c = getContacts().get(i);
			if (c.getNumber().equals(num)) {
				//System.out.println("isNumberExist num:"+num+" is exist");
				return true;
			}
		}
	//	System.out.println("isNumberExist num:"+num+" is false");
		return false;
	}

	public Key getId() {
		return id;
	}

	public void setId(Key id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	//
}