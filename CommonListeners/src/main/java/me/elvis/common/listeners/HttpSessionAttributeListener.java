package me.elvis.common.listeners;

import javax.servlet.http.HttpSessionBindingEvent;

/**
 * Version:v1.0 (description: 用于监听session中属性的变化，对应的被监听对象执行add/update/remove  )
 * Date:2017/11/22 0022  Time:12:30
 */
public class HttpSessionAttributeListener implements
		javax.servlet.http.HttpSessionAttributeListener {

	@Override
	public void attributeAdded(HttpSessionBindingEvent se) {

	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent se) {

	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent se) {

	}
}
