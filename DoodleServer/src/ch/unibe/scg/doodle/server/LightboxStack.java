package ch.unibe.scg.doodle.server;

import java.util.LinkedList;
import java.util.List;

public class LightboxStack implements Cloneable {
	private Object top;
	private LightboxStack tail;

	public LightboxStack(Object top) {
		this.top = top;
	}

	public Object top() {
		return top;
	}

	public void push(Object o) {
		try {
			this.tail = (LightboxStack) this.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
		this.top = o;
	}

	public List<Object> bottomUpList() {
		List<Object> list;
		if (tail == null) {
			list = new LinkedList<Object>();
		} else {
			list = tail.bottomUpList();
		}
		list.add(top);
		return list;
	}

	/**
	 * Removes a number of top elements from this stack. Does not handle
	 * Nullpointer exceptions.
	 * 
	 * @param num
	 */
	public void cutOffTop(int num) {
		for (int i = 0; i < num; i++) {
			this.top = tail.top;
			this.tail = this.tail.tail;
		}
	}
}
