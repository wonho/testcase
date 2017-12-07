package com.base.system.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class LoginSucessHandler extends SavedRequestAwareAuthenticationSuccessHandler  {

	/**
	 * 
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		  Cookie k = new Cookie("JSESSIONID", request.getSession().getId());
		  k.setPath(request.getContextPath());
		  response.addCookie(k);
		  String redirectUrl = request.getContextPath();
		  response.sendRedirect(redirectUrl+"/main.do");	 
		  super.onAuthenticationSuccess(request, response, authentication);
	}
}
