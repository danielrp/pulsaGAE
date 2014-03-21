package com.pulsa.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.appengine.api.datastore.Key;
import com.pulsa.persistence.model.Contact;
import com.pulsa.persistence.model.Customer;
import com.pulsa.persistence.model.UserInfo;

import com.pulsa.util.helper.SessionHelper;

public enum UserDAO {
	INSTANCE;
	
	@SuppressWarnings("unchecked")
	public List<UserInfo> listUser() {
		EntityManager em = EMFService.get().createEntityManager();
		// Read the existing entries

		//String userSession= SessionHelper.getUsername();
		Query q = em.createQuery("select c from UserInfo c");
		List<UserInfo> users = q.getResultList();
		//System.out.println("size : " + users.size());
		// System.out.println("CustomerDAO::listCustomers::"+customers.get(0).getName());
		return users;
		}
		/*
		 * // PersistenceManager pm = ...; Transaction tx =
		 * em.currentTransaction(); User user = userService.currentUser();
		 * List<Account> accounts = new ArrayList<Account>();
		 * 
		 * try { tx.begin();
		 * 
		 * Query query = pm.newQuery("select from Customer " +
		 * "where user == userParam " + "parameters User userParam");
		 * List<Customer> customers = (List<Customer>) query.execute(user);
		 * 
		 * query = pm.newQuery("select from Account " +
		 * "where parent-pk == keyParam " + "parameters Key keyParam"); for
		 * (Customer customer : customers) { accounts.addAll((List<Account>)
		 * query.execute(customer.key)); }
		 * 
		 * } finally { if (tx.isActive()) { tx.rollback(); } }
		 */
	
/*
	public Customer add(String name, String desc) {
		synchronized (this) {
			System.out.println("CUSTOMERDAO::add");
			EntityManager em = EMFService.get().createEntityManager();
			try {

				Customer customer = new Customer();
				// customer.setId(new Key());
				customer.setName(name);
				customer.setDesc(desc);
				// em.getTransaction().begin();
				em.persist(customer);
				// em.getTransaction().commit();
				System.out.println("name " + name + " desc " + desc
						+ " created");
				return customer;
			} catch (Exception e) {
				return null;
			} finally {
				em.close();
			}
		}
	}

	public void add(Customer cust) {
		synchronized (this) {
			System.out.println("CUSTOMERDAO::add");
			EntityManager em = EMFService.get().createEntityManager();
			Customer customer = new Customer();
			// customer.setId(new Key());
			customer.setName(cust.getName());
			customer.setDesc(cust.getDesc());
			// em.getTransaction().begin();
			em.persist(customer);
			// em.getTransaction().commit();
			System.out.println("name " + cust.getName() + " desc "
					+ cust.getDesc() + " created");
			em.close();
		}
	}
*/
	public UserInfo save(UserInfo u) {
		synchronized (this) {
			System.out.println("UserDAO::save id:"+u.getUsername());
			EntityManager em = EMFService.get().createEntityManager();
			try {

				// Customer customer = new Customer();
				// customer.setId(new Key());
				// customer.setName(cust.getName());
				// customer.setDesc(cust.getDesc());
				// em.getTransaction().begin();
				//cust.checkContacts();
				if(u.getUsername()==null){
					em.persist(u);
					System.out.println("UserDAO::id null persist:"+u.getUsername());
				}else if (getUser(u.getUsername()) != null) {
					em.merge(u);
					System.out.println("UserDAO::merge:"+u.getUsername());
				} else {
					em.persist(u);
					System.out.println("UserDAO::persist:"+u.getUsername());
				}
				// em.getTransaction().commit();
				

			} catch (Exception e) {
				e.printStackTrace();
				u = null;
			} finally {
				em.close();
				return u;
			}

		}
	}

	public List<Customer> getCustomers(String userId) {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em
				.createQuery("select t from Customer t where t.author = :userId");
		q.setParameter("userId", userId);
		List<Customer> customers = q.getResultList();
		return customers;
	}

	public UserInfo getUser() {
		
		EntityManager em = EMFService.get().createEntityManager();
		String userSession= SessionHelper.getUsername();
		UserInfo c = em.find(UserInfo.class, userSession);
		
		return c;
	}
	@SuppressWarnings("unchecked")
	public UserInfo getUser(String id) {
		if(id==null){
			return null;
		}
		EntityManager em = EMFService.get().createEntityManager();
		UserInfo c = em.find(UserInfo.class, id);
		
		return c;
	}
	
	public boolean isValidUser(String username,String password){
		UserInfo u= getUser(username);
		if(u==null){
			if(username.equals("daniel") && password.equals("daniel")){
				UserInfo insert=new UserInfo(username);
				insert.setPasswordApp(password);
				save(insert);
				return true;
			}else{
				return false;
			}
		}else{
		    if(u.getPasswordApp()==null){
		    	u.setPasswordApp(password);
		    	save(u);
		    	return true;
		    }else if(u.getPasswordApp().equals(password))
				return true;
			else
				return false;
		}	
		
	}
	
	public List<SimpleGrantedAuthority> getAuthoritiesByUser(String username){
		SimpleGrantedAuthority auth= new SimpleGrantedAuthority("ROLE_USER");
		List <SimpleGrantedAuthority> list = new ArrayList();
		list.add(auth);
		if(username.equals("daniel")){
			list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		return list;
	}

/*	public void remove(String username) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			UserInfo u= em.find(UserInfo.class, username);
			if (u!= null) {
				em.remove(u);
				System.out.println("CustomerDAO::remove::" + username + " "
						+ u.getUsername() + " removed");
			} else {
				System.out.println("CustomerDAO::remove::" + username
						+ " is not found");
			}
		} finally {
			em.close();
		}
	}
*/
	public boolean remove(String username) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			UserInfo u  = em.find(UserInfo.class, username);
			if (u != null) {
				em.remove(u);
				System.out.println("UserDAO::remove(string)::" + username + " "
						+ u.getUsername() + " removed");
				return true;
			} else {
				System.out.println("UserDAO::remove(long)::" + username
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

/*	public void removeAll() {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			Query q = em.createQuery("delete from Customer c");
			q.executeUpdate();
			// em.remove(customer);
		} finally {
			em.close();
		}
	}*/
}
