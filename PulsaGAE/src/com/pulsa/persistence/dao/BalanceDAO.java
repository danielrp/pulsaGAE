package com.pulsa.persistence.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.google.appengine.api.datastore.Key;
import com.pulsa.persistence.model.Balance;
import com.pulsa.persistence.model.Contact;
import com.pulsa.persistence.model.Customer;
import com.pulsa.persistence.model.Sell;
import com.pulsa.persistence.model.Buy;
import com.pulsa.util.helper.SessionHelper;

public enum BalanceDAO {
	INSTANCE;

	public List<Balance> listBalance() {
		EntityManager em = EMFService.get().createEntityManager();
		// Read the existing entries

		String userSession = SessionHelper.getUsername();
		Query q = em
				.createQuery("select b from Balance b where  b.username = '"
						+ userSession + "'");
		List<Balance> balance = q.getResultList();
		/*
		 * Query q =
		 * em.createQuery("select b from Buy b where  b.username = '"+userSession
		 * +"'"); List<Balance> buyList = q.getResultList(); Query q2 =
		 * em.createQuery
		 * ("select b from Sell b where  b.username = '"+userSession+"'");
		 * List<Balance> sellList = q2.getResultList(); List<Balance> balance =
		 * new ArrayList(); balance.addAll(buyList); balance.addAll(sellList);
		 */
		balance = sort(balance);
		// System.out.println("size : " + balance.size());
		// System.out.println("CustomerDAO::listCustomers::"+customers.get(0).getName());
		return balance;

	}

	public List<Balance> listBalance(int urut) {
		EntityManager em = EMFService.get().createEntityManager();
		// Read the existing entries

		String userSession = SessionHelper.getUsername();
		Query q = em
				.createQuery("select b from Balance b where  b.username = '"
						+ userSession + "' and urut>" + urut);
		List<Balance> balance = q.getResultList();
		/*
		 * Query q =
		 * em.createQuery("select b from Buy b where  b.username = '"+userSession
		 * +"' and urut>"+urut); List<Balance> buyList = q.getResultList();
		 * Query q2 =
		 * em.createQuery("select b from Sell b where  b.username = '"
		 * +userSession+"' and urut>"+urut); List<Balance> sellList =
		 * q2.getResultList(); List<Balance> balance = new ArrayList();
		 * balance.addAll(buyList); balance.addAll(sellList);
		 */
		balance = sort(balance);
		// System.out.println("size : " + balance.size());
		// System.out.println("CustomerDAO::listCustomers::"+customers.get(0).getName());
		return balance;

	}

	public int getLastNumber(List<Balance> balance) {
		// List<Balance> balance=listBalance();
		int max = 0;
		for (int i = 0; i < balance.size(); i++) {
			if (max < balance.get(i).getUrut()) {
				max = balance.get(i).getUrut();
			}
		}
		return max;
	}

	public long getLastBalance(List<Balance> balance) {
		// List<Balance> balance=listBalance();
		try{
			if (balance.size() > 0) {
				return balance.get(balance.size() - 1).getBalance();
			} else {
				return 0;
			}
		}catch(Exception e){
			return 0;
		}
	}

	public long getLastBalance(List<Balance> balance, int urut) {
		// List<Balance> balance=listBalance();
		long last = 0;
		try{
			if (balance != null) {
				for (int i = 0; i < balance.size(); i++) {
					if (urut > balance.get(i).getUrut()) {
						last = balance.get(i).getBalance();
					} else {
						break;
					}
				}
			}
		}catch(Exception e){
			
		}

		// if(balance.size()>0){
		return last;
		// }else{
		// return 0;
		// }
	}

	public List<Balance> sort(List<Balance> balance) {
		Collections.sort(balance, new Comparator<Balance>() {
			public int compare(Balance lhs, Balance rhs) {
				Integer l = lhs.getUrut();
				Integer r = rhs.getUrut();
				return l.compareTo(r);
			}
		});
		return balance;
	}

