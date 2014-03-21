package com.pulsa.persistence.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
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
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
// @MappedSuperclass
public class Balance extends Transaksi {

	// @Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	// private Key id;
	// private int urut;
	private long debet;
	private long kredit;
	private long balance;
	private String type;
	// private Sell sell;
	// private Buy buy;
	public void copyFrom(Balance b){
		setUrut(b.getUrut());
		setTransactionDate(b.getTransactionDate());
		setDebet(b.getDebet());
		setKredit(b.getKredit());
		setBalance(b.getBalance());
		setPrice(b.getPrice());
		setNumber(b.getNumber());
		setCustName(b.getCustName());
		setProfit(b.getProfit());
		setProduct(b.getProduct());
		setStatus(b.getStatus());
		setPaid(b.getPaid());
	}
	private long price;
	private String number;
	private String custName;
	private long profit;
	private String provider;
	private String product;

	// private String username;
	// private Date transactionDate;
	// private Date inputDate;
	// private String status;
	// private String paid;

	public Balance() {
		// setId(KeyFactory.createKey(Customer.class.getSimpleName(), 0));

		initValue();
	}

	public Balance(String id) {
		if (id == null) {
			setId(KeyFactory.createKey(Balance.class.getSimpleName(), 0));
		} else if (id.equals("")) {
			setId(KeyFactory.createKey(Balance.class.getSimpleName(), 0));
		} else {
			setId(KeyFactory.createKey(Balance.class.getName(), id));
		}
	}
	
	

	public Balance(String type, int urut) {
		initValue();
		setType(type);
		setUrut(urut);

		if (type.equals("Sell")) {
			// sell=new Sell();
			// buy= null;
		} else {
			// sell=null;
			// buy= new Buy();
		}
	}
	
	public Balance(String type, int urut, String username) {
		//initValue();
		setUsername(username);
		setType(type);
		setUrut(urut);

		if (type.equals("Sell")) {
			// sell=new Sell();
			// buy= null;
		} else {
			// sell=null;
			// buy= new Buy();
		}
	}

	public boolean isSell() {
		if (type.equals("Sell")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isBuy() {
		if (type.equals("Buy")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isPaidOff() {
		if (getPaid() != null) {
			if (getPaid().equals("p")) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public void initValue() {
		setUsername(SessionHelper.getUsername());
		setInputDate(new Date());
		setTransactionDate(new Date());
		setStatus("r");
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

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

}