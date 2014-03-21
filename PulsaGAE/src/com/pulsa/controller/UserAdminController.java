package com.pulsa.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pulsa.persistence.dao.CustomerDAO;
import com.pulsa.persistence.dao.ProductDAO;
import com.pulsa.persistence.dao.UserDAO;
import com.pulsa.persistence.model.Customer;
import com.pulsa.persistence.model.Product;
import com.pulsa.persistence.model.ProductForm;
import com.pulsa.persistence.model.UserInfo;
 
@Controller
@RequestMapping("/admin/user")
public class UserAdminController {
	
	List<UserInfo> list = null;
	ProductForm page = null;
	
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public String list( ModelMap model,
			@RequestParam(value = "p", defaultValue = "0", required = false) String p,
			@RequestParam(value = "search", defaultValue = "n", required = false) String search) {
		list=UserDAO.INSTANCE.listUser();
		getPage();
		model.addAttribute("listUser",list);
		model.addAttribute("page", page);
		model.addAttribute("user",new UserInfo(""));
		return "user";
 
	}
	@RequestMapping(value="/save", method = RequestMethod.POST)
	public String update( ModelMap model,UserInfo form) {
		UserInfo userInserted=UserDAO.INSTANCE.save(form);
		//UserInfo u = UserDAO.INSTANCE.getUser();
		if(userInserted!=null) {
			if (list != null) {
				boolean exist = false;
				System.out.println(" " + list.size());
				List<UserInfo> list2 = new ArrayList();
				for (int i = 0; i < list.size(); i++) {
					UserInfo compare = list.get(i);
					if (compare.getUsername().equals(userInserted.getUsername())) {
						list2.add(userInserted);
						exist = true;
					} else {
						list2.add(compare);
					}

				}
				if (!exist) {
					list2.add(userInserted);
				}
				list = list2;
			}
			
		}
		getPage();
		model.addAttribute("listUser",list);
		model.addAttribute("page", page);
		model.addAttribute("user",new UserInfo(""));
		return "user";
 
	}
	
	@RequestMapping(value = "/detail/{username}", method = RequestMethod.GET)
	public String detail(@PathVariable String username, 
			HttpServletRequest request, ModelMap model) {

		// boolean deleted=ProductDAO.INSTANCE.remove(id);
		// System.out.println("deleted "+deleted);
		UserInfo c = UserDAO.INSTANCE.getUser(username);
		getPage();
		model.addAttribute("listUser", list);
		model.addAttribute("user", c);
		model.addAttribute("page", page);
		return "user";
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
		//page.setName(name);
	}
}
