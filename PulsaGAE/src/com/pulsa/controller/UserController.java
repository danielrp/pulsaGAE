package com.pulsa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pulsa.persistence.dao.CustomerDAO;
import com.pulsa.persistence.dao.UserDAO;
import com.pulsa.persistence.model.Customer;
import com.pulsa.persistence.model.UserInfo;
 
@Controller
@RequestMapping("/personal/")
public class UserController {
	@RequestMapping(value="/setting", method = RequestMethod.GET)
	public String list( ModelMap model) {
		
		UserInfo u = UserDAO.INSTANCE.getUser();
		if(u==null) u = new UserInfo();
		model.addAttribute("userinfo",u);
		return "userinfo";
 
	}
	@RequestMapping(value="/update", method = RequestMethod.POST)
	public String update( ModelMap model,UserInfo form) {
		UserInfo userInserted=UserDAO.INSTANCE.save(form);
		//UserInfo u = UserDAO.INSTANCE.getUser();
		if(userInserted==null) userInserted = new UserInfo();
		model.addAttribute("userinfo",userInserted);
		return "userinfo";
 
	}
}
