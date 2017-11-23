package me.evlis.listener.core.test;

import org.junit.Test;

import me.elvis.listener.core.event.DefaultCatListener;
import me.elvis.listener.core.event.PersonEvent;
import me.elvis.listener.core.simple.IPersonRunListener;
import me.elvis.listener.core.simple.Person;

/**
 * Version:v1.0 (description:  ) Date:2017/11/21 0021  Time:11:27
 */
public class Demo {

	@Test
	public void testListenerCoreSimple() {
		Person person = new Person("张三");
		IPersonRunListener listener = new IPersonRunListener() {

			@Override
			public void fighting() {
				//这里可以做很多事，不是只能输出哦
				//不过由于还没写Event对象，所以拿不到是谁调用的
				System.out.println("先做好准备工作...");
			}
		};
		// 给Person对象添加前置的监听器
		person.addBefore(listener);

		A a = new A();

		// 给Person对象添加后置的监听器
		person.addAfter(a);

		// 执行时根据逻辑先执行前置监听器相关内容，再执行本身的内容，再执行后置监听器
		// 类似于AOP
		person.run();
	}

	class A implements IPersonRunListener {

		@Override
		public void fighting() {
			//这里可以做很多事，不是只能输出哦
			//不过由于还没写Event对象，所以拿不到是谁调用的
			System.out.println("跑完了，休息休息...");
		}

	}

	@Test
	public void testListenerCoreEvent() {
		me.elvis.listener.core.event.Person p1 = new me.elvis.listener.core.event.Person("张三");
		me.elvis.listener.core.event.Person p2 = new me.elvis.listener.core.event.Person("Jack");
		me.elvis.listener.core.event.IPersonRunListener listener = new me.elvis.listener.core.event.IPersonRunListener() {

			@Override
			public void fighting(PersonEvent pe) {
				System.out.println(pe.getSource() + "已经跑完了...");
				if (pe.getName().equals("张三")) {
					System.out.println(pe.getName() + "跑到了第一名...");
				}
			}
		};
		p1.addPersonListener(listener);
		p2.addPersonListener(listener);
		p1.run();
		p2.run();

		me.elvis.listener.core.event.Person p3 = new me.elvis.listener.core.event.Person("李四");
		p3.addPersonListener(new DefaultCatListener());
		p3.run();
	}
}
