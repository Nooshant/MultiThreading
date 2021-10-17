package com.duke.thread.demo;

import java.util.concurrent.CountDownLatch;

class MyThread extends Thread{
	private CountDownLatch latch;
	private int delay;
	
	public MyThread(String name, int delay, CountDownLatch latch) {
		super(name);
		this.delay=delay;
		this.latch=latch;
	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName()+" finished");
		try {
			Thread.sleep(delay);
			latch.countDown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

public class CountDownLatchDemo {

	public static void main(String[] args) throws InterruptedException {
		
		CountDownLatch latch = new CountDownLatch(4);
		MyThread first = new MyThread("first",2000,latch);
		MyThread second = new MyThread("second",2000,latch);
		MyThread third = new MyThread("third",2000,latch);
		MyThread fourth = new MyThread("fourth",2000,latch);
		
		first.start();
		second.start();
		third.start();
		fourth.start();
		
		latch.await();
		
		System.out.println("Main thread completed->"+Thread.currentThread().getName());
	}

}
