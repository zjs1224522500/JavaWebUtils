package me.elvis.listener.core.event;

/**
 * Version:v1.0 (description:  ) Date:2017/11/21 0021  Time:11:38
 */
public class DefaultCatListener implements IPersonRunListener {

	@Override
	public void fighting(PersonEvent personEvent) {
		System.out.println("默认的动作...");
	}
}
