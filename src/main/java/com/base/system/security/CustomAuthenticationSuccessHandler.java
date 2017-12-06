package com.base.system.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		 String redirectUrl = request.getContextPath();
	
		 logger.debug("onAuthenticationSuccess : {}",redirectUrl);
		 
		 response.sendRedirect(redirectUrl+"/main.do");
		  
//		AuthenticationEntryPoint
		
//	      HttpSession session = httpServletRequest.getSession();
//	      User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//	      session.setAttribute("username", authUser.getUsername());
//	      session.setAttribute("authorities", authentication.getAuthorities());
//	        //set our response to OK status
//	      httpServletResponse.setStatus(HttpServletResponse.SC_OK);
//	        //since we have created our custom success handler, its up to us to where
//	        //we will redirect the user after successfully login
//	      httpServletResponse.sendRedirect("home");		
	}

}
