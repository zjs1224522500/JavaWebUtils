package me.elvis.common.listeners.demo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * Version:v1.0 (description:  ) Date:2017/11/22 0022  Time:13:53
 */
@WebListener
public class HttpSessionAttributeListenerTest implements HttpSessionAttributeListener {

	Log log = LogFactory.getLog(getClass());

	@Override
	public void attributeAdded(HttpSessionBindingEvent se) {
		HttpSession session = se.getSession();
		String name = se.getName();
		log.info("新建session属性：" + name + ", 值为：" + se.getValue());
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent se) {
		HttpSession session = se.getSession();
		String name = se.getName();
		log.info("删除session属性：" + name + ", 值为：" + se.getValue());
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent se) {
		HttpSession session = se.getSession();
		String name = se.getName();
		Object oldValue = se.getValue();
		log.info("修改session属性：" + name + ", 原值：" + oldValue + ", 新值：" + session.getAttribute(name));
	}
}
