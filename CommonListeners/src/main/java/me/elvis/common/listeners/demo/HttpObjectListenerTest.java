package me.elvis.common.listeners.demo;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Version:v1.0 (description:  ) Date:2017/11/22 0022  Time:13:04
 */
@WebListener
public class HttpObjectListenerTest
		implements HttpSessionListener, ServletRequestListener, ServletContextListener {

	Log log = LogFactory.getLog(getClass());

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext servletContext = sce.getServletContext();
		log.info("即将启动" + servletContext.getContextPath());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ServletContext servletContext = sce.getServletContext();
		log.info("即将关闭" + servletContext.getContextPath());
	}

	@Override
	public void requestDestroyed(ServletRequestEvent sre) {

		HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();

		long time = System.currentTimeMillis() - (Long) request.getAttribute("dateCreated");

		log.info(request.getRemoteAddr() + "请求处理结束, 用时" + time + "毫秒. ");
	}

	@Override
	public void requestInitialized(ServletRequestEvent sre) {

		HttpServletRequest httpRequest = (HttpServletRequest) sre.getServletRequest();
		String uri = httpRequest.getRequestURI();
		uri = httpRequest.getQueryString() == null ?
				uri :
				(uri + "?" + httpRequest.getQueryString());

		httpRequest.setAttribute("dateCreated", System.currentTimeMillis());

		log.info("IP " + httpRequest.getRemoteAddr() + " 请求 " + uri);
	}

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		HttpSession httpSession = se.getSession();
		log.info("新创建一个session, ID为: " + httpSession.getId());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		HttpSession httpSession = se.getSession();
		log.info("销毁一个session, ID为: " + httpSession.getId());
	}
}
