package com.duke.thread.future;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

class MyCallable implements Callable<String>
{
	int delay;
	
	MyCallable(int delay)
	{
		this.delay=delay;
	}
	
	@Override
	public String call() throws Exception {
		
		Thread.sleep(delay);
		return Thread.currentThread().getName();
	}
}

public class FutureTaskExample {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		MyCallable t1= new MyCallable(1000);
		MyCallable t2= new MyCallable(2000);
		//CompletableFuture<T>
		
		FutureTask<String> futureTask1 = new FutureTask<String>(t1);
		FutureTask<String> futureTask2 = new FutureTask<String>(t2);
		
		ExecutorService executor = Executors.newFixedThreadPool(2);
		executor.execute(futureTask1);
		executor.execute(futureTask2);
		
		while (true) {
			
			
			if(futureTask1.isDone())
			{
				System.out.println("Waiting for task2 to complete:"+futureTask2.get());
			}
			
			if(futureTask2.isDone())
			{
				System.out.println("Waiting for task1 to complete: "+futureTask1.get());
			}
			
			if(futureTask1.isDone() && futureTask1.isDone())
			{
				System.out.println("done");
				executor.shutdown();
				return;
			}
		}
	}
}
