package com.pulsa.util.helper;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author user
 */
public class SessionHelper {

 
    public static String getUsername(){
    	//System.out.println(((SecurityContext) SecurityContextHolder.getContext()).getAuthentication());
    	if(((SecurityContext) SecurityContextHolder.getContext()).getAuthentication()!=null){
    		return ((SecurityContext) SecurityContextHolder.getContext()).getAuthentication().getName();
    	}else{
    		return "";
    	}
    }

  
}
