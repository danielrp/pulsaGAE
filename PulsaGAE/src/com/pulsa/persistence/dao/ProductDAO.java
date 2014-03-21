package com.pulsa.persistence.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.google.appengine.api.datastore.Key;
import com.pulsa.persistence.model.Contact;
import com.pulsa.persistence.model.Product;
import com.pulsa.util.helper.SessionHelper;

public enum ProductDAO {
	INSTANCE;

	public List<Product> listProduct() {
		EntityManager em = EMFService.get().createEntityManager();

		String userSession= SessionHelper.getUsername();
		Query q = em.createQuery("select c from Product c where  c.username = '"+userSession+"' order by c.provider,c.nominal");
		List<Product> products = q.getResultList();
		return products;
		
	}
	
	public List<Product> listProduct(String code) {
		EntityManager em = EMFService.get().createEntityManager();

		String userSession= SessionHelper.getUsername();
		Query q = em.createQuery("select c from Product c where  c.username = '"+userSession+"' and c.code like '"+code+"%' order by c.provider,c.nominal");
		List<Product> products = q.getResultList();
		return products;
		
	}

	public void add(Product p) {
		synchronized (this) {
			System.out.println("ProductDAO::add");
			EntityManager em = EMFService.get().createEntityManager();
			//Product product = new Product();
			// product.setId(new Key());
			//product.setName(p.getName());
			//product.setDesc(cust.getDesc());
			// em.getTransaction().begin();
			em.persist(p);
			// em.getTransaction().commit();
			
			em.close();
		}
	}

	public Product save(Product p) {
		synchronized (this) {
			System.out.println("ProductDAO::save id:"+p.getId());
			EntityManager em = EMFService.get().createEntityManager();
			try {


				if(p.getId()==null){
					em.persist(p);
					System.out.println("ProductDAO::id null persist:"+p.getId());
				}else if (getProduct(p.getId().getId()) != null) {
					em.merge(p);
					System.out.println("ProductDAO::merge:"+p.getId());
				} else {
					em.persist(p);
					System.out.println("ProductDAO::persist:"+p.getId());
				}
				// em.getTransaction().commit();
			

			} catch (Exception e) {
				e.printStackTrace();
				p = null;
			} finally {
				em.close();
				return p;
			}

		}
	}

	public List<Product> getProducts(String userId) {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em
				.createQuery("select t from Product t where t.author = :userId");
		q.setParameter("userId", userId);
		List<Product> products = q.getResultList();
		return products;
	}
	
	public Product getProductByCode(String code) {
		EntityManager em = EMFService.get().createEntityManager();
		Product product=null;
		try{
			String userSession= SessionHelper.getUsername();
			Query q = em
					.createQuery("select t from Product t where t.username = :username and t.code=code");
			q.setParameter("username", userSession);
			q.setParameter("code", code);
			List<Product> products = q.getResultList();
			
			if(products.size()>0)
				product=products.get(0);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			em.close();
		}
		return product;
	}

	public Product getProduct(long id) {
		if(id==0){
			return null;
		}
		EntityManager em = EMFService.get().createEntityManager();
		Product c = em.find(Product.class, id);
		// .createQuery("select t from Product t where t.id = :id");
		// q.setParameter("userId", userId);
		// List<Product> products = q.getResultList();
		return c;
	}

	public boolean remove(long id) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			Product product = em.find(Product.class, id);
			if (product != null) {
				em.remove(product);
				return true;
			} else {
				System.out.println("ProductDAO::remove::" + id
						+ " is not found");
				return false;
			}
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}finally {
			em.close();
		}
	}

	

	public void removeAll() {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			String userSession= SessionHelper.getUsername();
			Query q = em.createQuery("delete from Product c where c.username='"+userSession+"'");
			q.executeUpdate();
			// em.remove(product);
		} finally {
			em.close();
		}
	}
}
