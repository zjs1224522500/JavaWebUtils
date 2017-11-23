package me.elvis.servlet.listener;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * Version:v1.0 (description:  ) Date:2017/11/21 0021  Time:13:50
 */
public class SelfDefineHttpSessionAttributeListener implements HttpSessionAttributeListener {

	@Override
	public void attributeAdded(HttpSessionBindingEvent se) {
		if (se.getName().equals("name")) {//代表添加了name属性
			System.out.println("用户" + se.getValue() + "登录了");
			List<HttpSession> logins = (List<HttpSession>) se.getSession().getServletContext()
					.getAttribute("logins");

			if (logins == null) {
				logins = new ArrayList<HttpSession>();
				se.getSession().getServletContext().setAttribute("logins", logins);
			}
			logins.add(se.getSession());
		}
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent se) {

	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent se) {

	}
}
