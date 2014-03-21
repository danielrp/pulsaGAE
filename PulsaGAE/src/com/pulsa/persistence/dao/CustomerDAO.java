package com.pulsa.persistence.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.google.appengine.api.datastore.Key;
import com.pulsa.persistence.model.Contact;
import com.pulsa.persistence.model.Customer;
import com.pulsa.util.helper.SessionHelper;

public enum CustomerDAO {
	INSTANCE;

	public List<Customer> listCustomers() {
		EntityManager em = EMFService.get().createEntityManager();
		// Read the existing entries
		
		String userSession= SessionHelper.getUsername();
		Query q = em.createQuery("select c from Customer c where  c.username = '"+userSession+"' order by c.name");
		List<Customer> customers = q.getResultList();
		//System.out.println("size : " + customers.size());
		// System.out.println("CustomerDAO::listCustomers::"+customers.get(0).getName());
		return customers;
	}

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

	public Customer save(Customer cust) {
		synchronized (this) {
			EntityManager em = EMFService.get().createEntityManager();
			try {
				cust.checkContacts();
				if(cust.getId()==null){
					em.persist(cust);
				}else if (getCustomer(cust.getId().getId()) != null) {
					em.merge(cust);
				} else {
					em.persist(cust);
				}
			} catch (Exception e) {
				e.printStackTrace();
				cust = null;
			} finally {
				em.close();
				return cust;
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
	
	public Customer getCustomerByName(String name) {
		EntityManager em = EMFService.get().createEntityManager();
		Customer cust=null;
		try{
			String userSession= SessionHelper.getUsername();
			Query q = em
					.createQuery("select t from Customer t where t.username = :username and t.name=:name");
			q.setParameter("username", userSession);
			q.setParameter("name", name);
			List<Customer> customers = q.getResultList();
			System.out.println("getCustomerByName::list.size="+customers.size());
			if(customers.size()>0){
				cust=customers.get(0);
				System.out.println("getCustomerByName::cust="+cust.getId()+"|"+cust.getName()+" contact="+cust.getContacts()+"|"+cust.getContacts().size());
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			em.close();
		}
		return cust;
	}

	public Customer getCustomer(long id) {
		if(id==0){
			return null;
		}
		EntityManager em = EMFService.get().createEntityManager();
		Customer c = em.find(Customer.class, id);
		// .createQuery("select t from Customer t where t.id = :id");
		// q.setParameter("userId", userId);
		// List<Customer> customers = q.getResultList();
		return c;
	}

	public void remove(Key id) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			Customer customer = em.find(Customer.class, id.getId());
			if (customer != null) {
				em.remove(customer);
				System.out.println("CustomerDAO::remove::" + id + " "
						+ customer.getName() + " removed");
			} else {
				System.out.println("CustomerDAO::remove::" + id
						+ " is not found");
			}
		} finally {
			em.close();
		}
	}

	public boolean remove(long id) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			Customer customer = em.find(Customer.class, id);
			if (customer != null) {
				em.remove(customer);
				System.out.println("CustomerDAO::remove(long)::" + id + " "
						+ customer.getName() + " removed");
				return true;
			} else {
				System.out.println("CustomerDAO::remove(long)::" + id
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
			String userSession= SessionHelper.getUsername();
			Query q = em.createQuery("delete from Customer c where c.username='"+userSession+"'");
			q.executeUpdate();
			// em.remove(customer);
		} finally {
			em.close();
		}
	}
}
