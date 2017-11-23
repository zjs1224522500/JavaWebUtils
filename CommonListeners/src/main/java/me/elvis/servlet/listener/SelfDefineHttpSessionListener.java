package me.elvis.servlet.listener;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Version:v1.0 (description:  ) Date:2017/11/21 0021  Time:13:30
 */
public class SelfDefineHttpSessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		List<HttpSession> guests = (List<HttpSession>) se.getSession().getServletContext()
				.getAttribute("guests");//在线人的集合
		if (guests == null) {//第一个访问网站的人--沙发
			guests = new ArrayList<HttpSession>();
			se.getSession().getServletContext().setAttribute("guests", guests);//设置guests属性
		}
		guests.add(se.getSession());//将第一个用户的session添加到在线人集合
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		List<HttpSession> guests = (List<HttpSession>) se.getSession().getServletContext()
				.getAttribute("fuses");
		if (guests.contains(se.getSession())) {
			guests.remove(se.getSession());
		}
	}
}