	/*
	 * @SuppressWarnings("finally") public Sell saveSell(Sell sell) {
	 * synchronized (this) {
	 * System.out.println("BalanceDAO::saveSell id:"+sell.getId());
	 * EntityManager em = EMFService.get().createEntityManager(); try {
	 * em.getTransaction().begin(); if(sell.getId()==null){ em.persist(sell);
	 * System.out.println("BalanceDAO::id null persist:"+sell.getId()); }else if
	 * (getSell(sell.getId().getId()) != null) {
	 * System.out.println(em.getEntityManagerFactory
	 * ()+" "+em.getMetamodel()+" "+em+" "+em.getTransaction()); em.merge(sell);
	 * /* long bal=sell.getBalance(); List<Balance>
	 * listBalance=listBalance(sell.getUrut());
	 * 
	 * for(int i=0;i<listBalance.size();i++){ //em.close(); // em =
	 * EMFService.get().createEntityManager(); Balance balan=listBalance.get(i);
	 * bal=bal+balan.getDebet()-balan.getKredit();
	 * balan=em.find(Balance.class,balan.getId().getId());
	 * balan.setBalance(bal); /*if(listBalance.get(i).getType().equals("Sell")){
	 * Sell s=(Sell)listBalance.get(i); bal=bal+s.getDebet()-s.getKredit(); Sell
	 * s2=em.find(Sell.class, s.getId().getId()); s2.setBalance(bal);
	 * //em.merge(s); }else{ Buy b=(Buy)listBalance.get(i);
	 * bal=bal+b.getDebet()-b.getKredit(); Buy b2=em.find(Buy.class,
	 * b.getId().getId()); b2.setBalance(bal); //em.merge(b); } }
	 * System.out.println("BalanceDAO::merge:"+sell.getId()); } else {
	 * em.persist(sell);
	 * System.out.println("CUSTOMERDAO::persist:"+sell.getId()); } //em.flush();
	 * em.getTransaction().commit(); System.out.println("sell number : " +
	 * sell.getNumber() + " price " + sell.getPrice() + " created");
	 * 
	 * } catch (Exception e) { if(em.getTransaction().isActive())
	 * em.getTransaction().rollback(); e.printStackTrace(); sell = null; }
	 * finally { em.close(); return sell; }
	 * 
	 * } }
	 * 
	 * @SuppressWarnings("finally") public Buy saveBuy(Buy buy) { synchronized
	 * (this) { System.out.println("BalanceDAO::saveBuy id:"+buy.getId());
	 * EntityManager em = EMFService.get().createEntityManager(); try {
	 * em.getTransaction().begin(); if(buy.getId()==null){ em.persist(buy);
	 * System.out.println("BalanceDAO::id null persist:"+buy.getId()); }else if
	 * (getBuy(buy.getId().getId()) != null) { em.merge(buy); /*long
	 * bal=buy.getBalance(); List<Balance>
	 * listBalance=listBalance(buy.getUrut());
	 * 
	 * for(int i=0;i<listBalance.size();i++){ //em.close(); // em =
	 * EMFService.get().createEntityManager();
	 * 
	 * if(listBalance.get(i).getType().equals("Sell")){ Sell
	 * s=(Sell)listBalance.get(i); bal=bal+s.getDebet()-s.getKredit(); Sell
	 * s2=em.find(Sell.class, s.getId().getId()); s2.setBalance(bal);
	 * //em.merge(s); }else{ Buy b=(Buy)listBalance.get(i);
	 * bal=bal+b.getDebet()-b.getKredit(); Buy b2=em.find(Buy.class,
	 * b.getId().getId()); b2.setBalance(bal); //em.merge(b); } }
	 * System.out.println("BalanceDAO::merge:"+buy.getId()); } else {
	 * em.persist(buy); System.out.println("BalanceDAO::persist:"+buy.getId());
	 * } //em.flush(); // em.getTransaction().commit();
	 * em.getTransaction().commit(); System.out.println("buy number : " +
	 * buy.getPrice() + " price " + buy.getPrice() + " saved");
	 * 
	 * } catch (Exception e) { if(em.getTransaction().isActive())
	 * em.getTransaction().rollback(); e.printStackTrace(); buy = null; }
	 * finally { em.close(); return buy; }
	 * 
	 * } }
	 */

	@SuppressWarnings("finally")
	public Balance saveBalance(Balance balance) {
		synchronized (this) {
			System.out.println("BalanceDAO::saveBalance id:" + balance.getId());
			EntityManager em = EMFService.get().createEntityManager();
			try {
				em.getTransaction().begin();
				if (balance.getId() == null) {
					em.persist(balance);
					System.out.println("BalanceDAO::id null persist:"
							+ balance.getId());
				} else if (getBalance(balance.getId().getId()) != null) {
					Balance c = em.find(Balance.class, balance.getId().getId());
					c.copyFrom(balance);
					em.merge(c);
					/*
					 * long bal=balance.getBalance(); List<Balance>
					 * listBalance=listBalance(balance.getUrut());
					 * 
					 * for(int i=0;i<listBalance.size();i++){ //em.close(); //
					 * em = EMFService.get().createEntityManager(); Balance
					 * s=listBalance.get(i); bal=bal+s.getDebet()-s.getKredit();
					 * Balance s2=em.find(Balance.class, s.getId().getId());
					 * s2.setBalance(bal); /*
					 * if(listBalance.get(i).getType().equals("Sell")){ Sell
					 * s=(Sell)listBalance.get(i);
					 * bal=bal+s.getDebet()-s.getKredit(); Sell
					 * s2=em.find(Sell.class, s.getId().getId());
					 * s2.setBalance(bal); //em.merge(s); }else{ Buy
					 * b=(Buy)listBalance.get(i);
					 * bal=bal+b.getDebet()-b.getKredit(); Buy
					 * b2=em.find(Buy.class, b.getId().getId());
					 * b2.setBalance(bal); //em.merge(b); } }
					 */
					System.out.println("BalanceDAO::merge:" + balance.getId());
				} else {
					em.persist(balance);
					System.out
							.println("BalanceDAO::persist:" + balance.getId());
				}
				// em.flush();
				// em.getTransaction().commit();
				em.getTransaction().commit();
				System.out.println("buy number : " + balance.getUrut()
						+ " balance " + balance.getBalance() + " saved");

			} catch (Exception e) {
				if (em.getTransaction().isActive())
					em.getTransaction().rollback();
				e.printStackTrace();
				balance = null;
			} finally {
				em.close();
				return balance;
			}

		}
	}

