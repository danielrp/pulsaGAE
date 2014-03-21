package com.pulsa.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pulsa.persistence.dao.CustomerDAO;
import com.pulsa.persistence.model.Customer;
import com.pulsa.persistence.model.CustomerForm;
import com.pulsa.persistence.model.TransaksiForm;
import com.pulsa.util.helper.KeyStringConverter;
import com.pulsa.util.helper.WriteExcel;
import com.google.appengine.api.datastore.Key;
 
@Controller
@RequestMapping("/customer/")
public class CustomerController {

	List<Customer> list=null;
	CustomerForm page = null;
	String name="";
	
	@InitBinder
	protected void initBinder(HttpServletRequest req, ServletRequestDataBinder binder) {
	    binder.registerCustomEditor(Key.class, new KeyStringConverter());
	}
	
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public String list( ModelMap model,@RequestParam(value="p",defaultValue="0",required=false) String p,@RequestParam(value="search",defaultValue="n",required=false) String search) {
		
		
		if(search!=null){
			name=search;
		}
		
		//if(list==null){
			list=CustomerDAO.INSTANCE.listCustomers();
	//	}
		getPage();
		int pg=0;
		if(p!=null)
			pg=Integer.parseInt(p);
		if(pg!=0){
			page.setCurrent(pg);
		}
		System.out.println("page= "+page+" p "+p+" current "+page.getCurrent()+" begin "+page.getBegin()+" end "+page.getEnd());
		model.addAttribute("listCustomer",list);
		model.addAttribute("page", page);
		model.addAttribute("customer",new Customer());
		return "customer";
 
	}
	
	public void getPage(){
		page=new CustomerForm(list.size(), 10);
		page.setName(name);
	}
	
	@RequestMapping(value="/list2", method = RequestMethod.GET)
	public String list2( ModelMap model) {
		
		//if(list==null){
			list=CustomerDAO.INSTANCE.listCustomers();
	//	}
		getPage();
		model.addAttribute("listCustomer",list);
		model.addAttribute("page", page);
		model.addAttribute("customer",new Customer());
		return "customer2";
 
	}
	
	@RequestMapping(value="/add", method = RequestMethod.POST)
	public String add( ModelMap model, Customer form) {
		//Customer custInserted=CustomerDAO.INSTANCE.add(form.getName(),form.getDesc());
		Customer custInserted=CustomerDAO.INSTANCE.save(form);
		if(custInserted!=null){
			if(list!=null){
				boolean exist=false;
				System.out.println( " "+list.size());
				List<Customer> list2 = new ArrayList();
				for(int i=0;i<list.size();i++){
					Customer compare=list.get(i);
					if(compare.getId().getId()==custInserted.getId().getId()){
						list2.add(custInserted);
						exist=true;
					}else{
						list2.add(compare);
					}
						
				}
				if(!exist){
					list2.add(custInserted);
				}
				list=list2;
			}
		}
		//List<Customer> list=CustomerDAO.INSTANCE.listCustomers();
		getPage();
		model.addAttribute("listCustomer",list);
		model.addAttribute("customer",new Customer());
		model.addAttribute("page", page);
		return "customer";
	}
	
	@RequestMapping(value="/detail", method = RequestMethod.GET)
	public String detail( ModelMap model, long id) {
		
		//boolean deleted=CustomerDAO.INSTANCE.remove(id);
		//System.out.println("deleted "+deleted);
		Customer c = CustomerDAO.INSTANCE.getCustomer(id);
		getPage();
		model.addAttribute("listCustomer",list);
		model.addAttribute("customer",c);
		model.addAttribute("page", page);
		return "customer";
	}
	
	@RequestMapping(value="/remove", method = RequestMethod.GET)
	public String remove( ModelMap model, long id) {
		/*Customer custInserted=CustomerDAO.INSTANCE.add(form.getName(),form.getDesc());
		if(custInserted!=null){
			if(list!=null){
				System.out.println( " "+list.size());
				List<Customer> list2 = new ArrayList();
				for(int i=0;i<list.size();i++){
					list2.add(list.get(i));
				}
				list2.add(custInserted);
				list=list2;
			}
		}
		//List<Customer> list=CustomerDAO.INSTANCE.listCustomers();
		
		model.addAttribute("listCustomer",list);*/
		//System.out.println(id);
		boolean deleted=CustomerDAO.INSTANCE.remove(id);
		System.out.println("deleted "+deleted);
		if(deleted){
			System.out.println("masuk deleted");
			List<Customer> list2 = new ArrayList();
			for(int i=0;i<list.size();i++){
				Customer c=list.get(i);
				//System.out.println(id+"="+c.getId()+"?"+(c.getId()==id));
				if(c.getId().getId()==id){
					//System.out.println("list size before removed : "+list.size()+" id="+id);
					//list.remove(i);
					//System.out.println("list size after removed : "+list.size()+"");
					//break;
				}else{
					list2.add(c);
				}
			}
			list=list2;
			//delete dari list;
		}
		getPage();
		model.addAttribute("listCustomer",list);
		model.addAttribute("customer",new Customer());
		model.addAttribute("page", page);
		return "customer";
	}
 
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void download(HttpServletResponse response) {
		try {

			response.setContentType("application/x-download");
			// Specifying the name of the file
			response.setHeader("Content-Disposition",
					"attachment; filename=customer.xls");
			WriteExcel we = new WriteExcel();
			// Create the workbook with the output stream of the response
			WritableWorkbook jxlWorkbook = Workbook.createWorkbook(response
					.getOutputStream());
			jxlWorkbook.createSheet("Report", 0);
			WritableSheet excelSheet = jxlWorkbook.getSheet(0);
			excelSheet = we.addHeaderCustomer(excelSheet);
			excelSheet = we.addContentCustomer(excelSheet, list);

			jxlWorkbook.write();
			jxlWorkbook.close();
			// Finally close the stream
			response.getOutputStream().close();
			response.flushBuffer();
		} catch (Exception ex) {

			ex.printStackTrace();
		}

	}
	
}
