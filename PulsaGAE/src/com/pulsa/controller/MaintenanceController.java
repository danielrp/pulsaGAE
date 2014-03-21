package com.pulsa.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.appengine.api.datastore.Blob;
import com.pulsa.persistence.dao.CustomerDAO;
import com.pulsa.persistence.dao.UserDAO;
import com.pulsa.persistence.model.Customer;
import com.pulsa.persistence.model.MultiPartFileUpload;
import com.pulsa.persistence.model.UserInfo;
import com.pulsa.util.helper.ReadExcel;
 
@Controller
@RequestMapping("/maintenance/")
public class MaintenanceController {
	@RequestMapping(value="/maintenance", method = RequestMethod.GET)
	public String list( ModelMap model) {
		
		//UserInfo u = UserDAO.INSTANCE.getUser();
		//if(u==null) u = new UserInfo();
		model.addAttribute("file-upload",new MultiPartFileUpload());
		return "maintenance";
 
	}
	@RequestMapping(value="/import", method = RequestMethod.POST)
	public String update( ModelMap model) {
		
		try{
			//ReadExcel test = new ReadExcel();
			//test.setInputFile("D:/eclipse/pulsaGAE/PulsaGAE/war/daniel (8).xls");
			//test.read();
		}catch(Exception e){
			e.printStackTrace();
		}
		//UserInfo userInserted=UserDAO.INSTANCE.save(form);
		//UserInfo u = UserDAO.INSTANCE.getUser();
		//if(userInserted==null) userInserted = new UserInfo();
		//model.addAttribute("userinfo",userInserted);
		return "maintenance";
 
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String postUpload (MultiPartFileUpload upload, HttpServletRequest request) {
	    if (ServletFileUpload.isMultipartContent(request)) {
	    	System.out.println(upload+"|"+upload.getFile()+"|"+upload.getName());
	        if (upload.getFile().getSize() != 0) {
	            Blob file = new Blob(upload.getFile().getBytes());
	            File file2 = new File("import.xls");
	            try {
					//FileUtils.writeByteArrayToFile(file2, file.getBytes());
					ReadExcel read= new ReadExcel();
					//read.setFile(file2);
					read.setUpload(upload);
					read.read();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            System.out.println(upload.getFile().getOriginalFilename()+"|"+upload.getFile().getName());
	            //File file2=file.getBytes();
	            // or do something else with the uploaded file
	            System.out.println("berhasil");
	            return "maintenance";
	        }
	    }
	    System.out.println("gagal");
	    return "maintenance";
	}
	
	@RequestMapping(value = "/product", method = RequestMethod.POST)
	public String product (MultiPartFileUpload upload, HttpServletRequest request) {
	    if (ServletFileUpload.isMultipartContent(request)) {
	    	//System.out.println(upload+"|"+upload.getFile()+"|"+upload.getName());
	        if (upload.getFile().getSize() != 0) {
	          //  Blob file = new Blob(upload.getFile().getBytes());
	          //  File file2 = new File("import.xls");
	            try {
					//FileUtils.writeByteArrayToFile(file2, file.getBytes());
					ReadExcel read= new ReadExcel();
					//read.setFile(file2);
					read.setUpload(upload);
					read.readProduct();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            System.out.println(upload.getFile().getOriginalFilename()+"|"+upload.getFile().getName());
	            //File file2=file.getBytes();
	            // or do something else with the uploaded file
	            System.out.println("berhasil");
	            return "maintenance";
	        }
	    }
	    System.out.println("gagal");
	    return "maintenance";
	}
	
	@RequestMapping(value = "/customer", method = RequestMethod.POST)
	public String customer(MultiPartFileUpload upload, HttpServletRequest request) {
	    if (ServletFileUpload.isMultipartContent(request)) {
	    	//System.out.println(upload+"|"+upload.getFile()+"|"+upload.getName());
	        if (upload.getFile().getSize() != 0) {
	          //  Blob file = new Blob(upload.getFile().getBytes());
	          //  File file2 = new File("import.xls");
	            try {
					//FileUtils.writeByteArrayToFile(file2, file.getBytes());
					ReadExcel read= new ReadExcel();
					//read.setFile(file2);
					read.setUpload(upload);
					read.readCustomer();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            System.out.println(upload.getFile().getOriginalFilename()+"|"+upload.getFile().getName());
	            //File file2=file.getBytes();
	            // or do something else with the uploaded file
	            System.out.println("berhasil");
	            return "maintenance";
	        }
	    }
	    System.out.println("gagal");
	    return "maintenance";
	}
	
	
	
}
