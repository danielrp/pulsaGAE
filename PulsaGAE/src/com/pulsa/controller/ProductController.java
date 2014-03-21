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
import org.springframework.web.bind.annotation.ResponseBody;

import com.pulsa.persistence.dao.ProductDAO;
import com.pulsa.persistence.model.Product;
import com.pulsa.persistence.model.CustomerForm;
import com.pulsa.persistence.model.ProductForm;
import com.pulsa.persistence.model.TransaksiForm;
import com.pulsa.util.helper.HELPER;
import com.pulsa.util.helper.KeyStringConverter;
import com.pulsa.util.helper.WriteExcel;
import com.google.appengine.api.datastore.Key;

@Controller
@RequestMapping("/product/")
public class ProductController {

	List<Product> list = null;
	ProductForm page = null;
	String name = "";
	String[] providers = { "XL", "Simpati", "IM3", "Mentari", "XL Xtra",
			"Esia", "Smart", "As", "Axis", "Flexi", "Three", "Fren", "PLN" };

	@InitBinder
	protected void initBinder(HttpServletRequest req,
			ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Key.class, new KeyStringConverter());
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(
			ModelMap model,
			@RequestParam(value = "p", defaultValue = "0", required = false) String p,
			@RequestParam(value = "search", defaultValue = "n", required = false) String search) {

		if (search != null) {
			name = search;
		}

		// if(list==null){
		list = ProductDAO.INSTANCE.listProduct();
		// }
		getPage();
		int pg = 0;
		if (p != null)
			pg = Integer.parseInt(p);
		if (pg != 0) {
			page.setCurrent(pg);
		}
		// System.out.println("page= "+page+" p "+p+" current "+page.getCurrent()+" begin "+page.getBegin()+" end "+page.getEnd());
		model.addAttribute("listProduct", list);
		model.addAttribute("page", page);
		model.addAttribute("product", new Product());
		return "product";

	}

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void download(HttpServletResponse response) {
		try {

			response.setContentType("application/x-download");
			// Specifying the name of the file
			response.setHeader("Content-Disposition",
					"attachment; filename=product.xls");
			WriteExcel we = new WriteExcel();
			// Create the workbook with the output stream of the response
			WritableWorkbook jxlWorkbook = Workbook.createWorkbook(response
					.getOutputStream());
			jxlWorkbook.createSheet("Report", 0);
			WritableSheet excelSheet = jxlWorkbook.getSheet(0);
			excelSheet = we.addHeaderProduct(excelSheet);
			excelSheet = we.addContentProduct(excelSheet, list);

			jxlWorkbook.write();
			jxlWorkbook.close();
			// Finally close the stream
			response.getOutputStream().close();
			response.flushBuffer();
		} catch (Exception ex) {

			ex.printStackTrace();
		}

	}

	public void getPage() {
		int p = 1;
		boolean flag = false;
		if (page != null) {
			p = page.getCurrent();
			flag = true;
		}
		page = new ProductForm(list.size(), 15);
		if (flag) {
			page.setCurrent(p);
		}
		page.setName(name);
	}

