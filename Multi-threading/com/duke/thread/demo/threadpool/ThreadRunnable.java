package com.duke.thread.demo.threadpool;

import com.duke.thread.queue.BlockingQueue;

public class ThreadRunnable extends Thread {
	private BlockingQueue<Runnable> queue;

	public ThreadRunnable(com.duke.thread.queue.BlockingQueue<Runnable> queue2) {
		super();
		this.queue = queue2;
	}

	@Override
	public void run() {
		while(true) {
			synchronized (queue) {
				while (queue.isEmpty()) {
					try {
						queue.wait();
					} catch (InterruptedException e) {
						System.out.println("An error occurred while queue is waiting: " + e.getMessage());
					}
				}
				Task task = null;
				try {
					task = (Task) queue.dequeue();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Removed task from queue and execute: " + task.number);
				task.run();
			}
		}
	}
	
	
	public synchronized void shutdown()
	{
		if(!Thread.currentThread().isInterrupted() && queue.isEmpty())
		{
			Thread.currentThread().interrupt();
		}
	}
}
