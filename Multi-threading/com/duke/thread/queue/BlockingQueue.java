package com.duke.thread.queue;

import java.util.LinkedList;
import java.util.List;

public class BlockingQueue<T> {
	
	private List<Object> queue;
	private int capacity = 10;

	public BlockingQueue(int size) {
		this.capacity = size;
		this.queue = new LinkedList<Object>();;
	}
	
	public void enqueue(Object item) throws InterruptedException {
		while (queue.size() == capacity) {
			wait();
		}
		if (queue.size() == 0) {
			notifyAll();
		}
		this.queue.add(item);
	}
	
	public Object dequeue() throws InterruptedException {
		while (queue.size() == 0) {
			wait();
		}
		if (queue.size() == capacity) {
			notifyAll();
		}
		return queue.remove(0);
	}

	public boolean isEmpty() {
		if (queue.size() == 0) {
			return true;
		}
		return false;
	}
}
