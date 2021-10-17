package com.duke.executors;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SimpleExecutorServiceExample {

	public static void main(String[] args) {
		
		ExecutorService  pool = Executors.newCachedThreadPool();
		
		Callable<Integer> callable = new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				Thread.sleep(1000);
				return 100;
			}
		};
		
		Future<Integer> future = pool.submit(callable);
		if (!future.isDone()) {
			try {
				System.out.println("Future Task done: "+future.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		pool.shutdown();
	}

}
