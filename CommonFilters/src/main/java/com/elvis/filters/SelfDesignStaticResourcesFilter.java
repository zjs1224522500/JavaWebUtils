package com.elvis.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Version:v1.0 (description: 静态资源缓存时间设置过滤器  ) Date:2017/10/31 0031  Time:13:59
 */
public class SelfDesignStaticResourcesFilter implements Filter {

	private FilterConfig filterConfig = null;

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		String uri = request.getRequestURI();
		String staticResource = uri.substring(uri.lastIndexOf(".") + 1);

		String expire = filterConfig.getInitParameter(staticResource);
		if (null != expire) {
			long t = Long.parseLong(expire) * 3600 * 1000;
			response.setDateHeader("expire", System.currentTimeMillis() + t);
		}
		filterChain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	@Override
	public void destroy() {

	}
}