	@RequestMapping(value = "/list2", method = RequestMethod.GET)
	public String list2(ModelMap model) {

		// if(list==null){
		list = ProductDAO.INSTANCE.listProduct();
		// }
		getPage();
		model.addAttribute("listProduct", list);
		model.addAttribute("page", page);
		model.addAttribute("product", new Product());
		return "product2";

	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(ModelMap model, Product form) {
		// Product
		// custInserted=ProductDAO.INSTANCE.add(form.getName(),form.getDesc());
		System.out.println(form.getId() + " id nya nih");
		Product inserted = ProductDAO.INSTANCE.save(form);
		if (inserted != null) {
			if (list != null) {
				boolean exist = false;
				System.out.println(" " + list.size());
				List<Product> list2 = new ArrayList();
				for (int i = 0; i < list.size(); i++) {
					Product compare = list.get(i);
					if (compare.getId().equals(inserted.getId())) {
						list2.add(inserted);
						exist = true;
					} else {
						list2.add(compare);
					}

				}
				if (!exist) {
					list2.add(inserted);
				}
				list = list2;
			}
		}
		// List<Product> list=ProductDAO.INSTANCE.listProducts();
		getPage();
		model.addAttribute("listProduct", list);
		model.addAttribute("product", new Product());
		model.addAttribute("page", page);
		return "product";
	}

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String detail(ModelMap model, long id) {

		// boolean deleted=ProductDAO.INSTANCE.remove(id);
		// System.out.println("deleted "+deleted);
		Product c = ProductDAO.INSTANCE.getProduct(id);
		getPage();
		model.addAttribute("listProduct", list);
		model.addAttribute("product", c);
		model.addAttribute("page", page);
		return "product";
	}

	@RequestMapping(value = "/remove", method = RequestMethod.GET)
	public String remove(ModelMap model, long id) {
		/*
		 * Product
		 * custInserted=ProductDAO.INSTANCE.add(form.getName(),form.getDesc());
		 * if(custInserted!=null){ if(list!=null){ System.out.println(
		 * " "+list.size()); List<Product> list2 = new ArrayList(); for(int
		 * i=0;i<list.size();i++){ list2.add(list.get(i)); }
		 * list2.add(custInserted); list=list2; } } //List<Product>
		 * list=ProductDAO.INSTANCE.listProducts();
		 * 
		 * model.addAttribute("listProduct",list);
		 */
		// System.out.println(id);
		boolean deleted = ProductDAO.INSTANCE.remove(id);
		System.out.println("deleted " + deleted);
		if (deleted) {
			System.out.println("masuk deleted");
			List<Product> list2 = new ArrayList();
			for (int i = 0; i < list.size(); i++) {
				Product c = list.get(i);
				// System.out.println(id+"="+c.getId()+"?"+(c.getId()==id));
				if (c.getId().equals(id)) {
					// System.out.println("list size before removed : "+list.size()+" id="+id);
					// list.remove(i);
					// System.out.println("list size after removed : "+list.size()+"");
					// break;
				} else {
					list2.add(c);
				}
			}
			list = list2;
			// delete dari list;
		}
		getPage();
		model.addAttribute("listProduct", list);
		model.addAttribute("product", new Product());
		model.addAttribute("page", page);
		return "product";
	}

	@RequestMapping(value = "product", method = RequestMethod.GET, headers = "Accept=*/*")
	public @ResponseBody
	String productList(@RequestParam("term") String query) {

		List<Product> products = ProductDAO.INSTANCE.listProduct(query);
		String json = "[";
		for (int i = 0; i < products.size(); i++) {
			// System.out.println("product list i="+i+" size "+products.size());
			Product o = products.get(i);
			if (i > 0) {
				json += ",";
			}
			json += "{\"value\":\"" + o.getCode() + "\",\"label\":\""
					+ o.getProvider() + " - " + o.getNominal()
					+ "\",\"price\":\"" + o.getPrice() + "\",\"cost\":\""
					+ o.getCost() + "\"}";
			// json+="\""+o.getCode()+"\"";
			// System.out.println("Object = " + i + " -> " + o.getCode());
		}
		json += "]";

		// System.out.println(json);
		return json;
	}

	@RequestMapping(value = "allproduct", method = RequestMethod.GET, headers = "Accept=*/*")
	public @ResponseBody
	String productList() {

		List<Product> products = ProductDAO.INSTANCE.listProduct();
		String json = "[";
		for (int i = 0; i < products.size(); i++) {
			Product o = products.get(i);
			if (i > 0) {
				json += ",";
			}
			json += "{\"value\":\"" + o.getCode() + "\",\"label\":\""
					+ o.getCode() + " - " + o.getProvider() + " - "
					+ HELPER.format(o.getNominal()) + "\",\"price\":\""
					+ o.getPrice() + "\",\"cost\":\"" + o.getCost() + "\"}";
		}
		json += "]";

		System.out.println(json);
		return json;
	}

	@RequestMapping(value = "allprovider", method = RequestMethod.GET, headers = "Accept=*/*")
	public @ResponseBody
	String providerList() {

		String json = "[";
		for (int i = 0; i < providers.length; i++) {
			// System.out.println("product list i="+i+" size "+products.size());
			// Product o = products.get(i);
			if (i > 0) {
				json += ",";
			}

			json += "\"" + providers[i] + "\"";
			// System.out.println("Object = " + i + " -> " + o.getCode());
		}
		json += "]";

		// System.out.println(json);
		return json;
	}

}
