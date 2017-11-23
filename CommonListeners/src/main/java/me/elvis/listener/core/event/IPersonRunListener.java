package me.elvis.listener.core.event;

/**
 * Version:v1.0 (description:  ) Date:2017/11/21 0021  Time:11:21
 */
public interface IPersonRunListener {

	// 声明监听器执行的操作。对应不同监听器的具体实现执行各自的操作
	void fighting(PersonEvent personEvent);
}
