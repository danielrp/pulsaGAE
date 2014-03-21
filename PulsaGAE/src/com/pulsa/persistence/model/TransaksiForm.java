package com.pulsa.persistence.model;

import com.pulsa.persistence.dao.UserDAO;

public class TransaksiForm extends PageForm{
	
	private String paid;
	private long balance;
	private long profit;
	private long debet;
	private long kredit;
	private long receivable;
	private long payable;
	private String password;
	
	public TransaksiForm(int size,int view){
		UserInfo u = UserDAO.INSTANCE.getUser();
		if(u!=null){
			setPassword(u.getPassword());
		}
		setSize(size);
		setView(view);
		setTotal(size/view);
		if(size%view>0){
			setTotal(getTotal()+1);
		}
		setCurrent(getTotal());
	}

	
	
	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}

	

	public long getReceivable() {
		return receivable;
	}



	public void setReceivable(long receivable) {
		this.receivable = receivable;
	}



	public long getPayable() {
		return payable;
	}



	public void setPayable(long payable) {
		this.payable = payable;
	}



	public long getProfit() {
		return profit;
	}

	

	public long getDebet() {
		return debet;
	}



	public void setDebet(long debet) {
		this.debet = debet;
	}



	public long getKredit() {
		return kredit;
	}



	public void setKredit(long kredit) {
		this.kredit = kredit;
	}



	public void setProfit(long profit) {
		this.profit = profit;
	}



	public String getPaid() {
		return paid;
	}

	public void setPaid(String paid) {
		this.paid = paid;
	}

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}
	
	
	
	
}
