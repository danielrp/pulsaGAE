package com.pulsa.persistence.model;

public class CustomerForm extends PageForm{
	
	private String name;
	
	public CustomerForm(int size,int view){
		
		setSize(size);
		setView(view);
		setTotal(size/view);
		if(size%view>0){
			setTotal(getTotal()+1);
		}
		setCurrent(getTotal());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
}
