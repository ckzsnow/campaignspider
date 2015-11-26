package com.ys.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ys.constant.ConstantCode;

public class SessionFilter extends OncePerRequestFilter {
	
	private String[] filterUrls;

	public SessionFilter() {
		filterUrls = new String[] { "/admin/" };
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String uri = request.getRequestURI();
		boolean doFilter = false;
			for (String url : filterUrls) {
				if (uri.indexOf(url) != -1) {
					doFilter = true;
					break;
				}
			}
		if (doFilter) {
			Object userId = request.getSession().getAttribute(
					ConstantCode.USER_SESSION_ID);
			if (null == userId) {
				boolean isAjaxRequest = isAjaxRequest(request);
				if (isAjaxRequest) {
					response.setCharacterEncoding("UTF-8");
					response.sendError(HttpStatus.UNAUTHORIZED.value(),
							"Unauthorized!");
					return;
				}
				response.sendRedirect("/views/unauthorized.html");
				return;
			} 
		}
		filterChain.doFilter(request, response);
	}

	public static boolean isAjaxRequest(HttpServletRequest request) {
		String header = request.getHeader("X-Requested-With");
		if (header != null && "XMLHttpRequest".equals(header))
			return true;
		else
			return false;
	}

}