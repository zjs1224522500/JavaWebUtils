package me.elvis.common.listeners;

import javax.servlet.ServletContextEvent;

/**
 * Version:v1.0 Date:2017/11/22 0022  Time:12:29
 * description: 监听context的创建与销毁，context代表当前web应用程序
 * 				该监听器可用于启动时加载web.xml中的初始化参数
 */
public class ServletContextListener implements javax.servlet.ServletContextListener {

	/**
	 * 服务器启动或者热部署war包时
	 * @param sce 上下文事件对象，可以获取事件源context
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {

	}

	/**
	 * 服务器关闭或者只关闭当前web应用程序时
	 * @param sce 上下文事件对象，可以获取事件源context
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}
}
