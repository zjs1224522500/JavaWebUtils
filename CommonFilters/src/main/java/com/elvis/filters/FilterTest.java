package com.elvis.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Version:v1.0 (description:  ) Date:2017/10/30 0030  Time:20:58
 */
public class FilterTest implements Filter {

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		// 对request、response进行一些预处理
		servletRequest.setCharacterEncoding("UTF-8");
		servletResponse.setCharacterEncoding("UTF-8");
		servletResponse.setContentType("text/html;charset=UTF-8");
		System.out.println("----调用service之前执行一段代码----");
		// 执行目标资源，放行
		filterChain.doFilter(servletRequest, servletResponse);
		System.out.println("----调用service之后执行一段代码----");
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("----Filter初始化----");
	}

	@Override
	public void destroy() {
		System.out.println("----Filter销毁----");
	}
}