	public boolean removeBalance(long id) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			Balance balance = em.find(Balance.class, id);
			if (balance != null) {
				em.getTransaction().begin();
				long bal = balance.getBalance() - balance.getDebet()
						+ balance.getKredit();
				int urut = balance.getUrut();
				em.remove(balance);
				/*
				 * List<Balance> listBalance=listBalance(balance.getUrut());
				 * 
				 * for(int i=0;i<listBalance.size();i++){ //em.close(); // em =
				 * EMFService.get().createEntityManager(); Balance
				 * s=listBalance.get(i); bal=bal+s.getDebet()-s.getKredit();
				 * Balance s2=em.find(Balance.class, s.getId().getId());
				 * s2.setBalance(bal); s2.setUrut(urut++);
				 * 
				 * }
				 */
				em.getTransaction().commit();
				// System.out.println("CustomerDAO::remove(long)::" + id + " "
				// + customer.getName() + " removed");
				return true;
			} else {
				System.out.println("BalanceDAO::removeSell(long)::" + id
						+ " is not found");
				return false;
			}

		} catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
			return false;
		} finally {

			em.close();
			// return false;
		}
	}

	/*
	 * public List<Sell> getSell(String userId) { EntityManager em =
	 * EMFService.get().createEntityManager(); Query q = em
	 * .createQuery("select t from Customer t where t.author = :userId");
	 * q.setParameter("userId", userId); List<Customer> customers =
	 * q.getResultList(); return customers; }
	 */
	public Balance getBalance(long id) {
		if (id == 0) {
			return null;
		}
		EntityManager em = EMFService.get().createEntityManager();
		Balance c = em.find(Balance.class, id);
		// .createQuery("select t from Customer t where t.id = :id");
		// q.setParameter("userId", userId);
		// List<Customer> customers = q.getResultList();
		return c;
	}

	public Sell getSell(long id) {
		if (id == 0) {
			return null;
		}
		EntityManager em = EMFService.get().createEntityManager();
		Sell c = em.find(Sell.class, id);
		// .createQuery("select t from Customer t where t.id = :id");
		// q.setParameter("userId", userId);
		// List<Customer> customers = q.getResultList();
		return c;
	}

	public Buy getBuy(long id) {
		if (id == 0) {
			return null;
		}
		EntityManager em = EMFService.get().createEntityManager();
		Buy c = em.find(Buy.class, id);
		// .createQuery("select t from Customer t where t.id = :id");
		// q.setParameter("userId", userId);
		// List<Customer> customers = q.getResultList();
		return c;
	}

	public void removeSell(Key id) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			Sell s = em.find(Sell.class, id.getId());
			if (s != null) {
				em.remove(s);
				// System.out.println("CustomerDAO::remove::" + id + " "
				// + customer.getName() + " removed");
			} else {
				System.out.println("BalanceDAO::removeSell::" + id
						+ " is not found");
			}
		} finally {
			em.close();
		}
	}

	public boolean removeSell(long id) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			Sell sell = em.find(Sell.class, id);
			if (sell != null) {
				em.remove(sell);
				// System.out.println("CustomerDAO::remove(long)::" + id + " "
				// + customer.getName() + " removed");
				return true;
			} else {
				System.out.println("BalanceDAO::removeSell(long)::" + id
						+ " is not found");
				return false;
			}

		} catch (Exception e) {

			e.printStackTrace();
			return false;
		} finally {

			em.close();
			// return false;
		}
	}

	public void removeAll() {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			String userSession = SessionHelper.getUsername();
			Query q = em.createQuery("delete from Balance c where c.username='"
					+ userSession + "'");
			q.executeUpdate();
			// em.remove(customer);
		} finally {
			em.close();
		}
	}
}
