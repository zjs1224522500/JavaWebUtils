package me.elvis.common.listeners;

import javax.servlet.ServletRequestEvent;

/**
 * Version:v1.0  Date:2017/11/22 0022  Time:12:30
 * description: 监听request的创建与销毁
 */
public class ServletRequestListener implements javax.servlet.ServletRequestListener {

	/**
	 * request处理完毕自动销毁前执行该方法
	 * @param sre request事件对象，可获取事件源request和上下文context
	 */
	@Override
	public void requestDestroyed(ServletRequestEvent sre) {

	}

	/**
	 * 每次请求request都会执行该方法
	 * @param sre request事件对象，可获取事件源request和上下文context
	 */
	@Override
	public void requestInitialized(ServletRequestEvent sre) {

	}
}
