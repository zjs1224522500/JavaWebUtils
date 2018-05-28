package tech.shunzi.utils;

import org.apache.http.conn.HttpClientConnectionManager;

/**
 * Version:v1.0 (description:  ) Date:2018/5/19 0019  Time:17:11
 */
public class IdleConnectionEvictor extends Thread {

	private final HttpClientConnectionManager connectionManager;

	private volatile boolean shutdown;

	public IdleConnectionEvictor(HttpClientConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}

	@Override
	public void run() {
		try {
			while (!shutdown) {
				synchronized (this) {
					//3s检查一次
					wait(3000);
					// 关闭失效的连接
					connectionManager.closeExpiredConnections();
				}
			}
		} catch (InterruptedException ex) {
			// 结束
			ex.printStackTrace();
		}
	}

	public void shutdown() {
		shutdown = true;
		synchronized (this) {
			notifyAll();
		}
	}
}
