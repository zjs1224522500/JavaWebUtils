package me.elvis.common.listeners;

import javax.servlet.http.HttpSessionEvent;

/**
 * Version:v1.0 Date:2017/11/22 0022  Time:12:28
 * description:
 * 		监听session的创建与销毁，可用于收集在线者信息
 *
 */
public class HttpSessionListener implements javax.servlet.http.HttpSessionListener {

	/**
	 * 创建session时执行
	 * @param se session事件对象 可获取事件源
	 */
	@Override
	public void sessionCreated(HttpSessionEvent se) {

	}

	/**
	 * 超时或者执行 session.invalidate() 时执行
	 * @param se session事件对象 可获取事件源
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {

	}
}
