package me.elvis.servlet.listener;

import org.apache.commons.fileupload.servlet.ServletRequestContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

/**
 * Version:v1.0 (description:  ) Date:2017/11/21 0021  Time:13:18
 */
public class SelfDefineRequestListener implements ServletRequestListener {

	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		ServletContext context = sre.getServletContext();
		new SelfDefineThread(context).start();
	}

	@Override
	public void requestInitialized(ServletRequestEvent sre) {

	}
}
