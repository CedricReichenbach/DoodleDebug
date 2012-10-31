package ch.unibe.scg.doodle.server;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class LightboxStack implements Cloneable {
	private Object top;
	private LightboxStack tail;
	private LightboxStack cutHead;

	public LightboxStack(Object top) {
		this.top = top;
	}

	public Object top() {
		return top;
	}

	public void push(Object o) {
		this.cutHead = null;
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

	public List<Object> cutHeadList() {
		List<Object> list = new LinkedList<Object>();
		if (this.cutHead != null) {
			list.add(cutHead.top);
			list.addAll(cutHead.cutHeadList());
		}
		return list;
	}

	/**
	 * Removes a number of top elements from this stack. Does not handle
	 * Nullpointer exceptions.
	 * 
	 * @param num
	 */
	public void cutOffTop(int num) {
		if (num < 0) {
			putBackOnHeads(-num);
			return;
		}

		for (int i = 0; i < num; i++) {
			if (this.tail == null) {
				System.err
						.println("LightboxStack: Cannot go back one more (only one on stack)");
				return;
			}
			try {
				this.cutHead = (LightboxStack) this.clone();
			} catch (CloneNotSupportedException e) {
				throw new RuntimeException(e);
			}
			this.top = tail.top;
			this.tail = this.tail.tail;
		}
	}

	private void putBackOnHeads(int num) {
		if (num <= 0) {
			return;
		}
		if (this.cutHead == null) {
			System.err
					.println("LightboxStack: Cannot go forward one more (reached end of list)");
		}

		this.tail = this.cutHead.tail;
		this.top = this.cutHead.top;
		this.cutHead = this.cutHead.cutHead;
		putBackOnHeads(num - 1);
	}

	public int depth() {
		if (this.tail == null)
			return 1;

		return this.tail.depth() + 1;
	}
}
