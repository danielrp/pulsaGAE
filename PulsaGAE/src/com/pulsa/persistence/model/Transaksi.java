package com.pulsa.persistence.model;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pulsa.util.helper.SessionHelper;

@Entity
@MappedSuperclass
public abstract class Transaksi {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key id;
	private int urut;
	private String username;
	private Date transactionDate;
	private Date inputDate;
	private String status;
	private String paid;
	public Key getId() {
		return id;
	}
	public void setId(Key id) {
		this.id = id;
	}
	public int getUrut() {
		return urut;
	}
	public void setUrut(int urut) {
		this.urut = urut;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public Date getInputDate() {
		return inputDate;
	}
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPaid() {
		return paid;
	}
	public void setPaid(String paid) {
		this.paid = paid;
	}
	
	
}
