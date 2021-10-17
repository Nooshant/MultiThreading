package com.duke.thread.demo.threadpool;

public class Task implements Runnable {
	
	int number;
	public Task(int number) {
		this.number = number;
	}

	@Override
	public void run() {
		System.out.println("Start executing the task number:"+number);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("End executing the task number:"+number);
	}
}
