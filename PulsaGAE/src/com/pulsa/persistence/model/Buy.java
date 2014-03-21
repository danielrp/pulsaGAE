package com.pulsa.persistence.model;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

//@Entity
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Buy {
	private long price;

	public Buy(){
	//	setType("Buy");
	}
	public Buy(int x){
		//setType("Buy");
		//setUrut(x);
	}
	
	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}
	
}
