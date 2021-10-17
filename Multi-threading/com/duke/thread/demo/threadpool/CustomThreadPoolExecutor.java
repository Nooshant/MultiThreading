package com.duke.thread.demo.threadpool;

import com.duke.thread.queue.BlockingQueue;

public class CustomThreadPoolExecutor {	
	
	private ThreadRunnable[] threadRunnable;
	private BlockingQueue<Runnable> queue;
	private static int MAX_TASK_SIZE = 5;
	//create the noOfThreads and start it.
	public CustomThreadPoolExecutor(int noOfThreads)
	{
		queue = new BlockingQueue<>(MAX_TASK_SIZE);
		threadRunnable = new ThreadRunnable[noOfThreads];
		for (int i = 0; i < noOfThreads; i++)
		{
			threadRunnable[i] = new ThreadRunnable(queue);
		}
		for (int i = 0; i < noOfThreads; i++) {
			Thread thread = new Thread(threadRunnable[i]);
			thread.start();
		}
	}

	public void execute(Runnable task) throws InterruptedException {
		Task task2 = (Task) task;
		System.out.println("Add in queue-> task:" + task2.number);
		synchronized(queue)
		{
			queue.enqueue(task2);
			queue.notify();
		}	
	}
	
	public void shutdown()
	{
		
		synchronized (queue) {
			System.out.println("Shutting down the Threadpool.");
			for (int i = 0; i < threadRunnable.length; i++) {
				threadRunnable[i].shutdown();
			}
		}
		
	}
}
