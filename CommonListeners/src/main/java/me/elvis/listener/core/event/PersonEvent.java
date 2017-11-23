package me.elvis.listener.core.event;

/**
 * Version:v1.0 (description:  ) Date:2017/11/21 0021  Time:11:37
 */
public class PersonEvent {

	Person p = null;
	public PersonEvent(Person p) {
		this.p = p;
	}

	public String getName(){
		return p.getName();
	}

	public Object getSource(){
		return p;
	}
}
