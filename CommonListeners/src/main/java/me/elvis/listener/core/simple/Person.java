package me.elvis.listener.core.simple;

/**
 * Version:v1.0 (description:  ) Date:2017/11/21 0021  Time:11:19
 */
public class Person {

	private String name;

	// 通过依赖监听器的方式来进行绑定
	private IPersonRunListener listener1;

	private IPersonRunListener listener2;

	public Person(String name) {
		super();
		this.name = name;
	}

	// 决定相关监听器的执行过程
	public void run() {
		if (listener1 != null) {
			listener1.fighting();
		}
		System.out.println(name + "正在跑...");
		if (listener2 != null) {
			listener2.fighting();
		}
	}

	// 注入监听器
	public void addBefore(IPersonRunListener listener) {
		this.listener1 = listener;
	}

	// 注入监听器
	public void addAfter(IPersonRunListener listener) {
		this.listener2 = listener;
	}
}
