package com.duke.thread.demo.threadpool;

public class ThreadPoolMain {
	public static void main(String[] args) throws InterruptedException {
		CustomThreadPoolExecutor poolExecutor = new CustomThreadPoolExecutor(5);

		for (int i = 0; i < 10; i++) {
			Runnable task = new Task(i);
			poolExecutor.execute(task);
		}
		//poolExecutor.waitUntilAllTasksFinished();
		poolExecutor.shutdown();
	}
}
