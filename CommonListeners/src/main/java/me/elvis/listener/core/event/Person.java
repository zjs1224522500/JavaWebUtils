package me.elvis.listener.core.event;

/**
 * Version:v1.0 (description:  ) Date:2017/11/21 0021  Time:11:37
 */
public class Person {

	private String name;

	private IPersonRunListener listener;

	public Person(String name) {
		super();
		this.name = name;
	}

	public void run() {
		System.out.println(name + "开始跑了..");
		if (listener != null) {
			listener.fighting(new PersonEvent(this));
		}
	}

	public void addPersonListener(IPersonRunListener listener) {
		this.listener = listener;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", listener=" + listener + "]";
	}
}
