package com.pulsa.security;

import java.util.List;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.pulsa.persistence.dao.UserDAO;

public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider
{
	//private SecurityDao securityDao; 

	@Override
	protected UserDetails retrieveUser(String username,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException
	{
		final String password = authentication.getCredentials().toString();
		boolean isValidUser = UserDAO.INSTANCE.isValidUser(username, password);
		if (isValidUser)
		{
			final List<SimpleGrantedAuthority> authorities = UserDAO.INSTANCE.getAuthoritiesByUser(username);
			//User u=new User(username,password,);
			return new User(username, password, true, true, true, true, authorities);
		}
		else
		{
			authentication.setAuthenticated(false);
			throw new BadCredentialsException("Username/Password does not match for " 
				+ authentication.getPrincipal());
		}
		
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails arg0,
			UsernamePasswordAuthenticationToken arg1)
			throws AuthenticationException {
		// TODO Auto-generated method stub
		
	}
}