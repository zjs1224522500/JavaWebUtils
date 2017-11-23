package me.elvis.servlet.listener;

import javax.servlet.ServletContext;

/**
 * Version:v1.0 (description:  ) Date:2017/11/21 0021  Time:13:20
 */
public class SelfDefineThread extends Thread {

	private ServletContext servletContext = null;

	private static Object object = new Object();

	public SelfDefineThread(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@Override
	public void run() {
		synchronized (object) {
			servletContext
					.setAttribute("count", (Integer) servletContext.getAttribute("count") + 1);
		}
	}
}
