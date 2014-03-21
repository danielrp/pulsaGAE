package com.pulsa.persistence.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import com.pulsa.util.helper.SessionHelper;

//@Entity
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Sell {
	private long price;
	private String number;
	private String custName;
	private long profit;
	private String provider;
	private String product;
	
	public Sell() {
		// setId(KeyFactory.createKey(Customer.class.getSimpleName(), 0));
		//contacts.add(new Contact());
		initValue();
	}
	
	public Sell(int x) {
		// setId(KeyFactory.createKey(Customer.class.getSimpleName(), 0));
		//contacts.add(new Contact());
		initValue();
	//	setUrut(x);
	}
	
	public void initValue(){
		//setUsername(SessionHelper.getUsername());
		//setType("Sell");
	}
	
	
	public String getProduct() {
		return product;
	}



	public void setProduct(String product) {
		this.product = product;
	}



	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public long getProfit() {
		return profit;
	}
	public void setProfit(long profit) {
		this.profit = profit;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	
	
	
}
