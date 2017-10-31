package com.elvis.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Version:v1.0 (description:  ) Date:2017/10/31 0031  Time:12:38
 */
public class SelfDefineCharacterEncodingFilter implements Filter {

	private FilterConfig filterConfig = null;

	private String defaultCharset = "UTF-8";

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		String charset = filterConfig.getInitParameter("charset");
		if(null == charset || "".equals(charset)) {
			charset = defaultCharset;
		}

		servletRequest.setCharacterEncoding(charset);
		servletResponse.setCharacterEncoding(charset);
		servletResponse.setContentType("text/html;charset=" + charset);

		filterChain.doFilter(servletRequest,servletResponse);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	@Override
	public void destroy() {

	}
}
