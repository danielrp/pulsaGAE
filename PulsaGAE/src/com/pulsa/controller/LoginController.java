package com.pulsa.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pulsa.persistence.dao.CustomerDAO;
import com.pulsa.persistence.model.Customer;
import com.pulsa.util.helper.KeyStringConverter;
import com.google.appengine.api.datastore.Key;
 
@Controller
@RequestMapping("/login")
public class LoginController {

	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login( ModelMap model) {
		
		return "login";
 
	}
	
	
}
